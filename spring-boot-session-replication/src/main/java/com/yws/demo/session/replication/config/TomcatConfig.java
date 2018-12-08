package com.yws.demo.session.replication.config;

import org.apache.catalina.ha.session.ClusterSessionListener;
import org.apache.catalina.ha.session.DeltaManager;
import org.apache.catalina.ha.session.JvmRouteBinderValve;
import org.apache.catalina.ha.tcp.ReplicationValve;
import org.apache.catalina.ha.tcp.SimpleTcpCluster;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.interceptors.MessageDispatchInterceptor;
import org.apache.catalina.tribes.group.interceptors.StaticMembershipInterceptor;
import org.apache.catalina.tribes.group.interceptors.TcpFailureDetector;
import org.apache.catalina.tribes.group.interceptors.TcpPingInterceptor;
import org.apache.catalina.tribes.membership.StaticMember;
import org.apache.catalina.tribes.transport.ReplicationTransmitter;
import org.apache.catalina.tribes.transport.nio.NioReceiver;
import org.apache.catalina.tribes.transport.nio.PooledParallelSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yws
 * @data 2018/10/31
 */
@Configuration
public class TomcatConfig {

    @Value("${tomcat.localClusterMemberPort}")
    private int localClusterMemberPort;

    @Value("${tomcat.clusterMembers}")
    private String clusterMembers;

    @Bean
    public TomcatServletWebServerFactory servletContainerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory() {
            @Override
            protected TomcatWebServer getTomcatWebServer(Tomcat tomcat) {
                configureCluster(tomcat);
                return super.getTomcatWebServer(tomcat);
            }
        };

        factory.addContextCustomizers((TomcatContextCustomizer) context -> {
            context.setDistributable(true);
        });

        return factory;
    }

    private void configureCluster(Tomcat tomcat) {
        SimpleTcpCluster cluster = new SimpleTcpCluster();
        cluster.setChannelSendOptions(8);

        DeltaManager manager = new DeltaManager();
        manager.setExpireSessionsOnShutdown(false);
        manager.setNotifyListenersOnReplication(true);
        cluster.setManagerTemplate(manager);

        GroupChannel channel = new GroupChannel();

        NioReceiver receiver = new NioReceiver();
        receiver.setPort(localClusterMemberPort);
        channel.setChannelReceiver(receiver);

        ReplicationTransmitter sender = new ReplicationTransmitter();
        sender.setTransport(new PooledParallelSender());
        channel.setChannelSender(sender);

        channel.addInterceptor(new TcpPingInterceptor());
        //将消息调度到线程（线程池）以异步发送消息
        channel.addInterceptor(new TcpFailureDetector());
        channel.addInterceptor(new MessageDispatchInterceptor());

        StaticMembershipInterceptor membership = new StaticMembershipInterceptor();
        String[] memberSpecs = clusterMembers.split(",", -1);
        for (int i = 0; i < memberSpecs.length; i++) {
            String[] addrPart = memberSpecs[i].split(":");
            StaticMember member = new StaticMember();
            member.setHost(addrPart[0]);
            member.setPort(Integer.parseInt(addrPart[1]));
            member.setDomain("MyDomain");
            member.setUniqueId(String.valueOf(i).getBytes());
            membership.addStaticMember(member);
        }
        channel.addInterceptor(membership);

        cluster.setChannel(channel);

        cluster.addValve(new ReplicationValve());
        cluster.addValve(new JvmRouteBinderValve());
        cluster.addClusterListener(new ClusterSessionListener());

        tomcat.getEngine().setCluster(cluster);
    }

}

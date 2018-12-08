# spring-boot-session-replication 
> 以`session replication`实现分布式会话在嵌入式tomcat(`embedded tomcat`)上的实践  
原理:将集群中某实例的Session数据广播复制到集群中其他实例  
author: yws(严伟森)  
email: yws179@gmail.com

鉴于目前网上针对嵌入式tomcat实现`session replication`较少，且`springboot2.0+`对于`tomcat`配置代码有了较大的改变，网上很难找到相关示例，故提供此示例以供参考。

注：如果项目中已经使用了redis或者集群规模较大，建议使用以下方案：
- 使用`spring session`实现分布式会话(session交给分布式缓存集群管理)
- sticky session 粘性会话

[相关文档 - tomcat8.5 doc](http://tomcat.apache.org/tomcat-8.5-doc/cluster-howto.html)

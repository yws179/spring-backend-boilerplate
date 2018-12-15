package com.yws.boilerplate.swagger.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * @author yws
 * @data 2018/12/15
 */
@Data
@Entity
@Table(name = "tb_item")
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String value;
    
}

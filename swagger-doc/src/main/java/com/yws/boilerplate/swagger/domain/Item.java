package com.yws.boilerplate.swagger.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author yws
 * @data 2018/12/15
 */
@ApiModel(value = "测试品", description = "测试用的物品")
@Data
@Entity
@Table(name = "tb_item")
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "值", allowEmptyValue = true)
    private String value;
    
}

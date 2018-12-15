package com.yws.boilerplate.swagger.repository;

import com.yws.boilerplate.swagger.domain.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yws
 * @data 2018/12/15
 */
public interface ItemRepository extends CrudRepository<Item, Long> {}

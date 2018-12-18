package com.yws.boilerplate.swagger.controller;

import com.yws.boilerplate.swagger.domain.Item;
import com.yws.boilerplate.swagger.repository.ItemRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author yws
 * @data 2018/12/15
 */
@Api(value = "Item Restful API", tags="item接口")
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @ApiOperation(value="item列表", notes = "item列表")
    @GetMapping
    public ResponseEntity list() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @ApiOperation(value="创建item", notes = "创建item")
    @PostMapping
    public ResponseEntity create(@RequestBody Item item) {
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @ApiOperation(value="更新item", notes = "更新item")
    @PutMapping("/{id}")
    public ResponseEntity update(@ApiParam(name = "测试品id") @PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @ApiOperation(value="删除item", notes = "删除item")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@ApiParam(name = "测试品id") @PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

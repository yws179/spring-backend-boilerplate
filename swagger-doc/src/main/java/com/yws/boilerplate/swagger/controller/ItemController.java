package com.yws.boilerplate.swagger.controller;

import com.yws.boilerplate.swagger.domain.Item;
import com.yws.boilerplate.swagger.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author yws
 * @data 2018/12/15
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity list() {
        return ResponseEntity.ok(itemRepository.findAll());
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Item item) {
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Item item) {
        item.setId(id);
        return ResponseEntity.ok(itemRepository.save(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

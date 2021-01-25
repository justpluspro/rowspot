package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.service.CategoryService;
import org.qwli.rowspot.service.NewCategory;
import org.qwli.rowspot.model.aggregate.MenuAggregate;
import org.qwli.rowspot.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class CategoryApi {


    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("category/{parentId}")
    public ResponseEntity<List<MenuAggregate>> findByCategoryId(@PathVariable("parentId") int id) {

        return ResponseEntity.ok(categoryService.findMenuById(id));
    }

    @PostMapping("category/saved")
    public ResponseEntity<Void> save(@RequestBody NewCategory newCategory) {

        categoryService.save(newCategory);
        return ResponseEntity.ok().build();

    }
}

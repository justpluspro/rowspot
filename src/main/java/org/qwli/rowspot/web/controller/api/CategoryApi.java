package org.qwli.rowspot.web.controller.api;

import org.qwli.rowspot.service.CategoryService;
import org.qwli.rowspot.service.NewCategory;
import org.qwli.rowspot.model.aggregate.MenuAggregate;
import org.qwli.rowspot.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author qwli7
 * 分类操作
 */
@RequestMapping("api")
@RestController
public class CategoryApi extends AbstractApi {

    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * 获取所有的分类，不带子分类
     * @return List
     */
    @GetMapping("categories")
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    /**
     * 获取分类下的子类，并排序
     * @param id id
     * @return List<MenuAggregate>
     */
    @GetMapping("category/{parentId}")
    public ResponseEntity<List<MenuAggregate>> findByCategoryId(@PathVariable("parentId") long id) {
        Long parentId = (long) id;
        return ResponseEntity.ok(categoryService.findMenuById(parentId));
    }

    /**
     * 保存分类
     * @param newCategory newCategory
     * @return Void
     */
    @PostMapping("category/saved")
    public ResponseEntity<Void> save(@RequestBody @Validated NewCategory newCategory) {
        categoryService.save(newCategory);
        return ResponseEntity.ok().build();
    }

    /**
     * 删除分类
     * @param id id
     * @return String
     */
    @DeleteMapping("{id}/category")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();

    }
}

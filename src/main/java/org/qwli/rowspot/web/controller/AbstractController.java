package org.qwli.rowspot.web.controller;

import org.qwli.rowspot.MessageEnum;
import org.qwli.rowspot.exception.ResourceNotFoundException;
import org.qwli.rowspot.model.Category;
import org.qwli.rowspot.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qwli7
 * @date 2021/1/29 8:29
 * 功能：AbstractController
 **/
public abstract class AbstractController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final CategoryService categoryService;

    public AbstractController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    protected Category validCategory(String alias) {
       return categoryService.findOne(alias).orElseThrow(()
               -> new ResourceNotFoundException(MessageEnum.RESOURCE_NOT_FOUND));
    }
}

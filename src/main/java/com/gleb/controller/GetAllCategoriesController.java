package com.gleb.controller;

import com.gleb.dao.CategoryDao;
import com.gleb.model.Category;
import com.gleb.web.Request;
import com.gleb.web.ViewModel;

import java.util.List;

public class GetAllCategoriesController implements Controller {

    private final CategoryDao categoryDao;


    public GetAllCategoriesController(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }


    @Override
    public ViewModel process(Request request) {
        List<Category> categories = categoryDao.findAll();
        ViewModel vm = ViewModel.of("categories");
        vm.addAttribute("categories", categories);
        return vm;
    }
}

package com.gleb.controller.admin;

import com.gleb.controller.Controller;
import com.gleb.dao.CategoryDao;
import com.gleb.model.Category;
import com.gleb.web.Request;
import com.gleb.web.ViewModel;

import java.util.List;

public class GetAllCategoriesAdminController implements Controller {

    private final CategoryDao categoryDao;
    private final String VIEW_NAME;

    public GetAllCategoriesAdminController(CategoryDao categoryDao, String viewName) {
        this.categoryDao = categoryDao;
        this.VIEW_NAME = viewName;
    }


    @Override
    public ViewModel process(Request request) {
        List<Category> categories = categoryDao.findAll();
        ViewModel vm = ViewModel.of(VIEW_NAME);
        vm.addAttribute("categories", categories);
        return vm;
    }
}
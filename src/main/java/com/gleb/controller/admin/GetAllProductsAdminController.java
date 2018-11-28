package com.gleb.controller.admin;

import com.gleb.controller.Controller;
import com.gleb.model.Product;
import com.gleb.service.ProductService;
import com.gleb.web.Request;
import com.gleb.web.ViewModel;

import java.sql.SQLException;
import java.util.List;

public class GetAllProductsAdminController implements Controller {

    private ProductService productService;

    public GetAllProductsAdminController(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public ViewModel process(Request request) {
        List<Product> products = productService.findAll();
        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("products", products);
        return vm;
    }
}

package com.gleb.controller.admin;

import com.gleb.controller.Controller;
import com.gleb.model.Category;
import com.gleb.model.Product;
import com.gleb.service.ProductService;
import com.gleb.web.Request;
import com.gleb.web.ViewModel;

import java.util.List;

public class AddProductController implements Controller {

    private final ProductService productService;

    public AddProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ViewModel process(Request request) {
        String productName = request.getParamByName("productName");
        double price = Double.valueOf(request.getParamByName("price"));
        String description = request.getParamByName("description");
        long categoryId = Long.valueOf(request.getParamByName("categoryId"));

        Product product = new Product(productName, price, description);
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        productService.save(product);

        List<Product> products = productService.findAll();

        ViewModel vm = ViewModel.of("manageProducts");
        vm.addAttribute("products", products);

        return vm;
    }
}

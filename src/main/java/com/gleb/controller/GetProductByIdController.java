package com.gleb.controller;

import com.gleb.dao.ProductDao;
import com.gleb.model.Product;
import com.gleb.web.Request;
import com.gleb.web.ViewModel;

import java.sql.SQLException;

public class GetProductByIdController implements Controller {

    private final ProductDao productDao;

    public GetProductByIdController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public ViewModel process(Request request) throws SQLException {
        Product product = productDao.findById(getIdFromRequest(request));
        ViewModel vm = ViewModel.of("product");
        vm.addAttribute("product", product);
        return vm;
    }

    private Long getIdFromRequest (Request request ) {
        String iDobject = request.getParamByName("p_id");
        return Long.valueOf(iDobject);
    }
}

package com.gleb.dao;

import com.gleb.model.Product;

import java.util.List;

public interface ProductDao extends GenericDao<Product> {

    void save(Product product);

    Product findByName(String name);

    Product findById(Long id);

    List<Product> findAll();
}


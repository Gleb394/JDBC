package com.gleb.model;

import com.gleb.annotation.NameColumn;
import com.gleb.annotation.NameTable;

import java.util.List;

@NameTable(name = "CATEGORIES")
public class Category {

    @NameColumn(name = "ID")
    private Long id;
    @NameColumn(name = "CATEGORY_NAME")
    private String name;
    private List<Product> products;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public Category(Long id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}



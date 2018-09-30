package com.gleb.dao;

import com.gleb.model.Category;

import java.util.List;

public interface CategoryDao extends GenericDao<Category>{

    Category findById(long ID);

    List<Category> findAll();
}

package com.gleb.impl;

import com.gleb.dao.AbstractDao;
import com.gleb.dao.ProductDao;
import com.gleb.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl extends AbstractDao<Product> implements ProductDao {

    public ProductDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public void save(Product product) {
        String query = "INSERT INTO PRODUCTS (NAME, PRICE, DESCRIPTION) VALUES (?, ?, ?);";

        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product findByName(String name) {
        String query = "SELECT ID, NAME, PRICE, DESCRIPTION FROM PRODUCTS WHERE NAME = ?";
        PreparedStatement preparedStatement;
        ResultSet resultSet = null;
        Product product = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "iPhone");
            resultSet = preparedStatement.executeQuery();
            product = resultSet.next() ? getProductFromResultSet(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Product findById(Long id) {
        return null;
    }


    public List<Product> findAll() {
        String query = "SELECT ID, NAME, PRICE, DESCRIPTION FROM PRODUCTS";
        List<Product> result = new ArrayList<Product>();
        PreparedStatement statement = null;
        ResultSet resultSet;

        try {
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(getProductFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getDouble(3),
                resultSet.getString(4)
        );
    }
}
package com.gleb.impl;

import com.gleb.dao.AbstractDao;
import com.gleb.dao.UserDao;
import com.gleb.model.Role;
import com.gleb.model.User;

import java.sql.*;

import static com.gleb.model.Role.RoleName.USER;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    public UserDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public User addUser(User user) {
        String userQuery = "INSERT INTO USERS (EMAIL, TOKEN, PASSWORD, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?, ?, ?)";
        String roleQuery = "INSERT INTO USER_TO_ROLE (FK_USER_ID, FK_ROLE_ID) VALUES (?, ?)";
        PreparedStatement userStatement;
        PreparedStatement roleStatement;

        try {
            connection.setAutoCommit(false);
            userStatement = connection.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, user.getEmail());
            userStatement.setString(2, user.getToken());
            userStatement.setString(3, user.getPassword());
            userStatement.setString(4, user.getFirstName());
            userStatement.setString(5, user.getLastName());
            userStatement.executeUpdate();

            ResultSet resultSet = userStatement.getGeneratedKeys();
            long userId = 0;
            if (resultSet.next()) {
                userId = resultSet.getLong(1);
            } else {
                connection.rollback();
            }

            roleStatement = connection.prepareStatement(roleQuery);
            roleStatement.setLong(1, userId);
            roleStatement.setString(2, USER.toString());
            roleStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new RuntimeException(e.getMessage());
            }
        }
        return user;
    }

    @Override
    public User findByToken(String token) {
        String query = "SELECT U.ID, U.EMAIL, U.TOKEN, U.PASSWORD, U.FIRST_NAME, U.LAST_NAME, R.NAME FROM USERS " +
                "JOIN USER_TO_ROLE UTR ON U.ID = UTR.FK_USER_ID " +
                "JOIN ROLES R ON UTR.FK_ROLE_ID = R.NAME " +
                "WHERE U.TOKEN = ?";
        return getUser(query, token);
    }

    @Override
    public User findByEmail(String email) {
        String query = "SELECT ID, EMAIL, PASSWORD, TOKEN, FIRST_NAME, LAST_NAME FROM USERS WHERE EMAIL = ?";
        return getUser(query, email);
    }

    private User getUser(String query, String param) {
        PreparedStatement statement;
        ResultSet resultSet;
        User user = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, param);
            resultSet = statement.executeQuery();
            user = resultSet.next() ? getUser(resultSet) : null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    private User getUserWithRole(ResultSet resultSet) throws SQLException {
        User user = new User(
                resultSet.getLong("ID"),
                resultSet.getString("EMAIL"),
                resultSet.getString("PASSWORD"),
                resultSet.getString("TOKEN"),
                resultSet.getString("FIRST_NAME"),
                resultSet.getString("LAST_NAME")
        );

        while (!resultSet.isAfterLast()) {
            Role role = Role.of(resultSet.getString(7));
            user.addRole(role);
            resultSet.next();
        }
        return null;
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("ID"),
                resultSet.getString("EMAIL"),
                resultSet.getString("TOKEN"),
                resultSet.getString("PASSWORD"),
                resultSet.getString("FIRST_NAME"),
                resultSet.getString("LAST_NAME")
        );
    }
}
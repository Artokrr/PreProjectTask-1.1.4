package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;


    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createUsersTable() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("create table if not exists users (\n" +
                            "id int auto_increment not null, name varchar(50) not null, lastname varchar(50)\n" +
                            " not null, age tinyint not null, primary key(id)\n" +
                            ")");
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void dropUsersTable() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("drop table if exists users");
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("insert into users(name, lastName, age) values (?, ?, ?)");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            connection.commit();
            System.out.printf("User с именем - %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("delete from users where id = ?");
            statement.setLong(1, id);
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            PreparedStatement statement = connection
                    .prepareStatement("select * from users");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;

    }

    public void cleanUsersTable() {
        try {
            PreparedStatement statement = connection
                    .prepareStatement("truncate table users");
            statement.execute();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

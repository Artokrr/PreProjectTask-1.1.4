package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;

public class Main {
    static final UserService userService = new UserServiceImpl();
    private static final String testName = "Ivan";
    private static final String testLastName = "Ivanov";
    private static final byte testAge = 5;
    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser(testName, testLastName, testAge);
        userService.saveUser("Nikolai", "Milkovsky", (byte) 20);
        userService.saveUser("Artem", "Frolov", (byte) 18);
        userService.saveUser("German", "Sevostyanov", (byte) 25);
        List<User> usersList = userService.getAllUsers();
        System.out.println(usersList);
        userService.cleanUsersTable();
        userService.dropUsersTable();



    }
}

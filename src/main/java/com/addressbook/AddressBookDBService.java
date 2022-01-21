package com.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

    private Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/AddressBook?useSSL=false";
        String userName = "root";
        String password = "srivarun";
        Connection connection;
        System.out.println("Connecting to database : " +jdbcURL);
        connection = DriverManager.getConnection(jdbcURL,userName,password);
        System.out.println("Connection is successful!!!" + connection);
        return connection;
    }


    public List<AddressBookData> readData() {
            String sql = "SELECT * FROM Contacts; ";
            List<AddressBookData> addressBookList = new ArrayList<>();
            try (Connection connection = this.getConnection()){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while(resultSet.next()){
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    String state = resultSet.getString("state");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    addressBookList.add(new AddressBookData(id, name, address, state, date));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return addressBookList;
        }
    }


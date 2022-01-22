package com.addressbook;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddressBookDBService {

    private static AddressBookDBService addressBookDBService;
    private PreparedStatement addressBookDataStatement;

    private AddressBookDBService(){
    }

    public static AddressBookDBService getInstance(){
        if (addressBookDBService == null){
            addressBookDBService = new AddressBookDBService();
        }
        return addressBookDBService;
    }

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
            return this.getAddressBookDataUsingDB(sql);
        }

    public List<AddressBookData> getContactForDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = String.format("SELECT *FROM Contacts WHERE date BETWEEN '%s' AND '%s';" ,
                Date.valueOf(startDate), Date.valueOf(endDate));
        return this.getAddressBookDataUsingDB(sql);
    }

    private List<AddressBookData> getAddressBookDataUsingDB(String sql) {
        List<AddressBookData> addressBookList = new ArrayList<>();
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressBookList;
    }

    public List<AddressBookData> getAddressBookData(String name) {
        List<AddressBookData> addressBookList = null;
        if(this.addressBookDataStatement == null)
            this.prepareStatementForAddressData();
        try{
            addressBookDataStatement.setString(1, name);
            ResultSet resultSet = addressBookDataStatement.executeQuery();
            addressBookList = this.getAddressBookData(resultSet);
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return addressBookList;
    }
    private void prepareStatementForAddressData() {
        try{
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM Contacts WHERE name = ?";
            addressBookDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    private List<AddressBookData> getAddressBookData(ResultSet resultSet) {
        List<AddressBookData> addressBookList = new ArrayList<>();
        try{
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String state = resultSet.getString("state");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                addressBookList.add(new AddressBookData(id, name, address, state, date));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return addressBookList;
    }

    public int updateContactAddress(String name, String address) {
        return this.updateContactUsingStatement(name, address);
    }
    private int updateContactUsingStatement(String name, String address) {
        String sql = String.format("UPDATE Contacts SET address = '%s' where name = '%s';", address, name);
        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e ){
            e.printStackTrace();
        }
        return 0;
    }

    public AddressBookData addContactToAddressBook(int id, String name, String address, String state, LocalDate date) {
        AddressBookData addressBookData = null;
        String sql = String.format("INSERT INTO Contacts (id,name, address, state, date) VALUES ('%s','%s','%s','%s','%s')",id,name,address,state,date);
        try (Connection connection = this.getConnection()){
            Statement statement = connection.createStatement();
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if(rowAffected == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) id = resultSet.getInt(1);
            }
            addressBookData = new AddressBookData (id,name,address,state,date);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return addressBookData;
    }
}


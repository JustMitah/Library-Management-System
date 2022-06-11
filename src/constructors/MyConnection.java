/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constructors;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
public class MyConnection {
public Connection connection;
 
    public Connection getConnection(){
        String dbName = "books";
        String userName = "root";
        String password = "";
    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://localhost/";
        connection = DriverManager.getConnection(connectionURL+dbName,userName,password);
    } catch (ClassNotFoundException | SQLException e){
        
    }
        return connection;

}}

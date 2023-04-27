package com.example.ecommerce;
import java.sql.*;
///imppp

public class DbConnection {
    //url or location    gmail
    //username/userid
    //password
    ///required for dbconnection
    private  final String dburl="jdbc:mysql://localhost:3306/ecommerce";
    private   final String userName="root";
    private final String password="12345";


    ////////////imp
    private Statement getStatement(){
        try{
            Connection connection =DriverManager.getConnection(dburl,userName,password);
            return connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    ///impppppp
    public ResultSet getQueryTable(String query){
        try{
            Statement statement=getStatement();
            return statement.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int updateDatabase(String query){
        try{
            Statement statement=getStatement();
            return statement.executeUpdate(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        DbConnection conn=new DbConnection();
        ResultSet rs=conn.getQueryTable("SELECT * FROM customer");
        if(rs!=null){
            System.out.println("Connection Succesful");
        }
        else System.out.println("Connection Failed");
    }
}

package com.example.myownapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionUtil {





    static Connection conn = null;
    public static Connection connectdb()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mclab","root","");
            System.out.println("connection established " + conn);
            return conn;
        }
        catch(Exception e)
        {

            e.printStackTrace();
            return null;
        }
    }











    //Close Connection
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();

                System.out.println("connection closed");
            }
        } catch (Exception e){
            throw e;
        }
    }


















}

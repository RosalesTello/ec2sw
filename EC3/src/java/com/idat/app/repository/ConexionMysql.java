/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author jhonatanrosalestello
 */
public class ConexionMysql {
    //m
    public Connection conectar(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/modelo9nuevo?useSSL=false";
            String user = "root";
            String password = "jhonatan123456";
            con=DriverManager.getConnection(url,user, password);
        }catch(SQLException ex){
             System.out.println("Error : " + ex.getMessage());
        }catch(ClassNotFoundException ex){
             System.out.println("Error : " + ex.getMessage());
        }catch(Exception ex){
             System.out.println("Error : " + ex.getMessage());
        }
        return con;
    }
    //DSDS
    
}

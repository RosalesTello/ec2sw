/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.daoImpl;

import com.idat.app.dao.IProductoDao;
import com.idat.app.entity.Producto;
import com.idat.app.repository.ConexionMysql;
import com.idat.app.service.ServiceProducto;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author jhonatanrosalestello
 */

///me trae los metodos de  Iproducto de la interface 
 
public class PersonaDaoImpl implements IProductoDao{

    
        
        
    
    @Override
    public List<Producto> operacionesLectura(String indicador, Producto p) {
        
        ConexionMysql connection = new ConexionMysql();
    List<Producto> lista = new ArrayList<>();
    Connection con = null;
    CallableStatement cst = null;
    ResultSet rs = null;
    String procedimiento = "{call sp_products(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

    try {
        con = connection.conectar();
        cst = con.prepareCall(procedimiento);
        
        // Setear los parámetros para evitar errores de entrada nulos
        cst.setString(1, indicador);
        cst.setInt(2, p.getProductCode());
        cst.setString(3, p.getProductName());
        cst.setInt(4, p.getProductLine());
        cst.setString(5, p.getProductScale());
        cst.setString(6, p.getProductVendor());
        cst.setString(7, p.getProductDescription());
        cst.setInt(8, p.getQuantityInStock());
        cst.setBigDecimal(9, p.getBuyPrice());
        cst.setBigDecimal(10, p.getMSRP());

        // Ejecutar y procesar ResultSet
        rs = cst.executeQuery();
        while (rs.next()) {
            Producto objProducto = new Producto();
            objProducto.setProductCode(rs.getInt(1));
            objProducto.setProductName(rs.getString(2));
            objProducto.setProductLine(rs.getInt(3));
            objProducto.setProductScale(rs.getString(4));
            objProducto.setProductVendor(rs.getString(5));
            objProducto.setProductDescription(rs.getString(6));
            objProducto.setQuantityInStock(rs.getInt(7));
            objProducto.setBuyPrice(rs.getBigDecimal(8));
            objProducto.setMSRP(rs.getBigDecimal(9));
            lista.add(objProducto);
        }
    } catch (SQLException ex) {
        System.out.println("Error: " + ex.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (cst != null) cst.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            System.out.print("Error al cerrar la conexión: " + ex.getMessage());
        }
    }
    return lista;
}

    @Override
    public int agregarProducto(String indicador, Producto p) {
        ConexionMysql connection = new ConexionMysql(); // Nombre de la conexión
        Connection con = null;
        CallableStatement cst = null;
        int procesar = -1;
        String procedimiento = "{call sp_products(?,?,?,?,?,?,?,?,?,?)}"; // Llamada al procedimiento

        try {
            con = connection.conectar();
            cst = con.prepareCall(procedimiento);
            cst.setString(1, indicador);
            cst.setInt(2, p.getProductLine());
            cst.setString(3, p.getProductName());
            cst.setInt(4, p.getProductLine());
            cst.setString(5, p.getProductScale());
            cst.setString(6, p.getProductVendor());
            cst.setString(7, p.getProductDescription());
            cst.setInt(8, p.getQuantityInStock());
            cst.setBigDecimal(9, BigDecimal.ZERO);
            cst.setBigDecimal(10, BigDecimal.ZERO);
            procesar = cst.executeUpdate(); // Ejecutar la operación de escritura

        } catch (SQLException ex) {
            System.out.println("operacionesEscritura - Error: " + ex.getMessage());
        } finally {
            try {
                if (cst != null) {
                    cst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.print("Error: " + ex.getMessage());
            }
        }
        return procesar; //To change body of generated methods, choose Tools | Templates.
    }
    
    
       
    
    

    
    
    
}

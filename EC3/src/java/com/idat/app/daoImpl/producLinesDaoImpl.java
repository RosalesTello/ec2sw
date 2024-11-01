/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.daoImpl;

import com.idat.app.dao.IproductLinesDao;
import com.idat.app.entity.productLines;
import com.idat.app.repository.ConexionMysql;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author jhonatanrosalestello
 */

//te trae automatico expecificanadole clase abstracta
public class producLinesDaoImpl implements IproductLinesDao {

    @Override
    public int operacionesEscritura(String indicador, productLines p) {
        ConexionMysql connection = new ConexionMysql(); // Nombre de la conexión
        Connection con = null;
        //ResultSet rs = null;
        CallableStatement cst = null;
        int procesar = -1;
        String procedimiento = "{call sp_productlines(?,?,?,?,?)}"; // Llamada al procedimiento

        try {
            con = connection.conectar();
            cst = con.prepareCall(procedimiento);
            cst.setString(1, indicador);
            cst.setInt(2, p.getProductLine());
            cst.setString(3, p.getTextDescription());
            cst.setString(4, p.getHtmlDescription());
            cst.setString(5, p.getImage());
            procesar=cst.executeUpdate();
           /* rs=cst.executeQuery();
            
// Ejecutar la operación de escritura
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
*/
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
        return procesar; // Retornar el resultado de la operación
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<productLines> operacionesLectura(String indicador, productLines p) {
        ConexionMysql connection = new ConexionMysql();
        List<productLines> lista = new ArrayList<>();
        Connection con = null;
        CallableStatement cst = null;
        ResultSet rs = null;//NOMBRE DEL PROCEDIMIENTO EN SI
        String procedimiento = "{call sp_productlines(?,?,?,?,?)}"; // Llamada al procedimiento
        //LA MISMA CANTIDAD DE ? 
        try {
            con = connection.conectar();
            cst = con.prepareCall(procedimiento);
            cst.setString(1, indicador);
            cst.setInt(2, p.getProductLine());
            cst.setString(3, p.getTextDescription());
            cst.setString(4, p.getHtmlDescription()); // No se utiliza para buscar
            cst.setString(5, p.getImage()); // No se utiliza para buscar
            rs = cst.executeQuery(); // Ejecutar la operación de lectura
            
            while (rs.next()) {
                productLines objProductLine = new productLines();
                objProductLine.setProductLine(rs.getInt(1));
                objProductLine.setTextDescription(rs.getString(2));
                objProductLine.setHtmlDescription(rs.getString(3));
                objProductLine.setImage(rs.getString(4));
                lista.add(objProductLine);
            }
        } catch (SQLException ex) {
            System.out.println("operacionesLectura - Error: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
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
        return lista; //To change body of generated methods, choose Tools | Templates.
    }
    

    
    
    
    
    
}

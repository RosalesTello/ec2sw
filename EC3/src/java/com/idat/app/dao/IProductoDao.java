/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.dao;

import com.idat.app.entity.Producto;
import java.util.List;


/**
 *
 * @author jhonatanrosalestello
 */


public interface IProductoDao {
    
    public int agregarProducto(String indicador,Producto p); //aca con service 
    
    public List<Producto>operacionesLectura(String indicador, Producto p);
    
    
}

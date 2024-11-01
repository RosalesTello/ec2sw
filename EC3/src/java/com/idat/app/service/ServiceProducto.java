/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.service;

import com.idat.app.dao.IProductoDao;
import com.idat.app.daoImpl.PersonaDaoImpl;
import com.idat.app.entity.Producto;
import java.util.List;

/**
 *
 * @author jhonatanrosalestello
 */
public class ServiceProducto {
    
    public int operacionesEscritura(String indicador, Producto pe){
        
        IProductoDao dao =new PersonaDaoImpl();// el retornocambia a int
        return dao.agregarProducto(indicador, pe);
        
 
    }
    
    public List <Producto>operacionesLectura(String indicador, Producto p){
        
        IProductoDao dao = new PersonaDaoImpl();
        return dao.operacionesLectura(indicador, p);
    
    }
    //dd
    
}

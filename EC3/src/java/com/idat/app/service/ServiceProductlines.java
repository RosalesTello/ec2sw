/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.service;

import com.idat.app.dao.IProductoDao;
import com.idat.app.dao.IproductLinesDao;
import com.idat.app.daoImpl.PersonaDaoImpl;
import com.idat.app.daoImpl.producLinesDaoImpl;
import com.idat.app.entity.productLines;
import java.util.List;

/**
 *
 * @author jhonatanrosalestello
 */
public class ServiceProductlines {
    
    public int operacionesEscritura(String indicador, productLines pe){
        
        IproductLinesDao dao =new producLinesDaoImpl();// el retornocambia a int
        return dao.operacionesEscritura(indicador, pe);
        
        
        
    }
    
    public List <productLines>operacionesLectura(String indicador, productLines p){
        
        IproductLinesDao dao = new producLinesDaoImpl();
        return dao.operacionesLectura(indicador, p);
    
    }
    
    
}

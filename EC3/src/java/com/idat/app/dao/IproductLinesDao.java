/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.dao;

import com.idat.app.entity.productLines;
import java.util.List;

/**
 *
 * @author jhonatanrosalestello
 */
public interface IproductLinesDao {
    
    public int operacionesEscritura(String indicador, productLines p);
    
    /// select
    public List<productLines>operacionesLectura(String indicador, productLines p);
}

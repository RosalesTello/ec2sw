/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.dao.usando;

import com.idat.app.dao.IProductoDao;
import com.idat.app.entity.Producto;
import com.idat.app.entity.productLines;
import com.idat.app.service.ServiceProductlines;
import com.idat.app.service.ServiceProducto;
import java.math.BigDecimal;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author jhonatanrosalestello
 */
@WebService(serviceName = "WSDProductlines")
public class WSDProductlines {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    @WebMethod(operationName = "listarPersona")
    public List<productLines> listadoPersona() {

        System.out.println("....");
        ServiceProductlines servicio = new ServiceProductlines();
        productLines p = new productLines();
        p.setProductLine(0);
        p.setTextDescription("");
        p.setHtmlDescription("");
        p.setImage("");
        List<productLines> list = servicio.operacionesLectura("listar", p);//NOMBRE DEL PROCEMINIENTO ALMACENADO PERO LA OPCION 
        return list;
    }

    

///eliminar
    @WebMethod(operationName = "eliminarProducto")
    public int eliminarProducto(@WebParam(name = "productCode") int productCode) {
    Producto productoEliminar = new Producto();
    productoEliminar.setProductCode(productCode);

    ServiceProducto servicio = new ServiceProducto();
    int resultado = servicio.operacionesEscritura("eliminar", productoEliminar);

    return resultado; // Devuelve el resultado de la operación
}


    
    
    
    

}

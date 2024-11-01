/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idat.app.dao.usando;

import com.idat.app.entity.Producto;
import com.idat.app.service.ServiceProductlines;
import com.idat.app.service.ServiceProducto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.persistence.EntityManager;

/**
 *
 * @author jhonatanrosalestello
 */
@WebService(serviceName = "WSProducto")
public class WSProducto {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
@WebMethod(operationName = "listarProducto")
public List<Producto> listarProducto() {
    System.out.println("Iniciando el método listarProducto...");

    // Crear una instancia de ServiceProducto
    ServiceProducto servicio = new ServiceProducto(); // Verifica que esto no sea null
    

    // Crear e inicializar un objeto Producto
    Producto p = new Producto();
    p.setProductCode(0); // Ajusta según sea necesario
    p.setProductName("");
    p.setProductLine(0);
    p.setProductScale("");
    p.setProductVendor("");
    p.setProductDescription("");
    p.setQuantityInStock(0);
    p.setBuyPrice(BigDecimal.ZERO); // Inicializa correctamente
    p.setMSRP(BigDecimal.ZERO); // Inicializa correctamente

    // Llamar al método de operacionesLectura
            List<Producto>list=servicio.operacionesLectura("listar", p);

    return list; // Retorna la lista de productos
}



@WebMethod(operationName = "agregarProducto")
public int agregarProducto(
    @WebParam(name = "indicador") String indicador,
    @WebParam(name = "productCode") int productCode,
    @WebParam(name = "productName") String productName,
    @WebParam(name = "productLine") int productLine,
    @WebParam(name = "productScale") String productScale,
    @WebParam(name = "productVendor") String productVendor,
    @WebParam(name = "productDescription") String productDescription,
    @WebParam(name = "quantityInStock") int quantityInStock,
    @WebParam(name = "buyPrice") BigDecimal buyPrice,
    @WebParam(name = "msrp") BigDecimal msrp) {

    // Crear un nuevo objeto Producto
    Producto nuevoProducto = new Producto();
    nuevoProducto.setProductCode(productCode);
    nuevoProducto.setProductName(productName);
    nuevoProducto.setProductLine(productLine);
    nuevoProducto.setProductScale(productScale);
    nuevoProducto.setProductVendor(productVendor);
    nuevoProducto.setProductDescription(productDescription);
    nuevoProducto.setQuantityInStock(quantityInStock);
    nuevoProducto.setBuyPrice(buyPrice);
    nuevoProducto.setMSRP(msrp);

    int resultado = -1; // Valor predeterminado en caso de error
    try {
        // Llamar al servicio para agregar el nuevo producto
        ServiceProducto servicio = new ServiceProducto();
        resultado = servicio.operacionesEscritura(indicador, nuevoProducto);

        if (resultado == -1) {
            System.err.println("Error en operacionesEscritura: El producto no fue agregado.");
        } else {
            System.out.println("Producto agregado exitosamente. ID: " + productCode);
        }
    } catch (Exception ex) {
        System.err.println("Error al intentar agregar el producto: " + ex.getMessage());
        ex.printStackTrace(); // Mostrar más detalles del error
    }

    // Retornar el resultado de la operación
    return resultado;
}






}

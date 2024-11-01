CREATE DATABASE modelo9nuevo;

USE modelo9nuevo;

CREATE TABLE productlines (
    productLine INT PRIMARY KEY,                  -- Línea de Producto (Clave Primaria, tipo entero)
    textDescription TEXT,                         -- Descripción en Texto
    htmlDescription TEXT,                         -- Descripción en HTML
    image VARCHAR(255)                            -- Ruta de la Imagen
);

-- 2. Tabla offices (Oficinas) - No tiene dependencias
CREATE TABLE offices (
    officeCode INT PRIMARY KEY,                   -- Código de Oficina (Clave Primaria, tipo entero)
    city VARCHAR(50),                             -- Ciudad
    phone VARCHAR(50),                            -- Teléfono
    addressLine1 VARCHAR(50),                     -- Dirección Línea 1
    addressLine2 VARCHAR(50),                     -- Dirección Línea 2
    state VARCHAR(50),                            -- Estado/Provincia
    country VARCHAR(50),                          -- País
    postalCode VARCHAR(15),                       -- Código Postal
    territory VARCHAR(50)                         -- Territorio
);

-- 3. Tabla employees (Empleados) - Depende de offices y self-reference
CREATE TABLE employees (
    employeeNumber INT PRIMARY KEY,               -- Número de Empleado (Clave Primaria, tipo entero)
    lastName VARCHAR(50),                         -- Apellido
    firstName VARCHAR(50),                        -- Nombre
    extension VARCHAR(10),                        -- Extensión
    email VARCHAR(100),                           -- Correo Electrónico
    officeCode INT,                               -- Código de Oficina (Referencia a offices)
    reportsTo INT,                                -- Reporta a (Auto-referencia a employees)
    jobTitle VARCHAR(50),                         -- Cargo
    FOREIGN KEY (officeCode) REFERENCES offices(officeCode),         -- Relación con Oficinas
    FOREIGN KEY (reportsTo) REFERENCES employees(employeeNumber)    -- Relación con Jefe
);

-- 4. Tabla products (Productos) - Depende de productlines
CREATE TABLE products (
    productCode INT PRIMARY KEY,                  -- Código de Producto (Clave Primaria, tipo entero)
    productName VARCHAR(50),                      -- Nombre del Producto
    productLine INT,                              -- Línea de Producto (Referencia a productlines)
    productScale VARCHAR(50),                     -- Escala del Producto
    productVendor VARCHAR(50),                    -- Proveedor del Producto
    productDescription TEXT,                      -- Descripción del Producto
    quantityInStock INT,                          -- Cantidad en Stock
    buyPrice DECIMAL(10,2),                       -- Precio de Compra
    MSRP DECIMAL(10,2),                           -- Precio Sugerido de Venta
    FOREIGN KEY (productLine) REFERENCES productlines(productLine)  -- Relación con Líneas de Productos
);

-- 5. Tabla customers (Clientes) - Depende de employees
CREATE TABLE customers (
    customerNumber INT PRIMARY KEY,               -- Número de Cliente (Clave Primaria, tipo entero)
    customerName VARCHAR(50),                     -- Nombre del Cliente
    contactLastName VARCHAR(50),                  -- Apellido del Contacto
    contactFirstName VARCHAR(50),                 -- Nombre del Contacto
    phone VARCHAR(50),                            -- Teléfono
    addressLine1 VARCHAR(50),                     -- Dirección Línea 1
    addressLine2 VARCHAR(50),                     -- Dirección Línea 2
    city VARCHAR(50),                             -- Ciudad
    state VARCHAR(50),                            -- Estado/Provincia
    postalCode VARCHAR(15),                       -- Código Postal
    country VARCHAR(50),                          -- País
    salesRepEmployeeNumber INT,                   -- Número de Empleado Representante (Referencia a employees)
    creditLimit DECIMAL(10,2),                   -- Límite de Crédito
    FOREIGN KEY (salesRepEmployeeNumber) REFERENCES employees(employeeNumber)  -- Relación con Empleado Vendedor
);

-- 6. Tabla orders (Pedidos) - Depende de customers
CREATE TABLE orders (
    orderNumber INT PRIMARY KEY,                  -- Número de Pedido (Clave Primaria, tipo entero)
    orderDate DATE,                               -- Fecha de Pedido
    requiredDate DATE,                            -- Fecha Requerida
    shippedDate DATE,                             -- Fecha de Envío
    status VARCHAR(15),                           -- Estado
    comments TEXT,                                -- Comentarios
    customerNumber INT,                           -- Número de Cliente (Referencia a customers)
    FOREIGN KEY (customerNumber) REFERENCES customers(customerNumber)  -- Relación con Clientes
);

-- 7. Tabla orderdetails (Detalles de Pedido) - Depende de orders y products
CREATE TABLE orderdetails (
    orderNumber INT,                              -- Número de Pedido (Parte de Clave Primaria, Referencia a orders)
    productCode INT,                              -- Código de Producto (Parte de Clave Primaria, Referencia a products)
    quantityOrdered INT,                          -- Cantidad Ordenada
    priceEach DECIMAL(10,2),                     -- Precio Unitario
    orderLineNumber INT,                          -- Número de Línea de Pedido
    PRIMARY KEY (orderNumber, productCode),      -- Clave Primaria Compuesta
    FOREIGN KEY (orderNumber) REFERENCES orders(orderNumber),          -- Relación con Pedidos
    FOREIGN KEY (productCode) REFERENCES products(productCode)         -- Relación con Productos
);

-- 8. Tabla payments (Pagos) - Depende de customers
CREATE TABLE payments (
    customerNumber INT,                           -- Número de Cliente (Parte de Clave Primaria, Referencia a customers)
    checkNumber VARCHAR(50),                      -- Número de Cheque (Parte de Clave Primaria)
    paymentDate DATE,                             -- Fecha de Pago
    amount DECIMAL(10,2),                         -- Monto
    PRIMARY KEY (customerNumber, checkNumber),    -- Clave Primaria Compuesta
    FOREIGN KEY (customerNumber) REFERENCES customers(customerNumber)  -- Relación con Clientes
);


-- sp


CALL sp_productlines('agregar', 2, 'Descripción del Producto', '<p>Descripción HTML del Producto</p>', 'ruta/a/la/imagen.jpg');
CALL sp_productlines('leer', 1, NULL, NULL, NULL);
call sp_productlines ('listar', NULL, NULL, NULL, NULL);  -- P

DROP PROCEDURE sp_productlines;
-- PROCEDIMIENTO LA TABLA PRODUctos

DELIMITER //

CREATE PROCEDURE sp_products (
    IN action VARCHAR(10),                 -- Acción del CRUD: 'agregar', 'leer', 'actualizar', 'eliminar', 'listar'
    IN p_productCode INT,                  -- Código de Producto
    IN p_productName VARCHAR(50),          -- Nombre del Producto
    IN p_productLine INT,                  -- Línea de Producto (Referencia a productlines)
    IN p_productScale VARCHAR(50),         -- Escala del Producto
    IN p_productVendor VARCHAR(50),        -- Proveedor del Producto
    IN p_productDescription TEXT,          -- Descripción del Producto
    IN p_quantityInStock INT,              -- Cantidad en Stock
    IN p_buyPrice DECIMAL(10,2),           -- Precio de Compra
    IN p_MSRP DECIMAL(10,2)                -- Precio Sugerido de Venta
)
BEGIN
    -- Acción para agregar un nuevo producto
    IF action = 'agregar' THEN
        INSERT INTO products (productCode, productName, productLine, productScale, productVendor, productDescription, quantityInStock, buyPrice, MSRP)
        VALUES (p_productCode, p_productName, p_productLine, p_productScale, p_productVendor, p_productDescription, p_quantityInStock, p_buyPrice, p_MSRP);
        SELECT 'Producto agregado con éxito.' AS mensaje;

    -- Acción para leer un producto específico
    ELSEIF action = 'leer' THEN
        SELECT * FROM products
        WHERE productCode = p_productCode;

    -- Acción para actualizar un producto existente
    ELSEIF action = 'actualizar' THEN
        UPDATE products
        SET productName = p_productName,
            productLine = p_productLine,
            productScale = p_productScale,
            productVendor = p_productVendor,
            productDescription = p_productDescription,
            quantityInStock = p_quantityInStock,
            buyPrice = p_buyPrice,
            MSRP = p_MSRP
        WHERE productCode = p_productCode;
        SELECT 'Producto actualizado con éxito.' AS mensaje;

    -- Acción para eliminar un producto
    ELSEIF action = 'eliminar' THEN
        DELETE FROM products
        WHERE productCode = p_productCode;
        SELECT 'Producto eliminado con éxito.' AS mensaje;

    -- Acción para listar todos los productos
    ELSEIF action = 'listar' THEN
        SELECT * FROM products;

    -- Manejo de acciones no válidas
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Acción no válida. Las acciones válidas son: agregar, leer, actualizar, eliminar, listar.';
    END IF;
END //

DELIMITER ;

-- PRUEBAS 

CALL sp_products('agregar', 1, 'LAVADORA', 1, '1.50', 'TOTUS', 'LA MEJOR LABADORA', 100, 25.00, 35.00);
CALL sp_products('leer', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
CALL sp_products('eliminar', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


SELECT * FROM products
DELIMITER //

CREATE PROCEDURE sp_productlines (
    IN action VARCHAR(10),             -- Acción del CRUD: 'agregar', 'leer', 'actualizar', 'eliminar', 'listar'
	IN p_productLine INT,              -- Línea de Producto
    IN p_textDescription TEXT,         -- Descripción en Texto
    IN p_htmlDescription TEXT,         -- Descripción en HTML
    IN p_image VARCHAR(255)            -- Ruta de la Imagen
)
BEGIN
    -- Acción para agregar una nueva línea de producto
    IF action = 'agregar' THEN
        INSERT INTO productlines (productLine, textDescription, htmlDescription, image)
        VALUES (p_productLine, p_textDescription, p_htmlDescription, p_image);
        SELECT 'Línea de producto agregada con éxito.' AS mensaje;

    -- Acción para leer una línea de producto específica
    ELSEIF action = 'leer' THEN
        SELECT * FROM productlines
        WHERE productLine = p_productLine;

    -- Acción para actualizar una línea de producto existente
    ELSEIF action = 'actualizar' THEN
        UPDATE productlines
        SET textDescription = p_textDescription,
            htmlDescription = p_htmlDescription,
            image = p_image
        WHERE productLine = p_productLine;
        SELECT 'Línea de producto actualizada con éxito.' AS mensaje;

    -- Acción para eliminar una línea de producto
    ELSEIF action = 'eliminar' THEN
        DELETE FROM productlines
        WHERE productLine = p_productLine;
        SELECT 'Línea de producto eliminada con éxito.' AS mensaje;

    -- Acción para listar todas las líneas de producto
    ELSEIF action = 'listar' THEN
        SELECT * FROM productlines;

    -- Manejo de acciones no válidas
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Acción no válida. Las acciones válidas son: agregar, leer, actualizar, eliminar, listar.';
    END IF;
END //
DELIMITER ;


select * from productlines



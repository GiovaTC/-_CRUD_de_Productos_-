# -_CRUD_de_Productos_- :.
# CRUD de Productos:

<img width="1254" height="1254" alt="image" src="https://github.com/user-attachments/assets/0cb1e14d-dcba-4759-8c0f-03857d4e3f5e" />  

<img width="2556" height="1032" alt="image" src="https://github.com/user-attachments/assets/abad68f3-9bea-4e8b-a22a-8f233b728452" />    

<img width="2552" height="1031" alt="image" src="https://github.com/user-attachments/assets/623cd2d6-bcc8-4360-8c8d-13c71f9a02ce" />        

```

CRUD de productos **lo más básico posible** utilizando:

- Java 21
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- MySQL
- Thymeleaf

Con únicamente un campo **nombre** para simplificar el código.

---

# Estructura del Proyecto

```text
CRUDProductos
│
├── pom.xml
├── script.sql
│
└── src
    └── main
        ├── java
        │
        ├── controller
        │      ProductoController.java
        │
        ├── model
        │      Producto.java
        │
        ├── repository
        │      ProductoRepository.java
        │
        ├── service
        │      ProductoService.java
        │
        ├── serviceImpl
        │      ProductoServiceImpl.java
        │
        └── crudproductos.CrudProductosApplication.java
        │
        └── resources
            ├── templates
            │      index.html
            │      nuevo.html
            │      editar.html
            │
            └── application.properties
```

---

# pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.productos</groupId>
    <artifactId>CRUDProductos</artifactId>
    <version>1.0</version>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.3</version>
    </parent>

    <properties>
        <java.version>21</java.version>
    </properties>

    <dependencies>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>

    </dependencies>

</project>
```

---

# script.sql

```sql
CREATE DATABASE crudproductos;

USE crudproductos;

CREATE TABLE producto(

id INT AUTO_INCREMENT PRIMARY KEY,

nombre VARCHAR(100)

);
```

---

# application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/crudproductos

spring.datasource.username=root

spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true
```

---

# Producto.java

```java
package model;

import jakarta.persistence.*;

@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    public Producto() {
    }

    public Producto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
```

---

# ProductoRepository.java

```java
package repository;

import crudproductos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

}
```

---

# ProductoService.java

```java
package service;

import crudproductos.model.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> listar();

    void guardar(Producto producto);

    Producto buscar(Integer id);

    void eliminar(Integer id);

}
```

---

# ProductoServiceImpl.java

```java
package serviceImpl;

import crudproductos.model.Producto;
import crudproductos.repository.ProductoRepository;
import crudproductos.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    ProductoRepository repository;

    @Override
    public List<Producto> listar() {
        return repository.findAll();
    }

    @Override
    public void guardar(Producto producto) {
        repository.save(producto);
    }

    @Override
    public Producto buscar(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

}
```

---

# ProductoController.java

```java
package controller;

import crudproductos.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import crudproductos.service.ProductoService;

@Controller
public class ProductoController {

    @Autowired
    ProductoService service;

    @GetMapping("/")
    public String inicio(Model model) {

        model.addAttribute("lista", service.listar());

        return "index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("producto", new Producto());

        return "nuevo";
    }

    @PostMapping("/guardar")
    public String guardar(Producto producto) {

        service.guardar(producto);

        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {

        model.addAttribute("producto", service.buscar(id));

        return "editar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {

        service.eliminar(id);

        return "redirect:/";
    }

}
```

---

# crudproductos.CrudProductosApplication.java

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class crudproductos.CrudProductosApplication {

    public static void main(String[] args) {

        SpringApplication.run(crudproductos.CrudProductosApplication.class,args);

    }

}
```

---

# index.html

```html
<!DOCTYPE html>

<html xmlns:th="http://www.thymeleaf.org">

<head>

<meta charset="UTF-8">

<title>Productos</title>

</head>

<body>

<h1>CRUD Productos</h1>

<a href="/nuevo">Nuevo Producto</a>

<table border="1">

<tr>

<th>ID</th>

<th>Nombre</th>

<th>Acciones</th>

</tr>

<tr th:each="p:${lista}">

<td th:text="${p.id}"></td>

<td th:text="${p.nombre}"></td>

<td>

<a th:href="@{/editar/{id}(id=${p.id})}">Editar</a>

<a th:href="@{/eliminar/{id}(id=${p.id})}">Eliminar</a>

</td>

</tr>

</table>

</body>

</html>
```

---

# nuevo.html

```html
<!DOCTYPE html>

<html xmlns:th="http://www.thymeleaf.org">

<head>

<meta charset="UTF-8">

<title>Nuevo</title>

</head>

<body>

<h2>Nuevo Producto</h2>

<form action="/guardar" method="post" th:object="${producto}">

Nombre

<input type="text" th:field="*{nombre}">

<br><br>

<button type="submit">

Guardar

</button>

</form>

</body>

</html>
```

---

# editar.html

```html
<!DOCTYPE html>

<html xmlns:th="http://www.thymeleaf.org">

<head>

<meta charset="UTF-8">

<title>Editar</title>

</head>

<body>

<h2>Editar Producto</h2>

<form action="/guardar" method="post" th:object="${producto}">

<input type="hidden" th:field="*{id}">

Nombre

<input type="text" th:field="*{nombre}">

<br><br>

<button type="submit">

Actualizar

</button>

</form>

</body>

</html>
```

---

# Resultado

Este proyecto implementa el **CRUD más básico posible**.

## Características

- ✅ Listar productos.
- ✅ Agregar un producto.
- ✅ Editar un producto.
- ✅ Eliminar un producto.
- ✅ Un solo atributo (**nombre**) para mantener el código sencillo.
- ✅ Uso de Spring Data JPA sin configuraciones adicionales.
- ✅ Compatible con **Java 21**, **Spring Boot 3**, **MySQL 8** e **IntelliJ IDEA** .
- :. . / .  

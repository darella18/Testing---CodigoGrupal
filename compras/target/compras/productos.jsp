<!-- filepath: compras/src/main/webapp/productos.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.sinfloo.controlador.ProductoListServlet.Producto"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catálogo de Productos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
    <style>
        .producto { border: 1px solid #ccc; padding: 10px; margin: 10px; display: inline-block; width: 250px; vertical-align: top;}
        .producto img { max-width: 100%; height: 150px; object-fit: cover; }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <h2>Catálogo de Productos</h2>
        <div class="row">
        <%
            List<Producto> productos = (List<Producto>) request.getAttribute("productos");
            if (productos != null && !productos.isEmpty()) {
                for (Producto p : productos) {
        %>
            <div class="producto">
                <img src="<%= p.urlImagen %>" alt="Imagen de <%= p.nombre %>"/><br>
                <strong><%= p.nombre %></strong><br>
                <span><%= p.descripcion %></span><br>
                <span>Precio: $<%= p.precio %></span><br>
                <span>Stock: <%= p.stock %></span>
                <form action="AgregarCarritoServlet" method="post" style="margin-top:10px;">
                    <input type="hidden" name="id_producto" value="<%= p.id %>"/>
                    <button type="submit" class="btn btn-outline-info btn-sm">Agregar al carrito</button>
                </form>
            </div>
        <%
                }
            } else {
        %>
            <p>No hay productos disponibles.</p>
        <%
            }
        %>
        </div>
    </div>
</body>
</html>
<!-- filepath: compras/src/main/webapp/carrito.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.sinfloo.controlador.CarritoServlet.ItemCarrito"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Mi Carrito</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        img { max-width: 80px; max-height: 80px; }
        .eliminar-group { margin-top: 20px; }
        .eliminar-group form { display: inline-block; margin-right: 10px; }
    </style>
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
    <h2>Mi Carrito</h2>
    <a href="productos">Volver al catálogo</a>
    <%
        List<ItemCarrito> items = (List<ItemCarrito>) request.getAttribute("items");
        Double total = (Double) request.getAttribute("total");
        if (items != null && !items.isEmpty()) {
    %>
    <form action="ActualizarCarritoServlet" method="post">
        <table class="table table-bordered">
            <tr>
                <th>Imagen</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
            </tr>
            <%
                for (ItemCarrito item : items) {
            %>
            <tr>
                <td><img src="<%= item.urlImagen %>" alt="Imagen de <%= item.nombre %>"/></td>
                <td><%= item.nombre %></td>
                <td><%= item.descripcion %></td>
                <td>$<%= item.precioUnitario %></td>
                <td>
                    <input type="number" name="cantidades_<%= item.idProducto %>" value="<%= item.cantidad %>" min="1" style="width:60px;"/>
                </td>
                <td>$<%= item.subtotal %></td>
            </tr>
            <%
                }
            %>
            <tr>
                <td colspan="5" style="text-align:right;"><strong>Total:</strong></td>
                <td><strong>$<%= total %></strong></td>
            </tr>
        </table>
        <button type="submit" class="btn btn-primary">Actualizar cantidades</button>
    </form>
    <div class="card eliminar-group">
        <div class="card-body">
            <strong>Eliminar productos:</strong>
            <%
                for (ItemCarrito item : items) {
            %>
                <form action="EliminarCarritoServlet" method="post">
                    <input type="hidden" name="id_producto" value="<%= item.idProducto %>"/>
                    <button type="submit" class="btn btn-danger btn-sm mb-1">
                        Eliminar <%= item.nombre %>
                    </button>
                </form>
            <%
                }
            %>
        </div>
    </div>
    <form action="ComprarServlet" method="post" style="margin-top:20px;">
        <button type="submit" class="btn btn-success">Comprar</button>
    </form>
    <%
        } else {
    %>
        <p>Tu carrito está vacío.</p>
    <%
        }
    %>
    </div>
</body>
</html>
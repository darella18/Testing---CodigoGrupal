<!-- filepath: compras/src/main/webapp/compra_error.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error de compra</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <h2>Error al confirmar la compra</h2>
        <p>No hay suficiente stock para el producto: <strong><%= request.getAttribute("nombreProducto") %></strong></p>
        <p>Stock disponible: <strong><%= request.getAttribute("stockActual") %></strong></p>
        <a href="carrito" class="btn btn-warning">Volver al carrito</a>
    </div>
</body>
</html>
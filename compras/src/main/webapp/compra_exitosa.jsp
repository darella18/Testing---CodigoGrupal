<!-- filepath: compras/src/main/webapp/compra_exitosa.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Compra Exitosa</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <h2>¡Compra realizada con éxito!</h2>
        <p>Gracias por tu compra. Tu carrito ha sido confirmado y el stock actualizado.</p>
        <a href="productos" class="btn btn-success">Volver al catálogo</a>
    </div>
</body>
</html>
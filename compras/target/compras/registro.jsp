<!-- filepath: compras/src/main/webapp/registro.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro de Usuario</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <h2>Registro de Usuario</h2>
        <form action="RegistroServlet" method="post">
            <div class="form-group">
                <label>Nombre:</label>
                <input type="text" name="nombre" class="form-control" required />
            </div>
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" class="form-control" required />
            </div>
            <div class="form-group">
                <label>Contraseña:</label>
                <input type="password" name="password" class="form-control" required />
            </div>
            <button type="submit" class="btn btn-primary">Registrarse</button>
        </form>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger mt-2"><%= error %></div>
        <%
            }
        %>
        <p>¿Ya tienes cuenta? <a href="login.jsp">Inicia sesión aquí</a></p>
    </div>
</body>
</html>
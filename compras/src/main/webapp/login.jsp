<!-- filepath: compras/src/main/webapp/login.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.1.3/dist/css/bootstrap.min.css">
</head>
<body>
    <%@ include file="header.jsp" %>
    <div class="container">
        <h2>Iniciar sesión</h2>
        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label>Email:</label>
                <input type="email" name="email" class="form-control" required/>
            </div>
            <div class="form-group">
                <label>Contraseña:</label>
                <input type="password" name="password" class="form-control" required/>
            </div>
            <button type="submit" class="btn btn-primary">Entrar</button>
        </form>
        <p>¿No tienes cuenta? <a href="registro.jsp">Regístrate aquí</a></p>
        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <div class="alert alert-danger mt-2"><%= error %></div>
        <%
            }
        %>
    </div>
</body>
</html>
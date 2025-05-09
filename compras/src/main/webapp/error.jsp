<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2>¡Ups! Ha ocurrido un problema.</h2>
    <p style="color:red;">
        <%= request.getAttribute("errorMessage") %>
    </p>
    <a href="index.jsp">Volver al inicio</a>
</body>
</html>

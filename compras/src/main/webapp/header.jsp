<!-- filepath: compras/src/main/webapp/header.jsp -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
  <a class="navbar-brand" href="productos">Tienda Virtual</a>
  <div class="collapse navbar-collapse">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="productos">Catálogo</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="carrito">Carrito</a>
      </li>
    </ul>
    <ul class="navbar-nav">
      <%
        String usuario = (String) session.getAttribute("usuario");
        if (usuario == null) {
      %>
        <li class="nav-item"><a class="nav-link" href="login.jsp">Iniciar sesión</a></li>
      <%
        } else {
      %>
        <li class="nav-item"><span class="navbar-text text-light mr-2">Hola, <%= usuario %></span></li>
        <li class="nav-item"><a class="nav-link" href="LogoutServlet">Cerrar sesión</a></li>
      <%
        }
      %>
    </ul>
  </div>
</nav>
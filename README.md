# Tienda Virtual - Proyecto Java JSP/Servlets

## Requisitos

- Java JDK 17 o superior
- Apache Tomcat 11 (Windows Service Installer)
- MySQL Server
- Maven
- Visual Studio Code (VS Code) *(opcional, pero recomendado)*

---

## 1. Clonar el repositorio

git clone https://github.com/darella18/Testing---CodigoGrupal.git
cd Testing---CodigoGrupal

---

## 2. Instalar y configurar Apache Tomcat 11

### **Descargar e instalar Tomcat 11:**
- Ve a [https://tomcat.apache.org/download-11.cgi](https://tomcat.apache.org/download-11.cgi)
- Descarga el **Windows Service Installer** y sigue el asistente de instalación.
- Por defecto, Tomcat se instalará en:  
  `C:\Program Files\Apache Software Foundation\Tomcat 11.0\`

### **Iniciar Tomcat desde la terminal:**
- Abre **PowerShell** desde la terminal de VSCode.
- Ve a la carpeta `bin` de Tomcat, normalmente:

cd "C:\Program Files\Apache Software Foundation\Tomcat 11.0\bin"

- Inicia Tomcat con:

cmd /c catalina.bat run

*(Puedes detener Tomcat con ctrl+C)*

---

## 3. Configurar la base de datos

### **Crear la base de datos y tablas:**

1. Abre MySQL Workbench, DBeaver, o usa la terminal.
2. Ejecuta el script SQL que está en `database/bdcompras.sql`:

mysql -u root -p < database/bdcompras.sql

3. Verifica que la base de datos `bdcompras` y las tablas se hayan creado correctamente.

---

## 4. Configurar la conexión a la base de datos

- Abre el archivo `Conexion.java` en tu proyecto.
- Coloca tus credenciales de MySQL:

// Ejemplo de configuración
private final String url = "jdbc:mysql://localhost:3306/bdcompras";
private final String user = "root";
private final String pass = "TU_CONTRASEÑA";

---

## 5. Compilar y generar el archivo WAR

Desde la terminal de VS Code o cmd, en la raíz del proyecto:

mvn clean package

Esto generará el archivo `compras.war` en la carpeta `target/`.

---

## 6. Desplegar el WAR en Tomcat

- Copia el archivo `target/compras.war` a la carpeta:

C:\Program Files\Apache Software Foundation\Tomcat 11.0\webapps

- Si Tomcat está corriendo, el WAR se desplegará automáticamente.  
  Si no, inicia Tomcat como se explicó antes.

---

## 7. Acceder a la aplicación

Abre tu navegador y ve a:

```
http://localhost:8080/compras
```

---

## 8. Notas adicionales

- Si cambias el puerto de Tomcat, usa ese puerto en la URL.
- Si tienes problemas de tildes o caracteres raros, asegúrate de que **todos los archivos estén guardados como UTF-8**.
- Para detener Tomcat, puedes cerrar la ventana de la terminal o detener el servicio desde el monitor de servicios de Windows.

---

## 9. Usuarios y pruebas

- Regístrate como nuevo usuario desde la app.
- Agrega productos al carrito, prueba la compra y verifica el stock.

---
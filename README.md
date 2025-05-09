# Testing---CodigoGrupal

## Requisitos
- JDK 11 o superior
- Apache Maven
- MySQL

## Configuración
1. Configura la base de datos `bdcompras` en MySQL.
   - Usa el script SQL proporcionado para crear la base de datos y las tablas necesarias.
2. Actualiza las credenciales en `Conexion.java` si es necesario.

## Ejecución
### Desde la línea de comandos
1. Abre una terminal en el directorio del proyecto:
   cd ...\Testing---CodigoGrupal\compras
   NO DESDE:
   cd ...\Testing---CodigoGrupal

2. Compila el proyecto:
   mvn clean compile

3. Ejecuta la clase `MainApp`:
   mvn exec:java

### Desde un servidor
1. Ejecuta:
   mvn clean install
   
2. Despliega el archivo WAR generado en un servidor compatible con Jakarta EE 10 (GlassFish, Payara, etc.).
3. Accede a `http://localhost:8080/compras`.
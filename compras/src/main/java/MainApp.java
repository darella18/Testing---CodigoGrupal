import com.sinfloo.config.Conexion;
import com.sinfloo.modelo.Producto;
import com.sinfloo.modelo.ProductoDAO;
import com.sinfloo.modelo.ItemCarrito;

import java.sql.Connection;
import java.util.*;

public class MainApp {
    public static void main(String[] args) {
        // Inicializar la conexión a la base de datos
        Conexion conexion = new Conexion();
        Connection con = conexion.getConnection();
        if (con == null) {
            System.out.println("No se pudo conectar a la base de datos. Verifica la configuración.");
            return;
        }

        // Crear instancia de ProductoDAO
        ProductoDAO productoDAO = new ProductoDAO();

        // Listar productos
        System.out.println("Productos disponibles:");
        List<Producto> productos = productoDAO.listar();
        for (Producto producto : productos) {
            System.out.println(producto.getId() + " - " + producto.getNombre() + " - S/" + producto.getPrecio());
        }

        // Simular carrito de compras
        Map<Integer, ItemCarrito> carrito = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOpciones:");
            System.out.println("1. Agregar producto al carrito");
            System.out.println("2. Ver carrito");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingresa el ID del producto: ");
                    int idProducto = scanner.nextInt();
                    System.out.print("Ingresa la cantidad: ");
                    int cantidad = scanner.nextInt();

                    Producto productoSeleccionado = productos.stream()
                            .filter(p -> p.getId() == idProducto)
                            .findFirst()
                            .orElse(null);

                    if (productoSeleccionado == null) {
                        System.out.println("Producto no encontrado.");
                    } else {
                        carrito.put(idProducto, new ItemCarrito(productoSeleccionado, cantidad));
                        System.out.println("Producto agregado al carrito.");
                    }
                    break;

                case 2:
                    System.out.println("Carrito de compras:");
                    double total = 0;
                    for (ItemCarrito item : carrito.values()) {
                        System.out.println(item.getProducto().getNombre() + " - Cantidad: " + item.getCantidad() + " - Subtotal: S/" + item.getSubtotal());
                        total += item.getSubtotal();
                    }
                    System.out.println("Total: S/" + total);
                    break;

                case 3:
                    System.out.println("Gracias por usar el sistema. ¡Hasta luego!");
                    conexion.closeConnection();
                    return;

                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}

package ifp.kikeverea;

import ifp.kikeverea.modelo.Producto;
import ifp.kikeverea.modelo.ProductoDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final int CREAR_PRODUCTO = 1;
    private static final int LISTAR_PRODUCTOS = 2;
    private static final int MODIFICAR_PRODUCTO = 3;
    private static final int ELIMINAR_PRODUCTO = 4;
    private static final int SALIR = 0;

    private static final int MODIFICAR_NOMBRE = 1;
    private static final int MODIFICAR_PRECIO = 2;
    private static final int MODIFICAR_CANTIDAD = 3;

    private static final String MENU =
            "*******************************************\n" +
                    "\t\t\tGESTOR DE PRODUCTOS\n" +
            "******************************************* \n" +
            "1) Crear un producto\n" +
            "2) Listar todos los productos\n" +
            "3) Modificar un producto\n" +
            "4) Borrar un producto\n" +
            "0) Salir del programa";

    private static final String MENU_MODIFICACION =
            "Introduzca el campo que desea modificar del producto\n" +
            "1) Nombre\n" +
            "2) Precio\n" +
            "3) Cantidad";

    private static final InputUsuario inputUsuario =
            new InputUsuario(new Scanner(System.in), ValidadorNumeros.soloPositivos());

    private static ProductoDao dao;

    public static void main( String[] args )
    {
        try (Connection connexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", ""))
        {
            dao = new ProductoDao(connexion);
            int opcion;
            String resultado;

            do {
                System.out.println(MENU);
                opcion = inputUsuario.solicitarEntero("Opción: ");

                switch (opcion) {
                    case CREAR_PRODUCTO:
                        System.out.println("\nCrear producto");
                        resultado = crearProducto();
                        break;
                    case LISTAR_PRODUCTOS:
                        resultado = listarProductos();
                        break;
                    case MODIFICAR_PRODUCTO:
                        System.out.println("\nModificar producto");
                        resultado = modificarProducto();
                        break;
                    case ELIMINAR_PRODUCTO:
                        System.out.println("\nBorrar producto");
                        resultado = eliminarProducto();
                        break;
                    case SALIR:
                        resultado = "Cerrando programa";
                        break;
                    default:
                        resultado = "Opción errónea";
                        break;
                }

                // imprime el resultado
                System.out.println();
                System.out.println(resultado);
                System.out.println();
            }
            while (opcion != 0);
        }
        catch (SQLException e) {
            System.out.println("No se ha podido establecer una conexón con la base de datos");
            e.printStackTrace();
        }
    }

    private static String crearProducto() {
        String nombre = inputUsuario.solicitarTexto("Introduzca el nombre del producto: ");
        float precio = inputUsuario.solicitarDecimal("Introduzca el precio unitario del producto: ");
        int cantidad = inputUsuario.solicitarEntero("Introduzca la cantidad del producto: ");

        return  dao.insertar(new Producto(nombre, precio, cantidad)) ?
                "Producto creado correctamente" :
                "El producto no se ha podido crear";
    }

    private static String listarProductos() {
        return listarProductos(dao.consultarTodos());
    }

    private static String listarProductos(List<Producto> productos) {
        if (productos.isEmpty())
            return "Lista vacía";

        StringBuilder sb = new StringBuilder("Productos:\n");
        for(int i = 0; i < productos.size(); i++) {
            sb.append(i + 1);
            sb.append(" - ");
            sb.append(productos.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    private static String modificarProducto() {
        Producto producto = solicitarProducto(dao.consultarTodos());
        int opcionModificacion;

        if (producto == null)
            return "No hay productos para modificar";

        // imprime opciones
        System.out.println(MENU_MODIFICACION);

        opcionModificacion = inputUsuario.solicitarEntero("Escriba la opción: ");

        switch (opcionModificacion) {
            case MODIFICAR_NOMBRE:
                String nombre = inputUsuario.solicitarTexto("Introduzca el nuevo nombre del producto: ");
                producto.setNombre(nombre);
                break;
            case MODIFICAR_PRECIO:
                float precio = inputUsuario.solicitarDecimal("Introduzca el nuevo precio del producto: ");
                producto.setPrecio(precio);
                break;
            case MODIFICAR_CANTIDAD:
                int cantidad = inputUsuario.solicitarEntero("Introduzca la nueva cantidad del producto: ");
                producto.setCantidad(cantidad);
                break;
            default:
                return "Opcion errónea";
        }

        return  dao.modificar(producto) ?
                "Producto modificado correctamente" :
                "El producto no se ha podido modificar";
    }

    private static String eliminarProducto() {
        Producto producto = solicitarProducto(dao.consultarTodos());

        if (producto == null)
            return "No hay productos para borrar";

        return  dao.eliminar(producto) ?
                "Producto borrado correctamente" :
                "El producto no pudo ser borrado";
    }

    private static Producto solicitarProducto(List<Producto> productos) {
        if (productos.isEmpty())
            return null;

        Producto producto = null;
        while (producto == null) {
            // imprime la lista de todos los productos
            System.out.print(listarProductos(productos));

            int id = inputUsuario.solicitarEntero("Introduzca el identificador del producto: ");
            producto = dao.consultarId(id);

            if (producto == null)
                System.out.println("\nEl identificador " + id + " no existe. " +
                        "Por favor, introducir un identificador de la lista");
        }

        return producto;
    }
}

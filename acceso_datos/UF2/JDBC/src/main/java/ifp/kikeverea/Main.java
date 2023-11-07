package ifp.kikeverea;

import ifp.kikeverea.bd.*;
import ifp.kikeverea.datos.Repositorio;
import ifp.kikeverea.programas.Programa;
import ifp.kikeverea.programas.ProgramaBorradoTablas;
import ifp.kikeverea.programas.ProgramaCreacionTablas;
import ifp.kikeverea.programas.ProgramaManipulacionDatos;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.Menu;
import ifp.kikeverea.util.OpcionMenu;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String MOSTRAR_TABLAS = "Mostrar tablas";
    private static final String ELEGIR_TABLA = "Elegir tabla";
    private static final String CREAR_TABLA = "Crear tabla";
    private static final String BORRAR_TABLA = "Borrar tabla";
    private static final String SALIR = "Salir";

    private static final Menu<String> MENU_TABLAS = Menu.menuSimple()
            .prompt("Acción: ")
            .opciones(OpcionMenu.opcionesSimples(MOSTRAR_TABLAS, ELEGIR_TABLA, CREAR_TABLA, BORRAR_TABLA))
            .salida(OpcionMenu.opcionSimple(SALIR))
            .build();

    private static final String CANCELAR = "Cancelar";

    private static final String NOMBRE_BD = "bd_uf2";
    private static final String URI_BD = "jdbc:mysql://root@localhost:3306/"+NOMBRE_BD;

    public static void main(String[] args) {
        InputUsuario input = new InputUsuario(new Scanner(System.in));

        BaseDeDatos bd = new BaseDeDatos(NOMBRE_BD);
        conectarBaseDeDatos(bd);

        do {
            Programa.imprimirMensaje("Tablas");
            String accion = input.solicitarOpcionMenu(MENU_TABLAS);

            try {
                switch (accion) {
                    case MOSTRAR_TABLAS -> mostrarTablas(bd);
                    case ELEGIR_TABLA -> ejecutarProgramaManipulacionDatos(bd, input);
                    case CREAR_TABLA -> ProgramaCreacionTablas.crearTabla(bd, input);
                    case BORRAR_TABLA -> ejecutarProgramaBorrado(bd, input);
                    case SALIR -> terminarPrograma(bd);
                }
            }
            catch (SQLException e) {
                System.err.println("Error: error durante la ejecución del programa");
                System.err.println("Causa: " + e.getMessage());
                System.exit(e.getErrorCode());
            }
        }
        while (true);
    }

    private static void conectarBaseDeDatos(BaseDeDatos bd) {
        try {
            bd.connectar(URI_BD);
        }
        catch (SQLException e) {
            Programa.imprimirError("No se ha podido realizar una conexión a la base de datos");
            System.exit(1);
        }
    }

    private static void desconectarBaseDeDatos(BaseDeDatos bd) {
        try {
            bd.desconectar();
        }
        catch (SQLException e) {
            System.err.println("No se ha podido terminar la conexión con la base de datos");
        }
    }

    private static void terminarPrograma(BaseDeDatos bd) {
        desconectarBaseDeDatos(bd);
        Programa.imprimirMensaje("Programa finalizado");
        System.exit(0);
    }

    private static void mostrarTablas(BaseDeDatos bd) throws SQLException {
        List<String> tablas = bd.listarEntidades();

        String textoTablas = tablas.isEmpty()
                ? "No hay tablas en la base de datos"
                : "TABLAS:\n" + String.join(", ", tablas);

        Programa.imprimirMensaje(textoTablas);
    }



    private static void ejecutarProgramaManipulacionDatos(BaseDeDatos bd, InputUsuario input) throws SQLException {
        String entidad = escojerEntidad(bd, input);

        if (entidad == null)
            return;

        ProgramaManipulacionDatos.ejecutar(new Repositorio(bd, bd.buscarEntidad(entidad)), input);
    }

    private static void ejecutarProgramaBorrado(BaseDeDatos bd, InputUsuario input) throws SQLException {
        Programa.imprimirMensaje("Borrar tabla");

        String entidad = escojerEntidad(bd, input);

        if (entidad == null) {
            Programa.operacionCancelada();
            return;
        }

        ProgramaBorradoTablas.ejecutar(bd, entidad, input);
    }

    private static String escojerEntidad(BaseDeDatos bd, InputUsuario input) throws SQLException {
        List<String> entidades = bd.listarEntidades();

        if(entidades.isEmpty()) {
            Programa.imprimirMensaje("No hay tablas en la base de datos");
            return null;
        }

        Menu<String> menuEntidades = Menu.menuSimple()
                .mensajeInicial("Tablas disponibles:")
                .prompt("Escoger: ")
                .opciones(OpcionMenu.opcionesSimples(entidades))
                .salida(OpcionMenu.opcionSimple(CANCELAR))
                .build();

        String opcionElejida = input.solicitarOpcionMenu(menuEntidades);

        return !opcionElejida.equals(CANCELAR) ? opcionElejida : null;
    }
}

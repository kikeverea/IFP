package ifp.kikeverea;

import ifp.kikeverea.bd.*;
import ifp.kikeverea.programas.Programa;
import ifp.kikeverea.programas.ProgramaBorradoTablas;
import ifp.kikeverea.programas.ProgramaCreacionTablas;
import ifp.kikeverea.util.OpcionMenu;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.Menu;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private enum AccionTablas implements OpcionMenu {
        MOSTRAR_TABLAS("Mostrar tablas"),
        ELEGIR_TABLA("Elegir tabla"),
        CREAR_TABLA("Crear tabla"),
        BORRAR_TABLA("Borrar tabla"),
        SALIR("Salir");

        private final String mensaje;
        AccionTablas(String mensaje) {
            this.mensaje = mensaje;
        }

        @Override
        public String mensaje(String... args) {
            return mensaje;
        }
    }

    private static final String NOMBRE_BD = "bd_uf2";
    private static final String URI_BD = "jdbc:mysql://kike:asd@localhost:3306/"+NOMBRE_BD;
    private static final Menu MENU_TABLAS = new Menu("Acción: ", AccionTablas.values());

    public static void main(String[] args) {
        InputUsuario input = new InputUsuario(new Scanner(System.in));

        BaseDeDatos bd = new BaseDeDatos(NOMBRE_BD);
        conectarBaseDeDatos(bd);

        do {
            AccionTablas accion = (AccionTablas) input.solicitarOpcionMenu(MENU_TABLAS);

            if (accion == AccionTablas.SALIR) {
                Programa.imprimirResultado("Programa finalizado");
                System.exit(0);
            }

            try {
                switch (accion) {
                    case MOSTRAR_TABLAS -> mostrarTablas(bd);
                    case CREAR_TABLA -> ProgramaCreacionTablas.crearTabla(bd, input);
                    case BORRAR_TABLA -> ProgramaBorradoTablas.ejecutar(bd, input);
                }
            }
            catch (SQLException e) {
                System.err.println("Error: error durante la ejecución del programa");
                System.out.println("Causa: " + e.getMessage());
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

    private static void mostrarTablas(BaseDeDatos bd) throws SQLException {
        String tablas = String.join(", ", bd.listarEntidades());
        Programa.imprimirResultado("TABLAS:\n" + tablas);
    }
}

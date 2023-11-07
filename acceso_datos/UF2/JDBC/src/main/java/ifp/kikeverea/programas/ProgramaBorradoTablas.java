package ifp.kikeverea.programas;

import ifp.kikeverea.bd.BaseDeDatos;
import ifp.kikeverea.util.InputUsuario;

import java.sql.SQLException;

public class ProgramaBorradoTablas {
    
    public static void ejecutar(BaseDeDatos bd, String entidad, InputUsuario input) {
        boolean proceder = input.solicitarSioNo("¿Borrar tabla '" + entidad + "'? [Si/No] ");

        if (!proceder) {
            Programa.operacionCancelada();
            return;
        }

        try {
            bd.eliminarEntidad(entidad);
            Programa.imprimirMensaje("La tabla " + entidad + " ha sido eliminada");
        }
        catch (SQLException e) {
            Programa.imprimirError("No se ha podido borrar la tabla " + entidad);
        }
    }
}

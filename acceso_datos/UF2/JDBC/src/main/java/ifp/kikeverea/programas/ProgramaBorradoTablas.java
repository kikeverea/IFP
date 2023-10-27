package ifp.kikeverea.programas;

import ifp.kikeverea.bd.BaseDeDatos;
import ifp.kikeverea.util.InputUsuario;

import java.sql.SQLException;

public class ProgramaBorradoTablas {
    
    public static void ejecutar(BaseDeDatos bd, String entidad, InputUsuario input) throws SQLException {
        
        boolean proceder = input.solicitarEleccion(
                "¿Borrar tabla '" + entidad + "'? [Si/No] ",
                new String[]{"SI", "Si", "si", "S", "s"},
                new String[]{"NO", "No", "no", "N", "n"}
        );

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

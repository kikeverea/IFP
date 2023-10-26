package ifp.kikeverea;

import ifp.kikeverea.bd.BaseDeDatos;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.Menu;
import ifp.kikeverea.util.OpcionMenu;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramaBorradoTablas {

    private static final OpcionMenu CANCELAR = OpcionMenu.opcionSimple("Cancelar");

    public static void ejecutar(BaseDeDatos bd, InputUsuario input) throws SQLException {

        List<OpcionMenu> opcionesEntidades = crearOpciones(bd);
        Menu menuEntidades = new Menu("Tablas disponibles:", "Eliminar: ", opcionesEntidades.toArray(new OpcionMenu[0]));

        OpcionMenu opcionElejida = input.solicitarOpcionMenu(menuEntidades);

        if (opcionElejida == CANCELAR) {
            Programa.operacionCancelada();
            return;
        }

        String nombreEntidad = opcionElejida.mensaje();

        boolean proceder = input.solicitarEleccion(
                "¿Borrar tabla '"+ nombreEntidad + "'? [Si/No] ",
                new String[]{"SI", "Si", "si", "S", "s"},
                new String[]{"NO", "No", "no", "N", "n"});

        if (!proceder) {
            Programa.operacionCancelada();
            return;
        }

        try {
            bd.eliminarEntidad(nombreEntidad);
            Programa.imprimirResultado("La tabla " + nombreEntidad + " ha sido eliminada");
        }
        catch (SQLException e) {
            Programa.imprimirError("No se ha podido borrar la tabla " + nombreEntidad);
        }
    }

    private static List<OpcionMenu> crearOpciones(BaseDeDatos bd) throws SQLException {
        List<OpcionMenu> opcionesEntidades =
                bd.listarEntidades()
                    .stream()
                    .map(OpcionMenu::opcionSimple)
                    .collect(Collectors.toList());

        opcionesEntidades.add(CANCELAR);
        return opcionesEntidades;
    }

}

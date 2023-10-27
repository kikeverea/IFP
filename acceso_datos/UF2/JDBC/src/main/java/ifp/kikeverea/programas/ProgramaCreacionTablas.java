package ifp.kikeverea.programas;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.BaseDeDatos;
import ifp.kikeverea.bd.Entidad;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.Presentador;

import java.sql.SQLException;
import java.util.List;

public class ProgramaCreacionTablas {

    public static void crearTabla(BaseDeDatos bd, InputUsuario input) throws SQLException {
        String nombre = solicitarNombreEntidad(bd, input);

        if (nombre.isBlank()) {
            Programa.operacionCancelada();
            return;
        }

        List<Atributo> atributos = ProgramaSolicitudColumnas.solicitarColumnas(input);

        if (atributos.isEmpty()) {
            Programa.operacionCancelada();
            return;
        }

        bd.crearEntidad(new Entidad(nombre, atributos));
        Programa.imprimirResultado(resultadoCrearEntidad(nombre, atributos));
    }

    private static String solicitarNombreEntidad(BaseDeDatos bd, InputUsuario input) throws SQLException {
        List<String> tablas = bd.listarEntidades();

        while (true) {
            String nombre = input.solicitarSoloTexto("Nombre: ");

            if (!tablas.contains(nombre))
                return nombre;

            System.out.println("La tabla '" + nombre + "' ya existe");
        }
    }

    private static String resultadoCrearEntidad(String nombre, List<Atributo> atributos) {
        String nombresAtributos = Presentador.separadoPorComas(atributos, Atributo::getNombre);
        return "Tabla: " + nombre + " ("+nombresAtributos+"), creada con éxito";
    }
}

package ifp.kikeverea.main.programas;

import ifp.kikeverea.io.DatosNoContienenPersonasException;
import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.persona.Persona;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.ValidadorNumeros;

import java.io.IOException;
import java.util.Collection;

public class ProgramaLectura {

    private static final String MENU_ACCION_LECTURA =
        "Lectura. Elije una acción de lectura:\n" +
            "1- Leer todo el archivo:\n" +
            "2- Leer una persona:\n" +
            "Acción: ";

    public static void ejecutar(FicheroPersonas fichero, InputUsuario input) {
        AccionLectura accion = determinarAccionLectura(input);

        try {
            if (accion == AccionLectura.LEER_TODO) {
                imprimirPersonas(fichero.leerFichero());
            }
            else {
                String nombre = input.solicitarTexto("Nombre de la persona: ");
                imprimirPersonas(fichero.leerConNombre(nombre));
            }
        }
        catch (DatosNoContienenPersonasException | IOException e) {
            System.out.println("Error: no se ha podido leer el fichero");
            System.out.println("Causa: " + e.getMessage());
        }
    }

    private static AccionLectura determinarAccionLectura(InputUsuario input) {
        ValidadorNumeros validadorAccion = ValidadorNumeros.enIntervalo(1,2);
        int numeroAccion = input.solicitarEntero(MENU_ACCION_LECTURA, validadorAccion);

        return AccionLectura.determinarAccion(numeroAccion);
    }

    private static void imprimirPersonas(Collection<Persona> personas) {
        System.out.println("Personas:");
        personas.forEach(System.out::println);
    }
}

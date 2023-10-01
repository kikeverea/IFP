package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.persona.Persona;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.ValidadorNumeros;

import java.io.IOException;

import static ifp.kikeverea.main.AccionEscritura.*;
import static ifp.kikeverea.main.AccionEscritura.SOBREESCIBIR_FICHERO;

public class ProgramaEscritura {

    private static final String MENU_ACCION_ESCRITURA =
        "El fichero ya existe. Elija una acción:\n" +
            SOBREESCIBIR_FICHERO.numero() + "-" + SOBREESCIBIR_FICHERO.nombre() + "\n" +
            ANADIR_A_FICHERO.numero() + "-" + ANADIR_A_FICHERO.nombre() + "\n" +
            "Acción: ";

    private static final String NUMERO_DE_PERSONAS = "Número de personas que desea escribir: ";

    public static void ejecutar(FicheroPersonas fichero, InputUsuario input) {
        AccionEscritura accion =
            fichero.existe()
                ? determinarAccionEscritura(input)
                : CREAR_FICHERO;

        int numeroDePersonas = input.solicitarEntero(NUMERO_DE_PERSONAS, ValidadorNumeros.soloPositivos());

        solicitarPersonas(numeroDePersonas, input, fichero);

        try {
            fichero.escribirFichero(accion == ANADIR_A_FICHERO);
            System.out.println(accion.mensajeFinal());
        }
        catch (IOException e) {
            System.out.println("Error: no se ha podido escribir datos en el fichero");
            System.out.println("Causa: " + e.getMessage());
        }
    }

    private static AccionEscritura determinarAccionEscritura(InputUsuario input) {
        ValidadorNumeros validadorAccion = ValidadorNumeros.enIntervalo(1,2);
        int numeroAccion = input.solicitarEntero(MENU_ACCION_ESCRITURA, validadorAccion);
        return AccionEscritura.determinarAccion(numeroAccion);
    }

    private static void solicitarPersonas(int numeroDePersonas, InputUsuario input, FicheroPersonas ficheroPersonas) {
        for (int i = 0; i < numeroDePersonas; i++) {
            System.out.println("***** Persona" + (numeroDePersonas > 1 ? " " + (i + 1) : "") + " *****");
            String nombre = input.solicitarTexto("Nombre: ");
            String apellido = input.solicitarTexto("Apellido: ");
            String ciudad = input.solicitarTexto("Ciudad: ");
            String nacionalidad = input.solicitarTexto("Nacionalidad: ");
            int edad = input.solicitarEntero("Edad: ");
            ficheroPersonas.anadirPersona(new Persona(nombre, apellido, ciudad, nacionalidad, edad));
        }
    }
}

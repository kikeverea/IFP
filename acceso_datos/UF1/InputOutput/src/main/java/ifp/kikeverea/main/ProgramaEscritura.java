package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.persona.Persona;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.ValidadorNumeros;

import java.io.IOException;

import static ifp.kikeverea.main.AccionEscritura.*;

public class ProgramaEscritura {

    private static final int MAX_PERSONAS = 3;

    private static final String MENU_ACCION_ESCRITURA =
        "El fichero ya existe. Elija una acción:\n" +
            SOBREESCIBIR_FICHERO.numero() + "- " + SOBREESCIBIR_FICHERO.nombre() + "\n" +
            ANADIR_A_FICHERO.numero() + "- " + ANADIR_A_FICHERO.nombre() + "\n" +
            CANCELAR.numero() + "- " + CANCELAR.nombre() + "\n" +
            "Acción: ";

    public static void ejecutar(FicheroPersonas fichero, InputUsuario input) {
        AccionEscritura accion =
            fichero.existe()
                ? determinarAccionEscritura(input)
                : CREAR_FICHERO;

        if (accion == CANCELAR) {
            System.out.println(CANCELAR.mensajeFinal());
            System.exit(0);
        }

        int numeroDePersonas = input.solicitarEntero(
                "Número de personas que desea escribir (máx. " + MAX_PERSONAS + "): ",
                ValidadorNumeros.enIntervalo(1, MAX_PERSONAS));

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
        System.out.println("Determinar accion");
        ValidadorNumeros validadorAccion = ValidadorNumeros.enIntervalo(0,2);
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

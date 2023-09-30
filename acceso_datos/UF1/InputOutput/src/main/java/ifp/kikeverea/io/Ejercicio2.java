package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;
import ifp.kikeverea.util.InputUsuario;

import java.io.IOException;
import java.util.*;

public class Ejercicio2 {

    private enum AccionFichero {
        SOBREESCIBIR_FICHERO(1, "Sobreescribir fichero", "Datos escritos al fichero"),
        ANADIR_A_FICHERO(2, "Añadir contenido al final", "Datos añadidos al final del fichero"),
        CREAR_FICHERO(3, "Crear fichero", "Fichero creado correctamente");

        private final int numero;
        private final String nombre;
        private final String mensajeFinal;

        AccionFichero(int numero, String nombre, String mensajeFinal) {
            this.numero = numero;
            this.nombre = nombre;
            this.mensajeFinal = mensajeFinal;
        }

        static Optional<AccionFichero> determinarAccion(int numero) {
            return Arrays.stream(AccionFichero.values())
                    .filter(accionFichero -> accionFichero.numero == numero)
                    .findFirst();
        }
    }

    private static final String MENU_FICHERO_YA_EXISTE =
            "El fichero ya existe. Elija una acción:\n" +
            AccionFichero.SOBREESCIBIR_FICHERO.numero + "-" + AccionFichero.SOBREESCIBIR_FICHERO.nombre + "\n" +
            AccionFichero.ANADIR_A_FICHERO.numero + "-" + AccionFichero.ANADIR_A_FICHERO.nombre + "\n" +
            "Acción: ";

    private static final int NUMERO_DE_PERSONAS = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputUsuario input = new InputUsuario(scanner);

        FicheroPersonas fichero = new FicheroPersonas(new IOFicheroTextoPersona());
        establecerRutaDelFichero(input, fichero);

        AccionFichero accion =
                fichero.getEstado() == FicheroPersonas.Estado.CREADO
                ? AccionFichero.CREAR_FICHERO
                : determinarAccion();

        solicitarPersonas(input, fichero);

        try {
            fichero.escribirFichero(accion == AccionFichero.ANADIR_A_FICHERO);
            System.out.println(accion.mensajeFinal);
        }
        catch (IOException e) {
            System.out.println("Error: no se ha podido escribir datos en el fichero");
            System.out.println("Causa: " + e.getMessage());
        }
    }

    private static void establecerRutaDelFichero(InputUsuario input, FicheroPersonas fichero) {
        String ruta;
        do {
            ruta = input.solicitarTexto("Ruta del fichero: ");
        }
        while (!fichero.establecerRuta(ruta));
    }

    private static void solicitarPersonas(InputUsuario input, FicheroPersonas ficheroPersonas) {
        System.out.println("Datos de la Persona:");
        for (int i = 0; i < NUMERO_DE_PERSONAS; i++) {
            String nombre = input.solicitarTexto("Nombre: ");
            String apellido = input.solicitarTexto("Apellido: ");
            String ciudad = input.solicitarTexto("Ciudad: ");
            String nacionalidad = input.solicitarTexto("Nacionalidad: ");
            int edad = input.solicitarEntero("Edad: ");
            ficheroPersonas.anadirPersona(new Persona(nombre, apellido, ciudad, nacionalidad, edad));
        }
    }

    private static AccionFichero determinarAccion() {
        InputUsuario input = new InputUsuario(new Scanner(System.in));
        do {
            int numero = input.solicitarEntero(MENU_FICHERO_YA_EXISTE);
            Optional<AccionFichero> accion = AccionFichero.determinarAccion(numero);

            if (accion.isPresent())
                return accion.get();

            System.out.println("Acción " + numero + "inválida");
        }
        while (true);
    }
}
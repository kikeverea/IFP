package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.io.IOFicheroTextoPersona;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.ValidadorNumeros;

import java.util.Scanner;

public class Ejercicio3 {

    private static final int ESCRIBIR = 1;
    private static final int LEER = 2;
    private static final int SALIR = 0;

    private static final String MENU =
        "Entrada/Salida de datos. Elije una opción:\n" +
        "1- Escribir\n" +
        "2- Leer\n" +
        "0- Salir\n" +
        "Opción: ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputUsuario input = new InputUsuario(scanner);

        int programa = input.solicitarEntero(MENU, ValidadorNumeros.enIntervalo(0, 2));
        FicheroPersonas fichero;

        switch (programa) {
            case ESCRIBIR:
                fichero = solicitarFichero(input);
                ProgramaEscritura.ejecutar(fichero, input);
                break;
            case LEER :
                fichero = solicitarFicheroExistente(input);
                ProgramaLectura.ejecutar(fichero, input);
                break;
            case SALIR:
                System.out.println("Programa finalizado");
                System.exit(0);
                break;
        }
    }

    private static FicheroPersonas solicitarFichero(InputUsuario input) {
        FicheroPersonas fichero = new FicheroPersonas(new IOFicheroTextoPersona());
        establecerRutaDelFichero(input, fichero);
        return fichero;
    }

    private static FicheroPersonas solicitarFicheroExistente(InputUsuario input) {
        do {
            FicheroPersonas fichero = solicitarFichero(input);

            if (fichero.existe())
                return fichero;

            else System.out.println("El fichero no existe");
        }
        while (true);
    }

    private static void establecerRutaDelFichero(InputUsuario input, FicheroPersonas fichero) {
        String ruta;
        do {
            ruta = input.solicitarTexto("Ruta del fichero: ");
        }
        while (!fichero.establecerRuta(ruta));
    }
}

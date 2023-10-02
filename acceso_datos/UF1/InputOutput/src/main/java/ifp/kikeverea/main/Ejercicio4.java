package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.io.IOFicheroBinarioPersona;
import ifp.kikeverea.io.IOFicheroTextoPersona;
import ifp.kikeverea.main.programas.ProgramaEscritura;
import ifp.kikeverea.main.programas.ProgramaLectura;
import ifp.kikeverea.main.programas.ProgramaProveerdorFicheros;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.ValidadorNumeros;

import java.util.Scanner;

public class Ejercicio4 {

    private static final int ESCRIBIR = 1;
    private static final int LEER = 2;
    private static final int ESCRIBIR_BINARIO = 3;
    private static final int SALIR = 0;

    private static final String MENU =
            "Entrada/Salida de datos. Elije una opción:\n" +
                    "1- Escribir\n" +
                    "2- Leer\n" +
                    "3- Escribir binario\n" +
                    "0- Salir\n" +
                    "Opción: ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputUsuario input = new InputUsuario(scanner);

        int programa = input.solicitarEntero(MENU, ValidadorNumeros.enIntervalo(0, 3));
        FicheroPersonas fichero;

        switch (programa) {
            case ESCRIBIR:
                fichero = ProgramaProveerdorFicheros.solicitarFichero(new IOFicheroTextoPersona(), input);
                ProgramaEscritura.ejecutar(fichero, input);
                break;
            case LEER :
                fichero = ProgramaProveerdorFicheros.solicitarFicheroExistente(new IOFicheroTextoPersona(), input);
                ProgramaLectura.ejecutar(fichero, input);
                break;
            case ESCRIBIR_BINARIO:
                fichero = ProgramaProveerdorFicheros.solicitarFichero(new IOFicheroBinarioPersona(), input);
                ProgramaEscritura.ejecutar(fichero, input);
                break;
            case SALIR:
                System.out.println("Programa finalizado");
                System.exit(0);
                break;
        }
    }
}

package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.util.InputUsuario;

import java.util.Scanner;

public class Ejercicio2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputUsuario input = new InputUsuario(scanner);

        FicheroPersonas fichero = ProgramaProveerdorFicheros.ejecutar(input);
        ProgramaEscritura.ejecutar(fichero, input);
    }
}
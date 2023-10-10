package ifp.kikeverea.main;

import ifp.kikeverea.io.FicheroPersonas;
import ifp.kikeverea.io.IOFicheroTextoPersona;
import ifp.kikeverea.programas.ProgramaEscritura;
import ifp.kikeverea.programas.ProgramaProveerdorFicheros;
import ifp.kikeverea.util.InputUsuario;

import java.util.Scanner;

public class Ejercicio2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InputUsuario input = new InputUsuario(scanner);

        FicheroPersonas fichero = ProgramaProveerdorFicheros.solicitarFichero(new IOFicheroTextoPersona(), input);
        ProgramaEscritura.ejecutar(fichero, input);
    }
}

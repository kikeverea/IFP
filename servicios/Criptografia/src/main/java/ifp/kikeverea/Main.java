package ifp.kikeverea;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Encriptador encriptador = Encriptador.conClave(Encriptador.generarClave());

        System.out.println("***** Programa de cifrado *****");
        System.out.print("Introduce el mensaje a cifrar: ");
        String mensaje = scanner.nextLine();

        String cifrado = encriptador.cifrar(mensaje);
        String descifrado = encriptador.descifrar(cifrado);

        System.out.println("Mensaje: " + mensaje);
        System.out.println("Mensaje cifrado: " + cifrado);
        System.out.println("Mensaje descifrado: " + descifrado);

        // Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc nec ipsum tempus, posuere turpis ut, pretium quam
    }
}

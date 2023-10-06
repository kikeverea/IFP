package ifp.kikeverea;

import javax.crypto.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DES {

    private static final int ENCRIPTAR = 1;
    private static final int DESENCRIPTAR = 2;
    private static final int SALIR = 0;

    private static final String MENU =
            """
            Elige una opción:
            1- Cifrar
            2- Descifrar
            0- Salir
            Opción:\040""";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InputUsuario input = new InputUsuario(sc);

        try {
            KeyGenerator generadorDeClaves = KeyGenerator.getInstance("DES");   // obtiene un generador de claves DES
            SecretKey clave = generadorDeClaves.generateKey();                  // genera una clave
            Cipher cifrado = Cipher.getInstance("DES");                         // obtiene una instanci de cifrado DES

            int opcion;

            do {
                System.out.println("***** Cifrado de Ficheros *****");
                opcion = input.solicitarEntero(MENU, ValidadorNumeros.enIntervalo(0, 2));

                switch (opcion) {
                    case ENCRIPTAR -> encriptar(input, cifrado, clave);
                    case DESENCRIPTAR -> desencriptar(input, cifrado, clave);
                    case SALIR -> System.out.println("Programa finalizado");
                }
            }
            while (opcion != SALIR);
        }
        catch (InvalidKeyException e) {
            error("clave inválida");
        }
        catch (IOException e) {
            error("no se ha podido acceder al fichero");
        }
        catch (IllegalBlockSizeException | BadPaddingException e) {
            error("no se ha podido aplicar el cifrado");
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            error("no se ha podido iniciar el cifrado");
        }
    }

    private static void encriptar(InputUsuario input, Cipher cifrado, SecretKey clave)
            throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
    {
        cifrado.init(Cipher.ENCRYPT_MODE, clave);

        File fichero = solicitarFicheroParaLectura(input, "Ruta del fichero a encriptar: ");
        byte[] resultado = ejecutarCifradoEnFichero(cifrado, fichero);

        File ficheroDestino = solicitarFichero(input, "Ruta del fichero de destino: ");
        escribirEnFichero(ficheroDestino, resultado);
    }

    private static void desencriptar(InputUsuario input, Cipher cifrado, SecretKey clave)
            throws IOException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException
    {
        cifrado.init(Cipher.DECRYPT_MODE, clave);

        File fichero = solicitarFicheroParaLectura(input, "Ruta del fichero a desencriptar: ");
        byte[] resultado = ejecutarCifradoEnFichero(cifrado, fichero);

        System.out.println();
        System.out.println(new String(resultado, StandardCharsets.UTF_8));
        System.out.println();
    }

    private static File solicitarFicheroParaLectura(InputUsuario input, String mensaje) {
        do {
            File fichero = solicitarFichero(input, mensaje);

            if(fichero.exists())
                return fichero;

            System.out.println("Fichero inválido. El fichero no existe");
        }
        while (true);
    }

    private static File solicitarFichero(InputUsuario input, String mensaje) {
        do {
            String ruta = input.solicitarTexto(mensaje);
            File fichero = new File(ruta);

            if (validarDirectorio(fichero))
                return fichero;

            System.out.println("Fichero inválido. La ruta del fichero no existe");
        }
        while (true);
    }

    private static boolean validarDirectorio(File f) {
        String directorioDeTrabajo = f.getParent();
        return directorioDeTrabajo == null || new File(directorioDeTrabajo).exists();
    }

    private static byte[] ejecutarCifradoEnFichero(Cipher cifrado, File fichero)
            throws IOException, IllegalBlockSizeException, BadPaddingException
    {
        Path ruta = Path.of(fichero.toURI());
        byte[] contenidoFichero = Files.readAllBytes(ruta);     // lee el contenido entero del fichero
        return cifrado.doFinal(contenidoFichero);               // aplica el cifrado al contenido
    }

    private static void escribirEnFichero(File fichero, byte[] contenido) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fichero);
             DataOutputStream dos = new DataOutputStream(fos);
             BufferedOutputStream stream = new BufferedOutputStream(dos))
        {
            stream.write(contenido);        // escribe los datos al stream
            stream.flush();                 // vacía el buffer del stream
        }
    }

    private static void error(String mensaje) {
        System.out.println("Error: " + mensaje);
        System.exit(1);
    }
}

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
            Cipher cifrado = Cipher.getInstance("DES");                         // obtiene una instancia de cifrado DES

            int opcion;

            do {
                System.out.println("***** Cifrado de Ficheros *****");
                opcion = input.solicitarEntero(MENU, ValidadorNumeros.enIntervalo(0, 2));

                if (opcion == SALIR) {
                    System.out.println("Programa finalizado");
                    System.exit(0);
                }

                byte[] contenido = solicitarContenidoParaEncriptar(input);

                switch (opcion) {
                    case ENCRIPTAR -> {
                        cifrado.init(Cipher.ENCRYPT_MODE, clave);
                        byte[] encriptado = cifrado.doFinal(contenido);
                        Path rutaDestino = solicitarRutaParaEscritura(input);
                        escribirEnRuta(rutaDestino, encriptado);
                    }
                    case DESENCRIPTAR -> {
                        cifrado.init(Cipher.DECRYPT_MODE, clave);
                        byte[] desencriptado = cifrado.doFinal(contenido);
                        System.out.println("\n"+new String(desencriptado, StandardCharsets.UTF_8)+"\n");
                    }
                }
            }
            while (true);
        } catch (InvalidKeyException e) {
            error("clave inválida");
        } catch (IOException e) {
            error("no se ha podido acceder al fichero");
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            error("no se ha podido aplicar el cifrado");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            error("no se ha podido iniciar el cifrado");
        }
    }

    private static byte[] solicitarContenidoParaEncriptar(InputUsuario input) throws IOException {
        do {
            Path ruta = Path.of(input.solicitarTexto("Ruta del fichero a desencriptar: "));

            if(Files.exists(ruta))
                return Files.readAllBytes(ruta);

            System.out.println("Ruta de fichero inválida. El fichero no existe");
        }
        while (true);
    }

    private static Path solicitarRutaParaEscritura(InputUsuario input) {
        do {
            Path ruta = Path.of(input.solicitarTexto("Ruta del fichero de destino: "));
            Path directorioDeTrabajo = ruta.getParent();

            if (directorioDeTrabajo == null || Files.exists(directorioDeTrabajo))
                return ruta;

            System.out.println("Fichero inválido. La ruta del fichero no existe");
        }
        while (true);
    }

    private static void escribirEnRuta(Path ruta, byte[] contenido) throws IOException {
        try (OutputStream os = Files.newOutputStream(ruta);
             DataOutputStream dos = new DataOutputStream(os);
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

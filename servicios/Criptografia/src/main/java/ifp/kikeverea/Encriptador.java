package ifp.kikeverea;

import java.util.concurrent.ThreadLocalRandom;

public class Encriptador {

    private static final int BYTE = 8; // 1 byte == 8 bits
    private static final int LONGITUD_CLAVE_BITS = 56;
    private static final int LONGITUD_CLAVE_BYTES = LONGITUD_CLAVE_BITS / BYTE;

    private final byte[] clave;

    private Encriptador(byte[] clave) {
        this.clave = clave;
    }

    public static Encriptador conClave(byte[] clave) {
        return new Encriptador(clave);
    }

    public static byte[] generarClave() {
        byte[] clave = new byte[LONGITUD_CLAVE_BYTES];
        ThreadLocalRandom localRandom = ThreadLocalRandom.current(); // generador de números aleatorios

        for (int i = 0; i < clave.length; i++) {
            int in = localRandom.nextInt(33, 127); // asegura un caracter ASCII
            clave[i] = (byte) in;
        }

        return clave;
    }

    public String cifrar(String texto) {
        return cifrarDescifrar(texto);
    }

    public String descifrar(String texto) {
        return cifrarDescifrar(texto);
    }

    private String cifrarDescifrar(String texto) {

        char[] caracteres = texto.toCharArray();

        for (int i = 0, k = 0; i < caracteres.length; i++, k++) {

            int keyPos = k < clave.length ? k : 0;
            int caracter = caracteres[i];

            // combina los bits del caracter con 8 bits de la llave mediante XOR
            // la operación inversa de XOR es la misma XOR, de manera que este algoritmo puede ser usado tanto para
            // cifrar como para descifrar
            caracteres[i] = (char) (caracter ^ clave[keyPos]);
        }

        return new String(caracteres);
    }
}

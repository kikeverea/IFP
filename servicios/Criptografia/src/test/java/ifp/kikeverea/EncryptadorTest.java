package ifp.kikeverea;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class EncryptadorTest {

    @Test
    void encryptsAndDecryptsStrings() {
        String randomString = randomString();
        byte[] clave = Encriptador.generarClave();
        Encriptador en = Encriptador.conClave(clave);

        String encrypted = en.cifrar(randomString);
        Assertions.assertNotEquals(randomString, encrypted);
        Assertions.assertEquals(randomString, en.descifrar(encrypted));
    }

    @Test
    void encryptsAndDecryptsStringOfLastAscii() {
        char[] cs = new char[24];
        Arrays.fill(cs, (char) 126);

        String s = new String(cs);

        byte[] clave = Encriptador.generarClave();
        Encriptador en = Encriptador.conClave(clave);
        String encrypted = en.cifrar(s);

        Assertions.assertEquals(s, en.descifrar(encrypted));
    }

    public String randomString() {
        char[] cs = new char[24];
        for (int i = 0; i < cs.length; i++)
            cs[i] = (char) ThreadLocalRandom.current().nextInt(33, 126);

        return new String(cs);
    }
}

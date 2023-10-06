package ifp.kikeverea;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class CriptoTest {

    private static final int _48_BITS_IN_BYTES = 56 / 8;

    @Test
    void xor() {
        //   0101 a
        // + 0011 b
        // = 0110 result

        //   0010 0001 crypto
        // + 0010 0100 key
        // = 0000 0101 text

        int a = 0b0101; // 5
        int b = 0b0011; // 3

        int c = a ^ b; //  6 (0110)

        Assertions.assertEquals(6, c);
        Assertions.assertEquals(a, (c ^ b));
        Assertions.assertEquals(a, (b ^ c));
        Assertions.assertEquals(b, (c ^ a));
        Assertions.assertEquals(b, (a ^ c));
    }

    private byte[] generateKey() {
        byte[] keyChars = new byte[_48_BITS_IN_BYTES];

        for (int i = 0; i < keyChars.length; i++) {
            int in = ThreadLocalRandom.current().nextInt(33, 127);
            keyChars[i] = (byte) in;
        }

        return keyChars;
    }

    @Test
    void key() {
        byte[] key = generateKey();
        Assertions.assertEquals(_48_BITS_IN_BYTES, key.length);
    }

    @Test
    void encrypt() {
        String text = "Hello world";
        byte[] key = generateKey();

        String encrypted = encryptDecrypt(text, key);
        String decrypted = encryptDecrypt(encrypted, key);

        Assertions.assertEquals(text, decrypted);
    }

    private String encryptDecrypt(String text, byte[] key) {

        for (byte c : key) {
            System.out.print((char) c);
            System.out.print(", ");
        }
        System.out.println();

        char[] textChars = text.toCharArray();

        for (int i = 0, k = 0; i < textChars.length; i++, k++) {

            int keyPos = k < key.length ? k : 0;
            int textChar = textChars[i] & 0xff;

            textChars[i] = (char) (textChar ^ key[keyPos]);
        }

        return new String(textChars);
    }
}

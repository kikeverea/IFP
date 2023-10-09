package ifp.kikeverea;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.KeyPair;

import java.security.KeyPairGenerator;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;

public class Ejerpublica {
    public static void main(String[] args) {
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");


            KeyPair keypair = keygen.generateKeyPair();
            Cipher desCipher = Cipher.getInstance("RSA");

            desCipher.init(Cipher.ENCRYPT_MODE, keypair.getPrivate());
            File inf = new File("fichero_prueba.txt");
            FileInputStream is = new FileInputStream(inf);

            try (FileOutputStream os = new FileOutputStream("fichero_cifrado_con_RSA")) {

                byte[] buffer = new byte[64];
                int bytes_leidos = is.read(buffer);
                while (bytes_leidos != -1) {
                    byte[] cbuf = desCipher.doFinal(buffer, 0, bytes_leidos);
                    os.write(cbuf);
                    bytes_leidos = is.read(buffer);
                }
            }


            KeyFactory keyfac = KeyFactory.getInstance("RSA");
            System.out.println("Generando keyspec");
            RSAPublicKeySpec publicKeySpec = keyfac.getKeySpec(keypair.getPublic(), RSAPublicKeySpec.class);

            FileOutputStream cos = new FileOutputStream("clave_publica");
            PrintWriter pw = new PrintWriter(cos);
            pw.println(publicKeySpec.getModulus());
            pw.println(publicKeySpec.getPublicExponent());
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
} 







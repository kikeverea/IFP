package ifp.kikeverea.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IOFicheroTest {

    private static final String CADENA_EN_FICHERO =
            "Hello World\n" +
            "Español\n" +
            "_!+@#$%^&*)(,.<>?~!";
    private static final String PATH_FICHERO = "./src/test/java/ifp/kikeverea/io/prueba_fichero.txt";

    private IOFichero io;

    @BeforeEach
    void init() {
        io = new BufferedIOFichero();
    }

    @Test
    void leeElContenidoDeUnFichero() throws Exception {
        Assertions.assertEquals(CADENA_EN_FICHERO, io.leerContenido(PATH_FICHERO));
    }

}

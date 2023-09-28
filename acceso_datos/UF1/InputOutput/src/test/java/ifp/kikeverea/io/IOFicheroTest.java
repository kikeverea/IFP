package ifp.kikeverea.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

public class IOFicheroTest {

    private static final String CONTENIDO_FICHERO =
            "Hello World\n" +
            "Español\n" +
            "_!+@#$%^&*)(,.<>?~!";

    private static final String RUTA_BASE = "./src/test/java/ifp/kikeverea/io/";
    private static final String PATH_FICHERO = RUTA_BASE + "prueba_fichero.txt";

    private IOFichero io;

    @BeforeEach
    void init() {
        io = new IOFicheroImpl();
    }

    @Test
    void leeElContenidoDeUnFichero() throws Exception {
        Assertions.assertEquals(CONTENIDO_FICHERO, io.leerContenido(new File(PATH_FICHERO)));
    }

}

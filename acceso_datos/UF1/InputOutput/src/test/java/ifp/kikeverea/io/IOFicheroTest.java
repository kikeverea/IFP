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

    @Test
    void escribeContenidoEnUnFichero() throws Exception {
        File fichero = new File(RUTA_BASE + "prueba_fichero_dinamica.txt");
        fichero.deleteOnExit();

        io.escribirEnFichero(fichero, CONTENIDO_FICHERO);
        Assertions.assertEquals(CONTENIDO_FICHERO, io.leerContenido(fichero));
    }

    @Test
    void escribirContenidoEnUnFicheroEliminaElContenidoAnterior() throws Exception {
        File fichero = new File(RUTA_BASE + "prueba_fichero_dinamica.txt");
        fichero.deleteOnExit();

        io.escribirEnFichero(fichero, "Este contenido será eliminado");
        io.escribirEnFichero(fichero, CONTENIDO_FICHERO);

        Assertions.assertEquals(CONTENIDO_FICHERO, io.leerContenido(fichero));
    }
}

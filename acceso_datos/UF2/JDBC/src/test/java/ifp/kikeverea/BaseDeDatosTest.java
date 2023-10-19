package ifp.kikeverea;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseDeDatosTest {


    private static final String BASE_DE_DATOS = "base_de_datos_prueba";
    private static final String URL = "jdbc:h2:./"+BASE_DE_DATOS;

    @Test
    void realizaUnaConexionConExito() throws Exception {
        BaseDeDatos bd = new BaseDeDatos();
        bd.connectar(URL);
        Assertions.assertTrue(bd.isConectada());
    }

    @Test
    void realizaUnaDesconexionConExito() throws Exception {
        BaseDeDatos bd = new BaseDeDatos();
        bd.connectar(URL);
        bd.desconectar();

        Assertions.assertFalse(bd.isConectada());
    }
}

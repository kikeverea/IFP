package ifp.kikeverea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class BaseDeDatosTest {

    private static final String BASE_DE_DATOS = "test";
    private static final String URL = "jdbc:mysql://kike:asd@localhost:3306/"+BASE_DE_DATOS;

    private BaseDeDatos bd;

    @BeforeEach
    void init() {
        bd = new BaseDeDatos();
    }

    @AfterEach
    void close() throws Exception {
        bd.desconectar();
    }

    @Test
    void realizaUnaConexionConExito() throws Exception {
        bd.connectar(URL);
        Assertions.assertTrue(bd.isConectada());
    }

    @Test
    void anadeEntidadesConExito() throws Exception {
        Entidad entidad = new Entidad(
                "tabla_prueba",
                List.of(Atributo.nuevoAtributo("id").deTipo(TipoAtributo.NUMERO, RestriccionAtributo.PRIMARY_KEY))
        );
        bd.connectar(URL);
        bd.anadirEntidad(entidad);

        try (Statement query = bd.getConexion().createStatement();
             Statement close = bd.getConexion().createStatement();
             ResultSet result = query.executeQuery(
                     "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'tabla_prueba'"))
        {
            Assertions.assertTrue(result.next());
            close.execute("DROP TABLE tabla_prueba");
        }
    }

    @Test
    void realizaUnaDesconexionConExito() throws Exception {
        bd.connectar(URL);
        bd.desconectar();

        Assertions.assertFalse(bd.isConectada());
    }
}

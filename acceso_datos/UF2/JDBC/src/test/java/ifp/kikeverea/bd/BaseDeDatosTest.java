package ifp.kikeverea.bd;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class BaseDeDatosTest {

    private static final String BASE_DE_DATOS = "test";
    private static final String URL = "jdbc:mysql://kike:asd@localhost:3306/"+BASE_DE_DATOS;
    private static final String TABLA = "tabla_prueba";
    private BaseDeDatos bd;

    private static int mocks = 1;

    @BeforeEach
    void init() throws Exception {
        bd = new BaseDeDatos(BASE_DE_DATOS);
        bd.connectar(URL);
    }

    @AfterEach
    void close() throws Exception {
        if (bd.isConectada()) {
            try (Statement statement = bd.getConexion().createStatement()) {
                statement.execute("DROP TABLE IF EXISTS " + TABLA);
            }

            List<String> entidades = bd.listarEntidades();

            for (String entidad : entidades)
                bd.eliminarEntidad(entidad);

            bd.desconectar();
        }
    }

    @Test
    void realizaUnaConexionConExito() throws Exception {
        // conexión realizada en init()
        Assertions.assertTrue(bd.isConectada());
    }

    @Test
    void creaEntidades() throws Exception {
        Entidad entidad = mock();
        bd.crearEntidad(entidad);

        Assertions.assertTrue(entidadExiste(entidad.getNombre()));
    }

    @Test
    void listaTodasLasEntidades() throws Exception {
        Entidad entidad1 = mock();
        Entidad entidad2 = mock();
        Entidad entidad3 = mock();

        bd.crearEntidad(entidad1);
        bd.crearEntidad(entidad2);
        bd.crearEntidad(entidad3);

        List<String> entidades = bd.listarEntidades();
        List<String> expected = List.of(entidad1.getNombre(), entidad2.getNombre(), entidad3.getNombre());

        Assertions.assertEquals(expected.size(), entidades.size());
        Assertions.assertTrue(expected.containsAll(entidades));
    }

    @Test
    void obtieneEntidadPorNombre() throws Exception {
        Entidad entidad1 = mock();
        Entidad entidad2 = mock();
        Entidad entidad3 = mock();

        bd.crearEntidad(entidad1);
        bd.crearEntidad(entidad2);
        bd.crearEntidad(entidad3);

        Assertions.assertEquals(entidad2, bd.getEntidad(entidad2.getNombre()));
    }

    @Test
    void eliminaEntidades() throws Exception {
        Entidad entidad = mock();

        bd.crearEntidad(entidad);
        Assertions.assertTrue(entidadExiste(entidad.getNombre()));

        bd.eliminarEntidad(entidad.getNombre());
        Assertions.assertFalse(entidadExiste(entidad.getNombre()));
    }

    @Test
    void realizaUnaDesconexionConExito() throws Exception {
        bd.connectar(URL);
        bd.desconectar();

        Assertions.assertFalse(bd.isConectada());
    }

    private Entidad mock() {
        return new Entidad(TABLA+mocks++, List.of(
                Atributo.nuevoAtributo("id").deTipo(TipoAtributo.NUMERO, ClausulaAtributo.PRIMARY_KEY),
                Atributo.nuevoAtributo("nombre").deTipo(TipoAtributo.TEXTO, ClausulaAtributo.UNIQUE)
        ));
    }

    private boolean entidadExiste(String entidad) throws SQLException {
        try (Statement query = bd.getConexion().createStatement();
             ResultSet result = query.executeQuery(
                     "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = '"+entidad+"'"))
        {
            return result.next();
        }
    }
}

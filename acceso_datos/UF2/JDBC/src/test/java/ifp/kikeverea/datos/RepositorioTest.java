package ifp.kikeverea.datos;

import ifp.kikeverea.bd.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RepositorioTest {

    private static final String BASE_DE_DATOS = "test";
    private static final String URL = "jdbc:mysql://kike:asd@localhost:3306/"+BASE_DE_DATOS;
    private static final String TABLA = "entidad_prueba";
    
    private static int mocks = 1;

    private BaseDeDatos bd;
    private Repositorio repositorio;
    private Entidad entidad;

    @BeforeEach
    void init() throws Exception {
        List<Atributo> atributos = List.of(
                Atributo.nuevoAtributo("id").deTipo(TipoAtributo.NUMERO, RestriccionAtributo.PRIMARY_KEY, RestriccionAtributo.AUTO_INCREMENT),
                Atributo.nuevoAtributo("nombre").deTipo(TipoAtributo.TEXTO, RestriccionAtributo.NOT_NULL),
                Atributo.nuevoAtributo("serie").deTipo(TipoAtributo.NUMERO, RestriccionAtributo.UNIQUE));

        entidad = new Entidad(TABLA, atributos);

        bd = new BaseDeDatos(BASE_DE_DATOS);
        bd.connectar(URL);
        bd.crearEntidad(entidad);

        repositorio = new Repositorio(bd, entidad);
    }

    @SuppressWarnings("resource")
    @AfterEach
    void close() throws Exception {
        bd.getConexion().createStatement().execute("DROP TABLE IF EXISTS entidad_prueba" );
        bd.desconectar();
    }

    @Test
    void listaTodosLosObjetosDeUnaEntidad() throws Exception {
        Objeto mock1 = mock();
        Objeto mock2 = mock();
        Objeto mock3 = mock();

        insertarMock(mock1);
        insertarMock(mock2);
        insertarMock(mock3);

        Assertions.assertEquals(List.of(mock1, mock2, mock3), repositorio.listarTodo());
    }

    @Test
    void encuentraUnObjetoPorSuId() throws Exception {
        Objeto mock = mock();
        insertarMock(mock);

        Objeto result = repositorio.buscarPorId(1);
        Assertions.assertEquals(mock, result);
    }

    @Test
    void encuentraUnObjetoPorSusAtributos() throws Exception {
        Objeto mock = mock();
        insertarMock(mock);

        Objeto porSerie = repositorio.buscar(mock.getAtributo("serie"));
        Objeto porNombre = repositorio.buscar(mock.getAtributo("nombre"));

        Assertions.assertEquals(mock, porSerie);
        Assertions.assertEquals(mock, porNombre);
        Assertions.assertEquals(porSerie, porNombre);
    }

    @Test
    void insertaObjetos() throws Exception {
        Objeto mock = mock();

        repositorio.insertar(mock);
        Objeto objeto = repositorio.buscarPorId(1);
        Assertions.assertEquals(mock, objeto);
    }

    @Test
    void generaUnaClavePrimariaParaLosObjetosInsertados() throws Exception {
        Objeto mock = mock();
        Object clavePrimaria = mock.getClavePrimaria().valor();
        Assertions.assertTrue(clavePrimaria == null || (int) clavePrimaria == 0);

        repositorio.insertar(mock);

        int expectedClavePrimaria = 1;
        Assertions.assertEquals(expectedClavePrimaria, mock.getAtributo("id").valor());
    }

    @Test
    void modificaObjetos() throws Exception {
        Objeto mock = mock();

        Object serieInicial = mock.getAtributo("serie").valor();
        Object nombreInicial = mock.getAtributo("nombre").valor();
        Object serieFinal = (int) serieInicial + 1;

        repositorio.insertar(mock);
        mock.setValor("serie", serieFinal);
        repositorio.actualizarObjeto(mock);

        Objeto actualizado = repositorio.buscarPorId(1);
        Assertions.assertEquals(serieFinal, actualizado.getAtributo("serie").valor());
        Assertions.assertEquals(nombreInicial, actualizado.getAtributo("nombre").valor());
    }

    @Test
    void eliminaObjetos() throws Exception {
        Objeto mock = mock();

        repositorio.insertar(mock);
        Assertions.assertNotNull(repositorio.buscarPorId(1));

        repositorio.eliminar(mock);
        Assertions.assertNull(repositorio.buscarPorId(1));
    }

    private Objeto mock() {
        int numeroMock = mocks++;
        Objeto mock = Objeto.instanciaDe(entidad);
        mock.setValor("nombre", "mock"+numeroMock);
        mock.setValor("serie", numeroMock);
        return mock;
    }

    private void insertarMock(Objeto mock) throws SQLException {
        ValorAtributo serie = mock.getAtributo("serie");
        ValorAtributo nombre = mock.getAtributo("nombre");

        try (Statement statement = bd.getConexion().createStatement()) {
            statement.executeUpdate(
                    "INSERT INTO " + TABLA + " (serie, nombre) " +
                    "VALUES ("+serie.valor()+", '"+nombre.valor()+"')",
                    Statement.RETURN_GENERATED_KEYS
            );

            ResultSet clave = statement.getGeneratedKeys();
            clave.next();
            mock.setValor("id", clave.getInt(1));
        }
    }
}

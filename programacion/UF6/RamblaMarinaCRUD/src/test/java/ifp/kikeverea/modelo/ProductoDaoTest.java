package ifp.kikeverea.modelo;

import ifp.kikeverea.RandomUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ProductoDaoTest {

    private Connection connection;
    private ProductoDao dao;


    @BeforeEach
    void init() throws Exception {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "");
        dao = new ProductoDao(connection);
    }

    @AfterEach
    void close() throws Exception {
        connection.createStatement().execute("DELETE FROM productos");
        connection.close();
    }

    @Test
    void givenProduct_insertInDb_insertSuccessful_returnTrue() {
        Producto producto = randomProduct();
        Assertions.assertTrue(dao.insertar(producto));
    }

    @Test
    void givenProduct_insertInDb_insertFailed_returnFalse() throws Exception {
        Producto producto = randomProduct();
        ProductoDao mockDao = mockFailDao();
        Assertions.assertFalse(mockDao.insertar(producto));
    }

    @Test
    void givenProduct_insertInDb_queryDb_productIsPresent() {
        Producto product = randomProduct();
        dao.insertar(product);

        Producto inDb = dao.consultarUltimo();
        Assertions.assertEquals(product, inDb);
    }

    @Test
    void givenEmptyDatabase_queryAll_returnsEmptyList() {
        List<Producto> products = dao.consultarTodos();
        Assertions.assertTrue(products.isEmpty());
    }

    @Test
    void givenInsertedProducts_queryAll_returnsAllInsertedProducts() {
        Producto product1 = randomProduct();
        Producto product2 = randomProduct();
        Producto product3 = randomProduct();

        dao.insertar(product1);
        dao.insertar(product2);
        dao.insertar(product3);

        List<Producto> products = dao.consultarTodos();
        Assertions.assertTrue(products.contains(product1));
        Assertions.assertTrue(products.contains(product2));
        Assertions.assertTrue(products.contains(product3));
    }

    @Test
    void givenProductInDb_update_updateFailed_returnFalse() throws Exception {
        Producto producto = randomProduct();
        dao.insertar(producto);
        producto.setId(dao.consultarUltimo().getId());

        ProductoDao mockDao = mockFailDao();
        Assertions.assertFalse(mockDao.modificar(producto));
    }

    @Test
    void givenProductInDb_updateProduct_returnsTrue() {
        Producto product = randomProduct();
        dao.insertar(product);

        int inDbId = dao.consultarUltimo().getId();
        Producto toUpdate = randomProduct();
        toUpdate.setId(inDbId);

        Assertions.assertTrue(dao.modificar(toUpdate));
    }

    @Test
    void givenProductInDb_updateProduct_updatedIsInDb() {
        Producto product1 = randomProduct();
        Producto product2 = randomProduct();
        Producto product3 = randomProduct();

        dao.insertar(product1);
        dao.insertar(product2);
        dao.insertar(product3);

        List<Producto> products = dao.consultarTodos();

        int idToUpdate = products.get(RandomUtil.random(3)).getId();

        Producto toUpdate = randomProduct();
        toUpdate.setId(idToUpdate);

        dao.modificar(toUpdate);
        Producto updated = dao.consultarId(idToUpdate);

        Assertions.assertEquals(toUpdate, updated);
    }

    @Test
    void givenProductWithNoId_executeUpdate_throws() {
        Producto product = randomProduct();
        Assertions.assertThrows(IllegalArgumentException.class, ()-> dao.modificar(product));
        Assertions.assertThrows(IllegalArgumentException.class, ()-> dao.eliminar(product));
    }

    @Test
    void givenProductNotInDb_executeUpdate_failsAndReturnFalse() {
        int falseId = dao.consultarTodos()
                .stream()
                .map(Producto::getId)
                .max(Integer::compareTo)
                .orElse(1);
        Producto product = randomProduct();
        product.setId(falseId);

        Assertions.assertFalse(dao.modificar(product));
        Assertions.assertFalse(dao.eliminar(product));
    }

    @Test
    void givenProductInDb_deleteProduct_returnsTrue() {
        Producto product = randomProduct();
        dao.insertar(product);

        int inDbId = dao.consultarUltimo().getId();
        Producto toDelete = randomProduct();
        toDelete.setId(inDbId);

        Assertions.assertTrue(dao.eliminar(toDelete));
    }

    @Test
    void givenProductInDb_deleteProduct_productIsNotInDb() {
        Producto product1 = randomProduct();
        Producto product2 = randomProduct();
        Producto product3 = randomProduct();

        dao.insertar(product1);
        dao.insertar(product2);
        dao.insertar(product3);

        List<Producto> products = dao.consultarTodos();

        Producto toDelete = products.get(RandomUtil.random(3));
        dao.eliminar(toDelete);

        Producto deleted = dao.consultarId(toDelete.getId());
        Assertions.assertNull(deleted);
    }

    @Test
    void givenProductInDb_delete_deleteFailed_returnFalse() throws Exception {
        Producto producto = randomProduct();
        dao.insertar(producto);
        producto.setId(dao.consultarUltimo().getId());

        ProductoDao mockDao = mockFailDao();
        Assertions.assertFalse(mockDao.eliminar(producto));
    }

    private Producto randomProduct() {
        String name = RandomUtil.randomString();
        float price = RandomUtil.randomFloat(10, 2);
        int quantity = RandomUtil.random(10);

        return new Producto(name, price, quantity);
    }

    private ProductoDao mockFailDao() throws Exception {
        Connection mockConnection = Mockito.mock(Connection.class);
        Statement mockStatement = Mockito.mock(Statement.class);
        ProductoDao mockDao = new ProductoDao(mockConnection);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeUpdate(any())).thenReturn(1);
        when(mockStatement.executeUpdate(any())).thenThrow(new SQLException());

        return mockDao;
    }
}

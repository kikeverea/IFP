package ifp.kikeverea.productos;

import ifp.kikeverea.ClassTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ProductoTest {

    private static final int ID = 5;
    private static final String NAME = "Product";
    private static final String TYPE = "Type";
    private static final float WIDTH = 5.6f;
    private static final float DEPTH = 6.0f;
    private static final float HEIGHT = 7.5f;

    private final TestProduct product = new TestProduct(
        ID, NAME, TYPE, WIDTH, DEPTH, HEIGHT
    );

    @Test
    void callMostrarInfo_returnFieldValues() {
        String expected =
                "Identificación: " + ID + " - " +
                "Tipo: " + TYPE + " - " +
                "Nombre: " + NAME + " - " +
                "Anchura: " + WIDTH + " - " +
                "Profundidad: " + DEPTH + " - " +
                "Altura: " + HEIGHT;

        Assertions.assertEquals(expected, product.mostrarInfo());
    }

    @Test
    void givenProductoClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(Producto.class);
        Set<String> requiredFields = Set.of("id", "nombre", "tipo", "anchura", "profundidad", "altura");

        Assertions.assertTrue(classTest.allFieldsPresent(requiredFields));
        Assertions.assertTrue(classTest.allFieldsHaveGettersAndSetters());
        Assertions.assertTrue(classTest.methodPresent("mostrarInfo"));
    }

    private static final class TestProduct extends Producto {

        public TestProduct(int id, String nombre, String tipo, float anchura, float profundidad, float altura) {
            super(id, nombre, tipo, anchura, profundidad, altura);
        }
    }
}

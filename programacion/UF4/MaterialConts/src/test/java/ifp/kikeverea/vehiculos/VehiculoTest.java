package ifp.kikeverea.vehiculos;

import ifp.kikeverea.ClassTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class VehiculoTest {
    private static final String BRAND = "Good";
    private static final String COLOR = "Blue";
    private static final float PRICE = 5.6f;
    private static final float LENGTH = 6.0f;
    private static final float WEIGHT = 7.5f;

    private final TestVehiculo vehicle = new TestVehiculo(BRAND, COLOR, PRICE, LENGTH, WEIGHT);

    @Test
    void callMostrarInfo_returnFieldValues() {
        String expected =
                "Marca: " + BRAND + " - " +
                "Color: " + COLOR + " - " +
                "Precio: " + PRICE + " - " +
                "Longitud: " + LENGTH + " - " +
                "Peso: " + WEIGHT;

        Assertions.assertEquals(expected, vehicle.mostrarInfo());
    }

    @Test
    void givenVehiculoClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(Vehiculo.class);
        Set<String> requiredFields = Set.of("marca", "precio", "peso", "color", "longitud");

        Assertions.assertTrue(classTest.allFieldsPresent(requiredFields));
        Assertions.assertTrue(classTest.allFieldsHaveGettersAndSetters());
        Assertions.assertTrue(classTest.methodPresent("mostrarInfo"));
    }

    @Test
    void givenVehiculoGasolinaClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(VehiculoGasolina.class);
        Set<String> requiredFields = Set.of("contaminacion", "tamanoDeposito");

        Assertions.assertTrue(classTest.allFieldsPresent(requiredFields));
        Assertions.assertTrue(classTest.allFieldsHaveGettersAndSetters());
        Assertions.assertTrue(classTest.methodPresent("mostrarInfo"));
    }

    @Test
    void givenVehiculoElectricoClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(VehiculoElectrico.class);
        Set<String> requiredFields = Set.of("potencia");

        Assertions.assertTrue(classTest.allFieldsPresent(requiredFields));
        Assertions.assertTrue(classTest.allFieldsHaveGettersAndSetters());
        Assertions.assertTrue(classTest.methodPresent("mostrarInfo"));
    }

    private static final class TestVehiculo extends Vehiculo {

        public TestVehiculo(String marca, String color, float precio, float longitud, float peso) {
            super(marca, color, precio, longitud, peso);
        }
    }
}

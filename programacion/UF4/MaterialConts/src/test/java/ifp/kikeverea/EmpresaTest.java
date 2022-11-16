package ifp.kikeverea;

import ifp.kikeverea.edificios.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ifp.kikeverea.productos.Producto;
import ifp.kikeverea.productos.Puerta;
import ifp.kikeverea.productos.Silla;
import ifp.kikeverea.vehiculos.Vehiculo;
import ifp.kikeverea.vehiculos.VehiculoElectrico;
import ifp.kikeverea.vehiculos.VehiculoGasoil;

import java.util.Set;

public class EmpresaTest {

    private static final Vehiculo VEHICULO_1 = new VehiculoElectrico("Seat Altea", "rojo", 20000, 3, 3000, 4000);
    private static final Vehiculo VEHICULO_2 = new VehiculoGasoil("Renault Kadjar", "azul", 28000, 3.5f, 4000);
    private static final Edificio EDIFICIO_1 = new Fabrica(400, 400, 500);
    private static final Edificio EDIFICIO_2 = new Oficina(600, 400, 700);
    private static final Edificio EDIFICIO_3 = new Almacen(600, 400, 700);
    private static final Producto PRODUCTO_1 = new Silla(1, "Silla N30", 1, 0.5f, 0.5f);
    private static final Producto PRODUCTO_2 = new Silla(2, "Silla M100", 1.5f, 0.5f, 1.5f);
    private static final Producto PRODUCTO_3 = new Puerta(3, "Puerta P9", 1.5f, 2.25f, 0.25f);
    private static final Producto PRODUCTO_4 = new Puerta(4, "Puerta F50", 1.25f, 1.25f, 0.25f);

    private Empresa empresa;

    @BeforeEach
    void init() {
        empresa = new Empresa();
    }

    @Test
    void callMostrarInfo_returnFieldValues() {
        populateEmpresa();
        String expected =
                "Vehículos:\n" +
                    "\tVehículo 1: " + VEHICULO_1.mostrarInfo() + "\n" +
                    "\tVehículo 2: " + VEHICULO_2.mostrarInfo() + "\n" +
                "Edificios:\n" +
                    "\tEdificio 1: " + EDIFICIO_1.mostrarInfo() + "\n" +
                    "\tEdificio 2: " + EDIFICIO_2.mostrarInfo() + "\n" +
                    "\tEdificio 3: " + EDIFICIO_3.mostrarInfo() + "\n" +
                "Productos:\n" +
                    "\tProducto 1: " + PRODUCTO_1.mostrarInfo() + "\n" +
                    "\tProducto 2: " + PRODUCTO_2.mostrarInfo() + "\n" +
                    "\tProducto 3: " + PRODUCTO_3.mostrarInfo() + "\n" +
                    "\tProducto 4: " + PRODUCTO_4.mostrarInfo() + "\n";

        Assertions.assertEquals(expected, empresa.mostrarInfo());
    }

    @Test
    void givenEmpresaWithNoObjects_callMostrarInfo_returnBlankObjects() {
        String expected = "Vehículos:\n" +
                        "Edificios:\n" +
                        "Productos:\n";

        Assertions.assertEquals(expected, empresa.mostrarInfo());
    }

    @Test
    void givenProductoClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(Empresa.class);
        Set<String> requiredFields = Set.of("vehiculo1", "vehiculo2",
                                            "edificio1", "edificio2", "edificio3",
                                            "producto1", "producto2", "producto3", "producto4");

        Assertions.assertTrue(classTest.allFieldsPresent(requiredFields));
        Assertions.assertTrue(classTest.allFieldsHaveGettersAndSetters());
        Assertions.assertTrue(classTest.methodPresent("mostrarInfo"));
    }

    private void populateEmpresa() {
        empresa.setEdificio1(EDIFICIO_1);
        empresa.setEdificio2(EDIFICIO_2);
        empresa.setEdificio3(EDIFICIO_3);
        empresa.setVehiculo1(VEHICULO_1);
        empresa.setVehiculo2(VEHICULO_2);
        empresa.setProducto1(PRODUCTO_1);
        empresa.setProducto2(PRODUCTO_2);
        empresa.setProducto3(PRODUCTO_3);
        empresa.setProducto4(PRODUCTO_4);
    }
}

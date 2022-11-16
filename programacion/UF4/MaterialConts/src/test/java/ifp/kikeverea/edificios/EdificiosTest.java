package ifp.kikeverea.edificios;

import ifp.kikeverea.ClassTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class EdificiosTest {

    private static final float ANCHURA = 5.6f;
    private static final float ALTURA = 6.7f;
    private static final float PROFUNDIDAD = 8.9f;
    private static final String FUNCIONALIDAD = "test";
    private static final String TIPO = "tipo";

    private final Edificio edificio = new TestEdificio(ANCHURA, ALTURA, PROFUNDIDAD, TIPO);

    @Test
    void givenPricePerMeterPaintCost_returnCostPerCubicMeter() {
        //wrong formula, I know (perimeter should be calculated, not volume)

        float pricePerMeter = randomPrice();
        float expected = ANCHURA * ALTURA * PROFUNDIDAD * pricePerMeter;
        float actual = edificio.costePintura(pricePerMeter);

        Assertions.assertTrue(trivialDifference(expected, actual));
    }

    @Test
    void givenPricePerMeterPaintCost_andWidthPaintStrategy_returnWidthCostPerCubicMeter() {
        //wrong formula, I know (perimeter should be calculated, not volume)

        float pricePerMeter = randomPrice();
        float expected = ALTURA * ANCHURA * pricePerMeter;
        float actual = edificio.costePintura(Edificio.LADO_ANCHURA, pricePerMeter);

        Assertions.assertTrue(trivialDifference(expected, actual));
    }

    @Test
    void givenPricePerMeterPaintCost_andLengthPaintStrategy_returnLengthCostPerCubicMeter() {
        //wrong formula, I know (perimeter should be calculated, not volume)

        float pricePerMeter = randomPrice();
        float expected = ALTURA * PROFUNDIDAD * pricePerMeter;
        float actual = edificio.costePintura(Edificio.LADO_PROFUNDIDAD, pricePerMeter);

        Assertions.assertTrue(trivialDifference(expected, actual));
    }

    @Test
    void givenNegativePricePerMeterPaintCost_callBuildingPainCost_returnMinusOne() {
        Assertions.assertEquals(-1, edificio.costePintura(-5));
        Assertions.assertEquals(-1, edificio.costePintura(Edificio.LADO_ANCHURA, -1));
        Assertions.assertEquals(-1, edificio.costePintura(Edificio.LADO_PROFUNDIDAD, -2));
    }

    @Test
    void callBuildingFunctionality_returnFunctionality() {
        Assertions.assertEquals(FUNCIONALIDAD, edificio.funcionalidadEdificio());
    }

    @Test
    void callMostrarInfo_returnFieldValues() {
        String expected =
                "Tipo: " + TIPO + " - " +
                "Funcionalidad: " + FUNCIONALIDAD + " - " +
                "Anchura: " + ANCHURA + " - " +
                "Altura: " + ALTURA + " - " +
                "Profundidad: " + PROFUNDIDAD + " - " +
                "More Info: info";

        Assertions.assertEquals(expected, edificio.mostrarInfo());
    }

    @Test
    void givenEdificioClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(Edificio.class);
        Set<String> requiredFields = Set.of("tipo", "altura", "anchura", "profundidad");

        Assertions.assertTrue(classTest.allFieldsPresent(requiredFields));
        Assertions.assertTrue(classTest.allFieldsHaveGettersAndSetters());
        Assertions.assertTrue(classTest.methodPresent("funcionalidadEdificio"));
        Assertions.assertTrue(classTest.methodPresent("mostrarInfo"));
    }

    @Test
    void givenFabricaClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(FabricaTest.class);
        Assertions.assertTrue(classTest.methodPresent("funcionalidadEdificio"));
    }

    @Test
    void givenAlmacenClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(Almacen.class);
        Assertions.assertTrue(classTest.methodPresent("funcionalidadEdificio"));
    }

    @Test
    void givenOficinaClass_assertRequiredStructure() {
        ClassTestUtil classTest = new ClassTestUtil(Oficina.class);
        Assertions.assertTrue(classTest.methodPresent("funcionalidadEdificio"));
    }

    private float randomPrice() {
        return Math.max(1, ThreadLocalRandom.current().nextFloat()) * 10;
    }

    private boolean trivialDifference(float n1, float n2) {
        return Math.abs(n1 - n2) < 0.001;
    }

    private static final class TestEdificio extends Edificio {

        public TestEdificio(float anchura, float altura, float profundidad, String tipo)
        {
            super(anchura, altura, profundidad, tipo);
        }

        @Override
        public String funcionalidadEdificio() {
            return FUNCIONALIDAD;
        }

        @Override
        public String mostrarInfo() {
            return super.mostrarInfo() + " - More Info: info";
        }
    }

}

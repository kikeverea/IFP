package ejercicio1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class Ejercicio1Test {

    @Test
    void dividirDosNumerosEntreSiProduceElResultadoCorrecto() {
        int num1 = 10;
        int num2 = 2;

        int resultadoEsperado = 5;
        int resultado = ejercicio1.resultadoDivision(num1, num2);

        Assertions.assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void dividirUnNumeroNegativoConOtroPositivoProduceUnValorNegativo() {
        int num1 = -10;
        int num2 = 2;

        int resultadoEsperado = -5;
        int resultado = ejercicio1.resultadoDivision(num1, num2);

        Assertions.assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void dividirDosNumerosNegativosEntreSiProduceUnValorPositivo() {
        int num1 = -10;
        int num2 = -2;

        int resultadoEsperado = 5;
        int resultado = ejercicio1.resultadoDivision(num1, num2);

        Assertions.assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void dividirCeroEntreCualquierNumeroProduceCero() {
        int num1 = 0;
        int num2 = ThreadLocalRandom.current().nextInt();

        int resultadoEsperado = 0;
        int resultado = ejercicio1.resultadoDivision(num1, num2);

        Assertions.assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void dividirCualquierNumeroEntreCeroLanzaUnaExcepcion() {
        int num1 = ThreadLocalRandom.current().nextInt();
        int num2 = 0;

        Assertions.assertThrows(ArithmeticException.class, () -> ejercicio1.resultadoDivision(num1, num2));
    }

    @Test
    void laDivisionDeEnterosSoloProduceNumerosEnteros() {
        int num1 = 9;
        int num2 = 2;

        double resultadoReal = 4.5;
        int resultadoEsperado = 4;
        int resultado = ejercicio1.resultadoDivision(num1, num2);

        Assertions.assertNotEquals(resultadoReal, resultado);
        Assertions.assertEquals(resultadoEsperado, resultado);
    }

    @Test
    void laDivisionDeNumerosDecimalesProduceElResultadoDeLaDivisionDeSusPartesEnteras() {
        double resultadoReal = 1.8; // -> 4.5 / 2.5
        int resultadoEsperado = 2;  // -> 4 / 2

        int resultado = ejercicio1.resultadoDivision(9/2, 5/2);

        Assertions.assertNotEquals(resultadoReal, resultado);
        Assertions.assertEquals(resultadoEsperado, resultado);
    }
}

package ifp.kikeverea;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorTest {

    @Test
    void givenTwoNumbers_callSuma_addsBothNumbers() {
        Assertions.assertEquals(11, Start.suma(6, 5));
    }

    @Test
    void givenTwoNumbers_callResta_subtractsBothNumbers() {
        Assertions.assertEquals(1, Start.resta(6, 5));
    }

    @Test
    void givenTwoNumbers_callMultiplicacion_multipliesBothNumbers() {
        Assertions.assertEquals(30, Start.multiplicacion(6, 5));
    }

    @Test
    void givenTwoNumbers_callDivision_dividesBothNumbers() {
        Assertions.assertEquals(2, Start.division(6, 3));
    }

    @Test
    void givenTwoNumbers__theSecondOfWhichIsZero_callDivision_returnMinusOne() {
        Assertions.assertEquals(-1, Start.division(6, 0));
    }

    @Test
    void givenThreeNumbers_callMayor_returnTheHighestNumber() {
        Assertions.assertEquals(10, Start.mayor(-5, 0, 10));
    }

    @Test
    void givenInteger_callFactorial_returnItsFactorial() {
        Assertions.assertEquals(1, Start.factorial(1));
        Assertions.assertEquals(2, Start.factorial(2));
        Assertions.assertEquals(6, Start.factorial(3));
        Assertions.assertEquals(24, Start.factorial(4));
        Assertions.assertEquals(120, Start.factorial(5));
        Assertions.assertEquals(720, Start.factorial(6));
    }

    @Test
    void givenIntegerLessThanOne_callFactorial_returnMinusOne() {
        Assertions.assertEquals(-1, Start.factorial(-1));
        Assertions.assertEquals(-1, Start.factorial(0));
        Assertions.assertNotEquals(-1, Start.factorial(1));
    }

}

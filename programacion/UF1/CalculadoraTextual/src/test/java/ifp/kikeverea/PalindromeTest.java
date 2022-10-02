package ifp.kikeverea;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PalindromeTest {

    @ParameterizedTest
    @ValueSource(strings = {"1", "11", "111", "1111", "1881", "323", "123321", "1234321"})
    void givenPalindrome_evaluateToTrue(String s) {
        Assertions.assertTrue(Start.esCapicua(s));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "1882", "1818", "-1818"})
    void givenNonPalindrome_evaluateToFalse(String s) {
        Assertions.assertFalse(Start.esCapicua(s));
    }
}

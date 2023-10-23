package ifp.kikeverea;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AtributoTest {

    @Test
    void produceUnaExcepcionSiSeCreaConUnNombreVacio() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Atributo.nuevoAtributo(null).deTipo(TipoAtributo.NUMERO));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Atributo.nuevoAtributo("").deTipo(TipoAtributo.NUMERO));

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> Atributo.nuevoAtributo("     ").deTipo(TipoAtributo.NUMERO));
    }

    @Test
    void produceSuPropiaDefinicion() {
        Atributo atributo = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO);
        Assertions.assertEquals("prueba INTEGER", atributo.definicion());
    }

    @Test
    void seIdentificaComoPrimaryKeyCorrectamente() {
        Atributo primaryKey = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO, RestriccionAtributo.PRIMARY_KEY);
        Atributo noPrimaryKey = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO);

        Assertions.assertTrue(primaryKey.esPrimaryKey());
        Assertions.assertFalse(noPrimaryKey.esPrimaryKey());
    }
}

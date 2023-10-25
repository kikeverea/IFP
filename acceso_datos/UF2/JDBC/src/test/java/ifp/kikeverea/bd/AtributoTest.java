package ifp.kikeverea.bd;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.RestriccionAtributo;
import ifp.kikeverea.bd.TipoAtributo;
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
    void anadeAutoIncrementYNotNullSiEsClavePrimariaYNoSeEspecifica() {
        Atributo atributo = Atributo.nuevoAtributo("cp").deTipo(
                TipoAtributo.NUMERO,
                RestriccionAtributo.PRIMARY_KEY);

        Atributo expected = Atributo.nuevoAtributo("cp").deTipo(
                TipoAtributo.NUMERO,
                RestriccionAtributo.PRIMARY_KEY,
                RestriccionAtributo.AUTO_INCREMENT);

        Assertions.assertEquals(expected, atributo);
    }

    @Test
    void produceSuPropiaDefinicion() {
        Atributo atributo = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO);
        Assertions.assertEquals("prueba INTEGER", atributo.definicion());
    }

    @Test
    void seIdentificaComoClavePrimariaCorrectamente() {
        Atributo ClavePrimaria = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO, RestriccionAtributo.PRIMARY_KEY);
        Atributo noClavePrimaria = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO);

        Assertions.assertTrue(ClavePrimaria.esClavePrimaria());
        Assertions.assertFalse(noClavePrimaria.esClavePrimaria());
    }
}

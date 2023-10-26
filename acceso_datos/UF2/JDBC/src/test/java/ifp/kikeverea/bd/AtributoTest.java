package ifp.kikeverea.bd;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    void anadeAutoIncrementYNotNullSiEsClavePrimariaNumeroYNoSeEspecifica() {
        Atributo atributo = Atributo.nuevoAtributo("cp").deTipo(
                TipoAtributo.NUMERO,
                ClausulaAtributo.PRIMARY_KEY);

        List<ClausulaAtributo> expected = List.of(
                ClausulaAtributo.PRIMARY_KEY,
                ClausulaAtributo.AUTO_INCREMENT,
                ClausulaAtributo.NOT_NULL);

        Assertions.assertEquals(expected, atributo.getClausulas());
    }

    @Test
    void anadeNotNullSiEsClavePrimariaTextoYNoSeEspecifica() {
        Atributo atributo = Atributo.nuevoAtributo("cp").deTipo(
                TipoAtributo.TEXTO,
                ClausulaAtributo.PRIMARY_KEY);

        List<ClausulaAtributo> expected = List.of(
                ClausulaAtributo.PRIMARY_KEY,
                ClausulaAtributo.NOT_NULL);

        Assertions.assertEquals(expected, atributo.getClausulas());
    }

    @Test
    void noPermiteLaCreacionConDeTipoTextoAutoIncrement() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> Atributo.nuevoAtributo("cp").deTipo(
                        TipoAtributo.TEXTO,
                        ClausulaAtributo.AUTO_INCREMENT));
    }

    @Test
    void produceSuPropiaDefinicion() {
        Atributo atributo = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO);
        Assertions.assertEquals("prueba INTEGER", atributo.definicion());
    }

    @Test
    void seIdentificaComoClavePrimariaCorrectamente() {
        Atributo ClavePrimaria = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO, ClausulaAtributo.PRIMARY_KEY);
        Atributo noClavePrimaria = Atributo.nuevoAtributo("prueba").deTipo(TipoAtributo.NUMERO);

        Assertions.assertTrue(ClavePrimaria.esClavePrimaria());
        Assertions.assertFalse(noClavePrimaria.esClavePrimaria());
    }
}

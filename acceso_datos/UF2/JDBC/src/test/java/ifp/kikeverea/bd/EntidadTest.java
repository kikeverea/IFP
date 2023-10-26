package ifp.kikeverea.bd;

import ifp.kikeverea.datos.Objeto;
import ifp.kikeverea.datos.ValorAtributo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EntidadTest {

    @Test
    void identificaLaClavePrimariaEntreLaListaDeAtributos() {
        Atributo clavePrimaria = Atributo.nuevoAtributo("id").deTipo(TipoAtributo.NUMERO, ClausulaAtributo.PRIMARY_KEY);
        List<Atributo> atributos = List.of(
                Atributo.nuevoAtributo("nombre").deTipo(TipoAtributo.TEXTO, ClausulaAtributo.UNIQUE, ClausulaAtributo.NOT_NULL),
                clavePrimaria,
                Atributo.nuevoAtributo("descripcionombre").deTipo(TipoAtributo.TEXTO)
        );

        Entidad entidad = new Entidad("prueba", atributos);

        Assertions.assertEquals(clavePrimaria, entidad.getClavePrimaria());
    }

    @Test
    void anadeUnAtributoComoClavePrimariaSiSeCreaSinUna() {
        Entidad entidad = new Entidad("prueba", List.of(Atributo.nuevoAtributo("no_es_clave").deTipo(TipoAtributo.NUMERO)));
        Atributo expected = clavePrimariaValida("id");

        Assertions.assertEquals(expected, entidad.getClavePrimaria());
    }

    @Test
    void generaObjetosDeEstaEntidad() {
        List<Atributo> atributos = List.of(
                Atributo.nuevoAtributo("id").deTipo(TipoAtributo.NUMERO, ClausulaAtributo.PRIMARY_KEY),
                Atributo.nuevoAtributo("nombre").deTipo(TipoAtributo.TEXTO, ClausulaAtributo.UNIQUE, ClausulaAtributo.NOT_NULL),
                Atributo.nuevoAtributo("descripcionombre").deTipo(TipoAtributo.TEXTO)
        );
        Entidad entidad = new Entidad("prueba", atributos);
        Objeto objeto = Objeto.instanciaDe(entidad);

        List<ValorAtributo> valorAtributos = objeto.getAtributos();

        Assertions.assertEquals(atributos.size(), valorAtributos.size());
        Assertions.assertTrue(contieneTodos(atributos, valorAtributos));
    }

    private Atributo clavePrimariaValida(String nombre) {
        return Atributo.nuevoAtributo(nombre).deTipo(
                TipoAtributo.NUMERO,
                ClausulaAtributo.PRIMARY_KEY,
                ClausulaAtributo.AUTO_INCREMENT);
    }

    private boolean contieneTodos(List<Atributo> atributos, List<ValorAtributo> valorAtributos) {
        boolean contieneTodos = true;

        for (ValorAtributo valorAtributo : valorAtributos) {
            boolean contieneAtributo = atributos
                    .stream()
                    .filter(atributo -> esMismoAtributo(atributo, valorAtributo))
                    .count() == 1;
            if (!contieneAtributo) {
                contieneTodos = false;
                break;
            }
        }

        return contieneTodos;
    }

    private boolean esMismoAtributo(Atributo atributo, ValorAtributo valorAtributo) {
        return  atributo.getNombre().equals(valorAtributo.nombreAtributo()) &&
                atributo.getTipo().getTipoSql() == (valorAtributo.tipoSql());
    }
}

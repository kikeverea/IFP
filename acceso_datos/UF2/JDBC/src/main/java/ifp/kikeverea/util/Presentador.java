package ifp.kikeverea.util;

import ifp.kikeverea.datos.Objeto;
import ifp.kikeverea.datos.ValorAtributo;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Presentador {

    public static <T> String separadoPorComas(Collection<T> valores, Function<T,String> aTexto) {
        return valores
                .stream()
                .map(aTexto)
                .collect(Collectors.joining(", "));
    }

    public static String objetoATexto(Objeto objeto, String nombreEntidad) {
        String atributos = Presentador.separadoPorComas(objeto.getValoresAtributos(), ValorAtributo::toString);
        return "("+nombreEntidad+") "+ atributos;
    }
}

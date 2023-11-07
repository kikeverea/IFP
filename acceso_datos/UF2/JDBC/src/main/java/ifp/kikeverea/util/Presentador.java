package ifp.kikeverea.util;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Presentador {

    public static <T> String separadoPorComas(Collection<T> valores, Function<T,String> aTexto) {
        return separar(valores, aTexto, ", ");
    }

    public static <T> String separadoPorEspacios(Collection<T> valores, Function<T,String> aTexto) {
        return separar(valores, aTexto, " ");
    }

    private static <T> String separar(Collection<T> valores, Function<T,String> aTexto, String separador) {
        return valores
                .stream()
                .map(aTexto)
                .collect(Collectors.joining(separador));
    }
}

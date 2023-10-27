package ifp.kikeverea.util;

import ifp.kikeverea.datos.Objeto;
import ifp.kikeverea.datos.ValorAtributo;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Presentador {

    public static <T> String separadoPorComas(Collection<T> valores, Function<T,String> aTexto) {
        return valores
                .stream()
                .map(aTexto)
                .collect(Collectors.joining(", "));
    }

    public static String objetosATexto(List<Objeto> objetos, String nombreEntidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objetos.size(); i++) {
            Objeto objeto = objetos.get(i);

            String textoObjeto = Presentador.objetoATexto(objeto, nombreEntidad);
            sb.append(textoObjeto);

            if (i < objetos.size() -1)
                sb.append("\n");
        }

        return sb.toString();
    }

    public static String objetoATexto(Objeto objeto, String nombreEntidad) {
        String atributos = Presentador.separadoPorComas(objeto.getValoresAtributos(), ValorAtributo::toString);
        return "("+nombreEntidad+") "+ atributos;
    }
}

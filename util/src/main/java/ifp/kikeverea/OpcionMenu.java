package ifp.kikeverea.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OpcionMenu<T> {
    private final T objeto;
    private final String mensaje;

    private OpcionMenu(T objeto, Function<T,String> aTexto) {
        this.objeto = objeto;
        this.mensaje = aTexto.apply(objeto);
    }

    public String nombre() {
        return mensaje;
    }

    public T objeto() {
        return objeto;
    }

    public static OpcionMenu<String> opcionSimple(String mensaje) {
        return opcion(mensaje, s-> s);
    }

    public static List<OpcionMenu<String>> opcionesSimples(String... opciones) {
        return opcionesSimples(Arrays.asList(opciones));
    }

    public static List<OpcionMenu<String>> opcionesSimples(List<String> opciones) {
        return opciones
                .stream()
                .map(OpcionMenu::opcionSimple)
                .collect(Collectors.toList());
    }

    public static <T> OpcionMenu<T> opcion(T objeto, Function<T,String> aTexto) {
        return new OpcionMenu<>(objeto, aTexto);
    }

    @SafeVarargs public static <T> List<OpcionMenu<T>> opciones(Function<T,String> aTexto, T... opciones) {
        return opciones(Arrays.asList(opciones), aTexto);
    }

    public static <T> List<OpcionMenu<T>> opciones(List<T> opciones, Function<T,String> aTexto) {
        return opciones
                .stream()
                .map(obj -> new OpcionMenu<>(obj, aTexto))
                .collect(Collectors.toList());
    }
    public static <T> OpcionMenu<T> opcionNull(String nombre) {
        return new OpcionMenu<>(null, o -> nombre);
    }
}

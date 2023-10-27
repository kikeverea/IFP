package ifp.kikeverea.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface OpcionMenu<T> {
    T objeto();
    String nombre();

    static OpcionMenu<String> opcionSimple(String mensaje) {
        return opcion(mensaje, s-> s);
    }

    static List<OpcionMenu<String>> opcionesSimples(String... opciones) {
        return opcionesSimples(Arrays.asList(opciones));
    }

    static List<OpcionMenu<String>> opcionesSimples(List<String> opciones) {
        return opciones
                .stream()
                .map(OpcionMenu::opcionSimple)
                .collect(Collectors.toList());
    }

    static <T> Opcion<T> opcion(T objeto, Function<T,String> aTexto) {
        return new Opcion<>(objeto, aTexto);
    }

    static <T> List<OpcionMenu<T>> opciones(Function<T,String> aTexto, T... opciones) {
        return opciones(Arrays.asList(opciones), aTexto);
    }

    static <T> List<OpcionMenu<T>> opciones(List<T> opciones, Function<T,String> aTexto) {
        return opciones
                .stream()
                .map(obj -> new Opcion<>(obj, aTexto))
                .collect(Collectors.toList());
    }

    static <T> Opcion<T> opcionNull(String nombre) {
        return new Opcion<>(null, o -> nombre);
    }

    class Opcion<T> implements OpcionMenu<T> {

        private final T objeto;
        private final String mensaje;

        private Opcion(T objeto, Function<T,String> aTexto) {
            this.objeto = objeto;
            this.mensaje = aTexto.apply(objeto);
        }

        @Override
        public String nombre() {
            return mensaje;
        }

        @Override
        public T objeto() {
            return objeto;
        }
    }
}

package ifp.kikeverea.util;

import java.util.ArrayList;
import java.util.List;

public class Menu<T> {

    private static final int NUMERO_SALIDA = 0;

    private final String mensajeInicial;
    private final String prompt;
    private final List<OpcionMenu<T>> opciones;
    private final List<OpcionMenu<T>> opcionesIniciales;
    private final OpcionMenu<T> salida;

    private Menu(MenuBuilder<T> builder) {
        validarConstruccion(builder);

        List<OpcionMenu<T>> opciones = new ArrayList<>(builder.opciones);

        if (builder.salida != null)
            opciones.removeIf(opcion -> opcion.nombre().equals(builder.salida.nombre()));

        this.mensajeInicial = builder.mensajeInicial;
        this.prompt = builder.prompt;
        this.opciones = opciones;
        this.opcionesIniciales = List.copyOf(opciones); // lista inmutable, para evitar modificaciones accidentales
        this.salida = builder.salida;
    }

    private void validarConstruccion(MenuBuilder<T> builder) {
        if (builder.prompt == null || builder.prompt.isBlank())
            throw new IllegalArgumentException("Un 'prompt' es necesario para instanciar este Menu");

        int cantidadDeOpciones = builder.opciones.size();
        boolean tamanoValido = cantidadDeOpciones >= 2 || cantidadDeOpciones == 1 && builder.salida != null;

        if (!tamanoValido)
            throw new IllegalArgumentException("Al menos dos opciónes son necesarias para instanciar un Menu");
    }

    public String mostrar() {
        StringBuilder sb = new StringBuilder();

        if (mensajeInicial != null)
            sb.append(mensajeInicial).append("\n");

        for (int i = 0, numeroOpcion = 1; i < opciones.size(); i++, numeroOpcion++)
            appendOpcion(numeroOpcion, opciones.get(i).nombre(), sb);

        if (salida != null)
            appendOpcion(NUMERO_SALIDA, salida.nombre(), sb);

        sb.append(prompt);
        return sb.toString();
    }

    private void appendOpcion(int numeroOpcion, String nombreOpcion, StringBuilder sb) {
        sb.append(numeroOpcion).append("- ");
        sb.append(nombreOpcion);
        sb.append("\n");
    }

    public T getOpcion(int numeroOpcion) {
        OpcionMenu<T> opcion = salida != null && numeroOpcion == NUMERO_SALIDA ? salida : opciones.get(numeroOpcion - 1);
        return opcion.objeto();
    }

    public void inhabilitarOpcion(T opcion) {
        opciones.removeIf(o -> o.equals(opcion));
    }

    public void rehabilitarOpciones() {
        opciones.clear();
        opciones.addAll(opcionesIniciales);
    }

    public int count() {
        return opciones.size();
    }

    public int min() {
        return salida != null ? 0 : 1;
    }

    public int max() {
        return opciones.size();
    }

    public static MenuBuilder<String> menuSimple() {
        return new MenuBuilder<>();
    }

    public static <T> MenuBuilder<T> nuevoMenu(Class<T> c) {
        return new MenuBuilder<>();
    }
    
    public static class MenuBuilder<T> {
        private String mensajeInicial;
        private String prompt;
        private List<OpcionMenu<T>> opciones;
        private OpcionMenu<T> salida;
        
        public MenuBuilder<T> mensajeInicial(String mensajeInicial) {
            this.mensajeInicial = mensajeInicial;
            return this;
        }

        public MenuBuilder<T> prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public MenuBuilder<T> opciones(List<OpcionMenu<T>> opciones) {
            this.opciones = opciones;
            return this;
        }

        public MenuBuilder<T> salida(OpcionMenu<T> salida) {
            this.salida = salida;
            return this;
        }
        
        public Menu<T> build() {
            return new Menu<>(this);
        }
    }
}

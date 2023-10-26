package ifp.kikeverea.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private final String mensajeInicial;
    private final String prompt;
    private final List<OpcionMenu> opciones;
    private final List<OpcionMenu> opcionesAMostrar;

    public Menu(String prompt, OpcionMenu... opciones) {
        this(null, prompt, opciones);
    }
    public Menu(String mensajeInicial, String prompt, OpcionMenu... opciones) {
        if (opciones.length == 0)
            throw new IllegalArgumentException("Al menos un artículo es necesario para instanciar un Menu");

        this.mensajeInicial = mensajeInicial;
        this.prompt = prompt;
        this.opciones = Arrays.asList(opciones);
        this.opcionesAMostrar = new ArrayList<>(this.opciones);
    }

    public String mostrar(String... args) {
        StringBuilder sb = new StringBuilder();

        if (mensajeInicial != null)
            sb.append(mensajeInicial).append("\n");

        for (int indiceOpcion = 0; indiceOpcion < opcionesAMostrar.size(); indiceOpcion++) {
            OpcionMenu opcion = opcionesAMostrar.get(indiceOpcion);

            int numeroOpcion = indiceOpcionANumero(indiceOpcion);

            String display = opcion.mensaje(args);
            sb.append(numeroOpcion).append("- ");
            sb.append(display);
            sb.append("\n");
        }
        sb.append(prompt);
        return sb.toString();
    }

    private int numeroOpcionAIndice(int numeroOpcion) {
        // índice = numeroOpcion - 1, excepto la última opción '0' (opción de salida), que tiene índice 'length - 1'
        return numeroOpcion != 0 ? numeroOpcion - 1 : opcionesAMostrar.size() -1;
    }

    private int indiceOpcionANumero(int indiceOpcion) {
        // numero opcion = i + 1, excepto la última opción (opción de salida), a la que se le asigna el número '0'
        return indiceOpcion < opcionesAMostrar.size() -1 ? indiceOpcion + 1 : 0;
    }

    public OpcionMenu getOpcion(int numeroOpcion) {
        int indiceOpcion = numeroOpcionAIndice(numeroOpcion);
        return opcionesAMostrar.get(indiceOpcion);
    }

    public void inhabilitarOpcion(OpcionMenu opcion) {
        opcionesAMostrar.remove(opcion);
    }

    public void rehabilitarOpciones() {
        opcionesAMostrar.clear();
        opcionesAMostrar.addAll(opciones);
    }

    public int count() {
        return opcionesAMostrar.size();
    }
}

package ifp.kikeverea.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu {

    private final List<OpcionMenu> opciones;
    private final List<OpcionMenu> opcionesAMostrar;

    public Menu(ArticuloMenu... articulos) {
        if (articulos.length == 0)
            throw new IllegalArgumentException("Al menos un artículo es necesario para instanciar un Menu");

        this.opciones = Arrays.asList(opciones);
        this.opcionesAMostrar = new ArrayList<>(this.opciones);
    }

    public String mostrar(String... args) {
        StringBuilder sb = new StringBuilder();

        for (int indiceOpcion = 0; indiceOpcion < opcionesAMostrar.size(); indiceOpcion++) {
            OpcionMenu opcion = opcionesAMostrar.get(indiceOpcion);

            int numeroOpcion = indiceOpcionANumero(indiceOpcion);

            String display = opcion.mostrar(args);
            sb.append(numeroOpcion).append("- ");
            sb.append(display);
            sb.append("\n");
        }
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

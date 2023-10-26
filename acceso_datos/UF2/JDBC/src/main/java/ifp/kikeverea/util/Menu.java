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
        for (int i = 0; i < articulos.length; i++) {
            String display = articulos[i].mostrar(args);
            if (i < articulos.length -1) {
                sb.append(i+1).append("- ");
                sb.append(display);
                sb.append("\n");
            }
            else sb.append(display).append(": ");

        }
        return sb.toString();
    }
}

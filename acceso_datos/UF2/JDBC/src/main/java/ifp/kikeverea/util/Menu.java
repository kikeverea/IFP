package ifp.kikeverea.util;

public class Menu {

    private final ArticuloMenu[] articulos;

    public Menu(ArticuloMenu... articulos) {
        if (articulos.length == 0)
            throw new IllegalArgumentException("Al menos un artículo es necesario para instanciar un Menu");

        this.articulos = articulos;
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

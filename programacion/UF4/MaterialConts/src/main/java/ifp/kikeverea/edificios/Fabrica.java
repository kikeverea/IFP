package ifp.kikeverea.edificios;

public class Fabrica extends Edificio {

    public Fabrica(float anchura, float altura, float profundidad) {
        super(anchura, altura, profundidad, "Fábrica");
    }

    @Override
    public String funcionalidadEdificio() {
        return "Lugar donde se crean los productos";
    }
}

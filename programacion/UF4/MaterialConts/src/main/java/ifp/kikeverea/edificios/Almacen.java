package ifp.kikeverea.edificios;

public class Almacen extends Edificio {

    public Almacen(float anchura, float altura, float profundidad) {
        super(anchura, altura, profundidad, "Almacén");
    }

    @Override
    public String funcionalidadEdificio() {
        return "Lugar donde se guardan los productos";
    }
}

package ifp.kikeverea.edificios;

public class Oficina extends Edificio {

    public Oficina(float anchura, float altura, float profundidad) {
        super(anchura, altura, profundidad, "Oficina");
    }

    @Override
    public String funcionalidadEdificio() {
        return "Lugar donde se gestionan los productos";
    }
}

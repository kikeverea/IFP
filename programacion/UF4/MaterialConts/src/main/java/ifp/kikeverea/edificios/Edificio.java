package ifp.kikeverea.edificios;

public abstract class Edificio {

    public static final String LADO_ANCHURA = "ladoanchura";
    public static final String LADO_PROFUNDIDAD = "ladoprofundidad";

    protected float anchura;
    protected float altura;
    protected float profundidad;
    protected String tipo;

    public Edificio(float anchura, float altura, float profundidad, String tipo) {
        this.anchura = anchura;
        this.altura = altura;
        this.profundidad = profundidad;
        this.tipo = tipo;
    }

    public abstract String funcionalidadEdificio();

    public float costePintura(float precioPorMetro) {
        if (precioPorMetro < 0)
            return -1.0f;

        return anchura * altura * profundidad * precioPorMetro;
    }

    public float costePintura(String lado, float precioPorMetro) {
        if (precioPorMetro < 0)
            return -1.0f;

        return  lado.equalsIgnoreCase(LADO_ANCHURA)
                ? precioPorMetro * altura * anchura
                : precioPorMetro * altura * profundidad;
    }

    public String mostrarInfo() {
        return  "Tipo: " + tipo + " - " +
                "Funcionalidad: " + funcionalidadEdificio() + " - " +
                "Anchura: " + anchura + " - " +
                "Altura: " + altura + " - " +
                "Profundidad: " + profundidad;
    }

    public float getAnchura() {
        return anchura;
    }

    public void setAnchura(float anchura) {
        this.anchura = anchura;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

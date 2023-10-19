package ifp.kikeverea;

public class Montana {

    private int id;
    private String nombre;
    private int altura;
    private int primeraAscencion;
    private String region;
    private String pais;

    public Montana(String nombre, int altura, int primeraAscencion, String region, String pais) {
        this.nombre = nombre;
        this.altura = altura;
        this.primeraAscencion = primeraAscencion;
        this.region = region;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAltura() {
        return altura;
    }

    public int getPrimeraAscencion() {
        return primeraAscencion;
    }

    public String getRegion() {
        return region;
    }

    public String getPais() {
        return pais;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setPrimeraAscencion(int primeraAscencion) {
        this.primeraAscencion = primeraAscencion;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Montaña: " + nombre +
                ", altura: " + altura +
                ", primera ascención:" + primeraAscencion +
                ", región: " + region +
                ", país: " + pais;
    }
}

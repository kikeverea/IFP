package ifp.kikeverea.bd;

public enum ClausulaAtributo {
    PRIMARY_KEY("PRIMARY KEY"),
    AUTO_INCREMENT("AUTO_INCREMENT"),
    UNIQUE("UNIQUE"),
    NOT_NULL("NOT NULL");

    private final String nombre;

    ClausulaAtributo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

package ifp.kikeverea.bd;

public enum ClausulaAtributo {
    PRIMARY_KEY("PRIMARY KEY"),
    AUTO_INCREMENT("AUTO_INCREMENT"),
    UNIQUE("UNIQUE"),
    NOT_NULL("NOT NULL");

    private final String definicion;

    ClausulaAtributo(String definicion) {
        this.definicion = definicion;
    }

    @Override
    public String toString() {
        return definicion;
    }
}

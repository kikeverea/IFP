package ifp.kikeverea;

public enum RestriccionAtributo {
    PRIMARY_KEY("PRIMARY KEY"),
    UNIQUE("UNIQUE"),
    NOT_NULL("NOT NULL");

    private final String definicion;

    RestriccionAtributo(String definicion) {
        this.definicion = definicion;
    }

    @Override
    public String toString() {
        return definicion;
    }
}

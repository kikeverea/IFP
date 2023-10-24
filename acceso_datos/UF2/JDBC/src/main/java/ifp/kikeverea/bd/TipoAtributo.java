package ifp.kikeverea.bd;

import java.sql.Types;

public enum TipoAtributo {
    NUMERO("INTEGER", Types.INTEGER),
    TEXTO("VARCHAR(255)", Types.VARCHAR);

    private final String nombre;
    private final int tipoSql;

    TipoAtributo(String nombre, int tipoSql) {
        this.tipoSql = tipoSql;
        this.nombre = nombre;
    }

    public String nombre() {
        return toString();
    }

    public int getTipoSql() {
        return tipoSql;
    }

    public static TipoAtributo getTipo(String nombreSql) {
        nombreSql = nombreSql.replaceAll("\\s", "").toUpperCase();

        return switch (nombreSql) {
            case "INTEGER", "INT" -> NUMERO;
            case "VARCHAR" -> TEXTO;
            default -> throw new IllegalArgumentException("No da soporte a tipo " + nombreSql);
        };
    }

    @Override
    public String toString() {
        return nombre;
    }
}

package ifp.kikeverea;

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

    public String conLongitud(int longitud) {
        return this +"("+longitud+")";
    }

    @Override
    public String toString() {
        return nombre;
    }
}

package ifp.kikeverea;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Atributo {

    private final String nombre;
    private final TipoAtributo tipo;
    private final List<RestriccionAtributo> restricciones;
    private final boolean esPrimaryKey;

    private Atributo(AtributoBuilder builder) {
        this.nombre = builder.nombre;
        this.tipo = builder.tipo;
        this.restricciones = builder.restricciones;
        this.esPrimaryKey = builder.esPrimaryKey;
    }

    public String definicion() {
        String base = nombre + " " + tipo.nombre();

        if (restricciones != null) {
            base = base + " " + restricciones
                    .stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(" "));
        }

        return base;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoAtributo getTipo() {
        return tipo;
    }

    public boolean esPrimaryKey() {
        return esPrimaryKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atributo atributo = (Atributo) o;
        return  Objects.equals(nombre, atributo.nombre) &&
                tipo == atributo.tipo &&
                Objects.equals(restricciones, atributo.restricciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, restricciones);
    }

    @Override
    public String toString() {
        return "Atributo{" +
                ", nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                ", restricciones=" + restricciones +
                '}';
    }

    public static AtributoBuilder nuevoAtributo(String nombre) {
        return new AtributoBuilder(nombre);
    }

    public static final class AtributoBuilder {
        private final String nombre;
        private TipoAtributo tipo;
        private List<RestriccionAtributo> restricciones;
        private boolean esPrimaryKey = false;

        private AtributoBuilder(String nombre) {
            if (nombre == null || nombre.strip().equals(""))
                throw new IllegalArgumentException("El nombre del atributo no puede estar vacío");

            this.nombre = nombre;
        }

        public Atributo deTipo(TipoAtributo tipo) {
            this.tipo = tipo;
            return new Atributo(this);
        }

        public Atributo deTipo(TipoAtributo tipo, RestriccionAtributo... restricciones) {
            this.tipo = tipo;
            this.restricciones = Arrays.asList(restricciones);
            this.esPrimaryKey = this.restricciones.contains(RestriccionAtributo.PRIMARY_KEY);
            return new Atributo(this);
        }
    }
}

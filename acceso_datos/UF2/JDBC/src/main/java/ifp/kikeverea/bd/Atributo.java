package ifp.kikeverea.bd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Atributo {

    private final String nombre;
    private final TipoAtributo tipo;
    private final List<RestriccionAtributo> restricciones;
    private final boolean esClavePrimaria;

    private Atributo(AtributoBuilder builder) {
        this.nombre = builder.nombre;
        this.tipo = builder.tipo;
        this.esClavePrimaria = builder.esClavePrimaria;
        this.restricciones = builder.restricciones != null
                ? validarRestricciones(builder.restricciones)
                : null;
    }

    private List<RestriccionAtributo> validarRestricciones(List<RestriccionAtributo> restricciones) {
        restricciones = new ArrayList<>(restricciones);

        if (esClavePrimaria) {
            if (!restricciones.contains(RestriccionAtributo.AUTO_INCREMENT))
                restricciones.add(RestriccionAtributo.AUTO_INCREMENT);

            if (!restricciones.contains(RestriccionAtributo.NOT_NULL))
                restricciones.add(RestriccionAtributo.NOT_NULL);
        }

        return restricciones;
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

    public boolean esClavePrimaria() {
        return esClavePrimaria;
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
                "nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                ", restricciones=" + restricciones +
                ", esClavePrimaria=" + esClavePrimaria +
                '}';
    }

    public static AtributoBuilder nuevoAtributo(String nombre) {
        return new AtributoBuilder(nombre);
    }

    public static final class AtributoBuilder {
        private final String nombre;
        private TipoAtributo tipo;
        private List<RestriccionAtributo> restricciones;
        private boolean esClavePrimaria = false;

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
            this.esClavePrimaria = this.restricciones.contains(RestriccionAtributo.PRIMARY_KEY);
            return new Atributo(this);
        }
    }
}

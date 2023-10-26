package ifp.kikeverea.bd;

import java.util.*;
import java.util.stream.Collectors;

public class Atributo {

    private final String nombre;
    private final TipoAtributo tipo;
    private final List<ClausulaAtributo> clausulas;
    private final boolean esClavePrimaria;

    private Atributo(AtributoBuilder builder) {
        this.nombre = builder.nombre;
        this.tipo = builder.tipo;
        this.esClavePrimaria = builder.esClavePrimaria;
        this.clausulas = builder.clausulas != null
                ? validarClausulas(builder.clausulas)
                : null;
    }

    private List<ClausulaAtributo> validarClausulas(List<ClausulaAtributo> clausulas) {
        clausulas = new ArrayList<>(clausulas);


        if (esClavePrimaria) {
            if (tipo == TipoAtributo.NUMERO && !clausulas.contains(ClausulaAtributo.AUTO_INCREMENT))
                clausulas.add(ClausulaAtributo.AUTO_INCREMENT);

            if (!clausulas.contains(ClausulaAtributo.NOT_NULL))
                clausulas.add(ClausulaAtributo.NOT_NULL);
        }

        return clausulas;
    }

    public String definicion() {
        String base = nombre + " " + tipo.nombre();

        if (clausulas != null) {
            base = base + " " + clausulas
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


        return nombre.equals(atributo.nombre) && tipo == atributo.tipo && Objects.equals(clausulas, atributo.clausulas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, tipo, clausulas);
    }

    @Override
    public String toString() {
        return "Atributo{" +
                "nombre='" + nombre + '\'' +
                ", tipo=" + tipo +
                ", clausulas=" + clausulas +
                ", esClavePrimaria=" + esClavePrimaria +
                '}';
    }

    public static AtributoBuilder nuevoAtributo(String nombre) {
        return new AtributoBuilder(nombre);
    }

    public static final class AtributoBuilder {
        private final String nombre;
        private TipoAtributo tipo;
        private List<ClausulaAtributo> clausulas;
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

        public Atributo deTipo(TipoAtributo tipo, ClausulaAtributo... clausulas) {
            this.tipo = tipo;
            this.clausulas = Arrays.asList(clausulas);
            this.esClavePrimaria = this.clausulas.contains(ClausulaAtributo.PRIMARY_KEY);
            return new Atributo(this);
        }
    }
}

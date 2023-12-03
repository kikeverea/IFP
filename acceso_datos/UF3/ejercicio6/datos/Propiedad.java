package ifp.kikeverea.datos;

import java.util.ArrayList;
import java.util.List;

public interface Propiedad {

    String getNombre();
    String getValor();
    List<Propiedad> getPropiedades();
    List<Atributo> getAtributos();

    static Propiedad simple(String nombre, String valor) {
        return new PropiedadSimple(nombre, valor);
    }

    class PropiedadSimple implements Propiedad {
        private final String nombre;
        private final String valor;
        private final List<Atributo> atributos = new ArrayList<>();

        public PropiedadSimple(String nombre, String valor) {
            this.nombre = nombre;
            this.valor = valor;
        }

        /**
         * Da acceso al nombre de esta instancia de Propiedad
         * @return el nombre de la Propiedad
         */
        @Override
        public String getNombre() {
            return nombre;
        }

        /**
         * Da acceso al valor de esta instancia de Propiedad
         * @return el valor de la Propiedad
         */
        @Override
        public String getValor() {
            return valor;
        }

        /**
         * Da acceso a las propiedades hijas de esta instancia de Propiedad.
         * Al ser una Propiedad simple, regresa una lista vacía
         * @return Una lista vacía
         */
        @Override
        public List<Propiedad> getPropiedades() {
            return List.of();
        }

        /**
         * Da acceso a los atributos que describen esta instancia de Propiedad
         * @return Una lista con todos los atributos de esta Propiedad
         */
        @Override
        public List<Atributo> getAtributos() {
            return atributos;
        }
    }
}

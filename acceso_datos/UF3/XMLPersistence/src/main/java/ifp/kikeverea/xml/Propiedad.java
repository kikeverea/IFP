package ifp.kikeverea.xml;

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

        @Override
        public String getNombre() {
            return nombre;
        }

        @Override
        public String getValor() {
            return valor;
        }

        @Override
        public List<Propiedad> getPropiedades() {
            return List.of();
        }

        @Override
        public List<Atributo> getAtributos() {
            return atributos;
        }
    }
}

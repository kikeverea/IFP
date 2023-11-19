package ifp.kikeverea.persona;

import ifp.kikeverea.xml.Atributo;
import ifp.kikeverea.xml.Propiedad;
import ifp.kikeverea.xml.PropiedadCompuesta;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Persona extends PropiedadCompuesta {

    private final Propiedad nombre;
    private final Propiedad apellidos;
    private final Propiedad nif;
    private final Propiedad direccion;
    private final Propiedad nacionalidad;
    private final Propiedad infoDeContacto;

    private final Atributo mayorDeEdad;
    private final Atributo extranjero;

    public Persona(Builder builder) {
        super("persona");

        validarConstruccion(builder);

        anadirAtributo(new Atributo("mayor_edad", builder.mayorDeEdad));
        anadirAtributo(new Atributo("extranjero", builder.extranjero));

        nombre = Propiedad.simple("nombre", builder.nombre);
        nif = Propiedad.simple("nif", builder.nif);
        nacionalidad = Propiedad.simple("nacionalidad", builder.nacionalidad);
        mayorDeEdad = new Atributo("mayor_edad", builder.mayorDeEdad ? "SI" : "NO");
        extranjero = new Atributo("extranjero", builder.extranjero ? "SI" : "NO");

        apellidos = new Apellidos(builder);
        direccion = new Direccion(builder);
        infoDeContacto = new InfoDeContacto(builder);
    }

    private void validarConstruccion(Builder builder) {
        Set<String> fallos = new HashSet<>();

        if (builder.nombre == null)
            fallos.add("nombre");
        if (builder.apellidos == null)
            fallos.add("apellidos");
        if (builder.nif == null)
            fallos.add("nif");
        if (builder.direccion == null)
            fallos.add("direccion");
        if (builder.provincia == null)
            fallos.add("provincia");
        if (builder.ciudad == null)
            fallos.add("ciudad");
        if (builder.codigoPostal == null)
            fallos.add("codigoPostal");
        if (builder.nacionalidad == null)
            fallos.add("nacionalidad");
        if (builder.telefono == null)
            fallos.add("telefono");
        if (builder.email == null)
            fallos.add("email");

        if (!fallos.isEmpty())
            throw new IllegalArgumentException(
                    "No se ha creado la instancia de persona, faltan propiedades: " + String.join(",", fallos));
    }

    @Override
    public List<Propiedad> getPropiedades() {
        return List.of(nombre, apellidos, nif, direccion, nacionalidad, infoDeContacto);
    }

    @Override
    public List<Atributo> getAtributos() {
        return List.of(mayorDeEdad, extranjero);
    }

    public static Builder conNombre(String nombre) {
        return new Builder(nombre);
    }

    public static class Builder {
         final String nombre;
         String[] apellidos;
         String nif;
         String direccion;
         String ciudad;
         String provincia;
         String codigoPostal;
         String nacionalidad;
         boolean mayorDeEdad;
         boolean extranjero;
         String telefono;
         String email;

        public Builder(String nombre) {
            this.nombre = nombre;
        }

        public Builder appellidos(String... apellidos) {
            this.apellidos = apellidos;
            return this;
        }

        public Builder nif(String nif) {
            this.nif = nif;
            return this;
        }

        public Builder direccion(String direccion) {
            this.direccion = direccion;
            return this;
        }

        public Builder ciudad(String ciudad) {
            this.ciudad= ciudad;
            return this;
        }

        public Builder provincia(String provincia) {
            this.provincia = provincia;
            return this;
        }

        public Builder codigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
            return this;
        }

        public Builder nacionalidad(String nacionalidad) {
            this.nacionalidad = nacionalidad;
            return this;
        }

        public Builder mayorDeEdad(boolean mayorDeEdad) {
            this.mayorDeEdad = mayorDeEdad;
            return this;
        }

        public Builder extranjero(boolean extranjero) {
            this.extranjero = extranjero;
            return this;
        }

        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Persona construir() {
            return new Persona(this);
        }
    }
}

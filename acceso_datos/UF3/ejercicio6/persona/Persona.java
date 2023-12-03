package ifp.kikeverea.persona;

import ifp.kikeverea.datos.Atributo;
import ifp.kikeverea.datos.Propiedad;
import ifp.kikeverea.datos.PropiedadCompuesta;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Persona extends PropiedadCompuesta {

    private final Propiedad nombre;
    private final Propiedad apellidos;
    private final Propiedad fechaNacimiento;
    private final Propiedad nif;
    private final Propiedad direccion;
    private final Propiedad nacionalidad;
    private final Propiedad infoDeContacto;

    public Persona(Builder builder) {
        super("persona");

        validarConstruccion(builder);

        String fechaDeNacimiento = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(builder.fechaNacimiento);
        long anosEntreFechaDeNacimientoYHoy = ChronoUnit.YEARS.between(builder.fechaNacimiento, LocalDate.now());

        boolean mayorDeEdad = anosEntreFechaDeNacimientoYHoy >= 18;
        boolean extranjero = !builder.nacionalidad.equalsIgnoreCase("España");

        anadirAtributo(new Atributo("mayor_edad", mayorDeEdad));
        anadirAtributo(new Atributo("extranjero", extranjero));

        nombre = Propiedad.simple("nombre", builder.nombre);
        nif = Propiedad.simple("nif", builder.nif);
        nacionalidad = Propiedad.simple("nacionalidad", builder.nacionalidad);
        fechaNacimiento = Propiedad.simple("fecha_nacimiento", fechaDeNacimiento);

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

    /**
     * Da acceso a las propiedades hijas de esta instancia de Propiedad
     * @return Una lista con las Propiedades hijas de esta instancia de Propiedad
     */
    @Override
    public List<Propiedad> getPropiedades() {
        return List.of(nombre, apellidos, nif, fechaNacimiento, nacionalidad, direccion, infoDeContacto);
    }

    public static Builder conNombre(String nombre) {
        return new Builder(nombre);
    }

    public static class Builder {
        String nombre, nif, nacionalidad;
        String direccion, ciudad, provincia, codigoPostal;
        String telefono, email;
        String[] apellidos;
        LocalDate fechaNacimiento;

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
        public Builder fechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }
        public Builder nacionalidad(String nacionalidad) {
            this.nacionalidad = nacionalidad;
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

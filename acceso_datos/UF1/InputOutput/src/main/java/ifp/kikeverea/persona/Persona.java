package ifp.kikeverea.persona;
import java.io.Serializable;
import java.lang.String;

public class Persona implements Serializable {
    
    private String nombre;
    private String apellido;
    private String ciudad;
    private String nacionalidad;
    private int edad;

    /**
     * Constructor por defecto
     */
    public Persona() {}

    /**
     * Constructor con el parámetro "nombre"
     * @param nombre el nombre de la persona
     */
    public Persona(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor con los parámetros: nombre, apellido, ciudad, nacionalidad, edad
     * @param nombre el nombre de la persona
     * @param apellido el apellido de la persona
     * @param ciudad la ciudad de nacimiento de la persona
     * @param nacionalidad la nacionalidad de la persona
     * @param edad la edad en años de la persona
     */
    public Persona(String nombre, String apellido, String ciudad, String nacionalidad, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.ciudad = ciudad;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Compara un Objeto con esta instancia de Persona
     * @param o El objeto a comparar
     * @return 'true' si el Objeto es igual o es una referencia a esta misma instancia de Persona, de lo contrario 'false'
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return  edad == persona.edad && nombre.equals(persona.nombre) && apellido.equals(persona.apellido) &&
                ciudad.equals(persona.ciudad) && nacionalidad.equals(persona.nacionalidad);
    }

    /**
     * Convierte esta Persona en String
     * @return Una String con los atributos de esta Persona
     */
    @Override
    public String toString() {
        return "Nombre: '" + nombre + "', " +
                "Apellido: '" + apellido + "', " +
                "Ciudad: '" + ciudad + "', " +
                "Nacionalidad: '" + nacionalidad + "', " +
                "Edad: '" + edad + "'";
    }

    /**
     * Convierte una String en una Persona. La operación es el opuesto simétrico a {@link #toString() toString}
     * @param raw La String con la info para crear una nueva persona. Se asume que su formato viene dado por {@link #toString() toString}
     * @return Una nueva persona con los atributos encontrados en la String 'raw'
     */
    public static Persona fromString(String raw) {
        String[] attributos = raw.split(", ");
        return new Persona(
                extraerAtributo("Nombre", attributos[0]),
                extraerAtributo("Apellido", attributos[1]),
                extraerAtributo("Ciudad", attributos[2]),
                extraerAtributo("Nacionalidad", attributos[3]),
                Integer.parseInt(extraerAtributo("Edad", attributos[4]))
        );
    }

    private static String extraerAtributo(String atributo, String raw) {
        // longitud del atributo + los 3 caracteres siguientes (: ')
        int longitudAtributo = atributo.length() + 3;

        return raw.substring(longitudAtributo, raw.length() -1);
    }
}
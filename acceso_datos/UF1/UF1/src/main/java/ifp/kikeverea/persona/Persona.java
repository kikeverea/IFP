package ifp.kikeverea.persona;
import java.lang.String;

public class Persona {
    
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
     *
     * @return Una cadena con los atributos de esta Persona. Con formato para impresión
     */
    @Override
    public String toString() {
        return "Nombre: '" + nombre + '\'' +
                ", Apellido: '" + apellido + '\'' +
                ", Ciudad: '" + ciudad + '\'' +
                ", Nacionalidad: '" + nacionalidad + '\'' +
                ", Edad: " + edad;
    }
}

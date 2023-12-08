package ifp.kikeverea;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class Paciente implements Serializable {

    static final long serialVersionUID = 1L;

    private String nombre;
    private String apellido;
    private String telefono;
    private int edad;
    private int ultimoHierro;
    private int ultimaUrea;

    private final PropertyChangeSupport changeSupport;

    public Paciente() {
        changeSupport = new PropertyChangeSupport(this);
    }

    public Paciente(
            String nombre, String apellido, int edad, String telefono, int ultimoHierro, int ultimaUrea)
    {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.edad = edad;
        this.ultimoHierro = ultimoHierro;
        this.ultimaUrea = ultimaUrea;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.changeSupport.addPropertyChangeListener(listener);
    }

    // Setters
    public void setUltimoHierro(int valor) {
        int valorAntiguo = this.ultimoHierro;
        this.ultimoHierro = valor;

        // genera un evento de cambio en el valor de ultimoHierro
        changeSupport.firePropertyChange("ultimoHierro", valorAntiguo, valor);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setUltimaUrea(int valor) {
        this.ultimaUrea = valor;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getEdad() {
        return edad;
    }

    public int getUltimoHierro() {
        return ultimoHierro;
    }

    public int getUltimaUrea() {
        return ultimaUrea;
    }
}

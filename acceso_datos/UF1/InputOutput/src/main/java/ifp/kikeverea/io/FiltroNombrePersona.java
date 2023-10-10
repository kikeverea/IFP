package ifp.kikeverea.io;

import ifp.kikeverea.persona.Persona;

/**
 * Clase que prueba si una persona tiene un determinado nombre
 */
public class FiltroNombrePersona implements FiltroLectura<Persona> {

    private final String nombre;

    public FiltroNombrePersona(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Indica el nombre de la Persona y el nombre con que fue instanciada esta clase son iguales
     * @param persona La Persona cuyo nombre se va a revisar
     * @return true si los nombres coincides, de lo contrario false
     */
    @Override
    public boolean pasaFiltro(Persona persona) {
        return persona.getNombre().equals(nombre);
    }
}

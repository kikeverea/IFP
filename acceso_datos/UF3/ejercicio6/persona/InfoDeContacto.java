package ifp.kikeverea.persona;

import ifp.kikeverea.datos.Propiedad;
import ifp.kikeverea.datos.PropiedadCompuesta;

import java.util.List;

public class InfoDeContacto extends PropiedadCompuesta {

    private final Propiedad telefono;
    private final Propiedad email;

    InfoDeContacto(Persona.Builder builder) {
        super("info_contacto");
        telefono = Propiedad.simple("telefono", builder.telefono);
        email = Propiedad.simple("email", builder.email);
    }

    /**
     * Da acceso a las propiedades hijas de esta instancia de Propiedad
     * @return Una lista con las Propiedades hijas de esta instancia de Propiedad
     */
    @Override
    public List<Propiedad> getPropiedades() {
        return List.of(telefono, email);
    }
}

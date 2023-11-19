package ifp.kikeverea.persona;

import ifp.kikeverea.xml.Propiedad;
import ifp.kikeverea.xml.PropiedadCompuesta;

import java.util.List;

public class InfoDeContacto extends PropiedadCompuesta {

    private final Propiedad telefono;
    private final Propiedad email;

    InfoDeContacto(Persona.Builder builder) {
        super("info_contacto");
        telefono = Propiedad.simple("telefono", builder.telefono);
        email = Propiedad.simple("email", builder.email);
    }

    @Override
    public List<Propiedad> getPropiedades() {
        return List.of(telefono, email);
    }
}

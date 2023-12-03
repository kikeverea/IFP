package ifp.kikeverea.persona;

import ifp.kikeverea.datos.Propiedad;
import ifp.kikeverea.datos.PropiedadCompuesta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Apellidos extends PropiedadCompuesta {

    private final List<Propiedad> apellidos;

    Apellidos(Persona.Builder builder) {
        super("apellidos");
        apellidos = Arrays
                .stream(builder.apellidos)
                .map(apellido -> Propiedad.simple("apellido", apellido))
                .collect(Collectors.toList());
    }

    /**
     * Da acceso a las propiedades hijas de esta instancia de Propiedad
     * @return Una lista con las Propiedades hijas de esta instancia de Propiedad
     */
    @Override
    public List<Propiedad> getPropiedades() {
        return new ArrayList<>(apellidos);
    }
}

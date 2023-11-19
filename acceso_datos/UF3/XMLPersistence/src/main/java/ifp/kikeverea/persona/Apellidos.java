package ifp.kikeverea.persona;

import ifp.kikeverea.xml.Propiedad;
import ifp.kikeverea.xml.PropiedadCompuesta;

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

    @Override
    public List<Propiedad> getPropiedades() {
        return new ArrayList<>(apellidos);
    }
}

package ifp.kikeverea.persona;

import ifp.kikeverea.xml.Propiedad;
import ifp.kikeverea.xml.PropiedadCompuesta;

import java.util.List;

class Direccion extends PropiedadCompuesta {

    private final Propiedad direccion;
    private final Propiedad ciudad;
    private final Propiedad provincia;
    private final Propiedad codigoPostal;

    Direccion(Persona.Builder builder) {
        super("direccion");
        this.direccion = Propiedad.simple("direccion", builder.direccion);
        this.ciudad = Propiedad.simple("ciudad", builder.ciudad);
        this.provincia = Propiedad.simple("provincia", builder.provincia);
        this.codigoPostal = Propiedad.simple("codigo_postal", builder.codigoPostal);
    }

    @Override
    public List<Propiedad> getPropiedades() {
        return List.of(direccion, ciudad, provincia, codigoPostal);
    }

}

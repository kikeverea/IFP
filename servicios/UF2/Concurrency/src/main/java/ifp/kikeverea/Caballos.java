package ifp.kikeverea;

import java.util.List;

public class Caballos {

    private final List<Caballo> caballos;
    private final int longitudMaximaAvatares;

    public Caballos(List<Caballo> caballos) {
        this.caballos = caballos;
        this.longitudMaximaAvatares = this.caballos
                .stream()
                .mapToInt(caballo -> caballo.avatar().longitud())
                .max()
                .orElseThrow();
    }

    public List<Caballo> lista() {
        return caballos;
    }

    public int count() {
        return caballos.size();
    }

    public int longitudMaximaAvatares() {
        return longitudMaximaAvatares;
    }
}

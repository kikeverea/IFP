package ifp.kikeverea.edificios;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OficinaTest {

    @Test
    void callFuncionalidadEdificio_correctValueReturned() {
        Oficina o = new Oficina(0, 0, 0);
        Assertions.assertEquals("Lugar donde se gestionan los productos", o.funcionalidadEdificio());
    }
}

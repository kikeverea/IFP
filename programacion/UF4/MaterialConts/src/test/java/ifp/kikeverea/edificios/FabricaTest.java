package ifp.kikeverea.edificios;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FabricaTest {

    @Test
    void callFuncionalidadEdificio_correctValueReturned() {
        Fabrica f = new Fabrica(0, 0, 0);
        Assertions.assertEquals("Lugar donde se crean los productos", f.funcionalidadEdificio());
    }
}

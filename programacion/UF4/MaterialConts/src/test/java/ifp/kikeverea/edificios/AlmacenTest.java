package ifp.kikeverea.edificios;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AlmacenTest {

    @Test
    void callFuncionalidadEdificio_correctValueReturned() {
        Almacen a = new Almacen(0, 0, 0);
        Assertions.assertEquals("Lugar donde se guardan los productos", a.funcionalidadEdificio());
    }
}

package ifp.kikeverea.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MenuTest {

    @Test
    void muestraTodasLasOpcionesEnElOrdenQueFueronAgregadas() {
        String expected = """
                Menu simple:
                1- mock1
                2- mock2
                3- mock3
                0- mock4
                Opcion:\040""";

        Assertions.assertEquals(expected, menuSimple().mostrar());
    }

    @Test
    void muestraTodasLasOpcionesMenosLasInhabilitados() {
        Menu<MockOpcion> menu = menuSimple();
        menu.inhabilitarOpcion(menu.getOpcion(2));

        String expected = """
                Menu simple:
                1- mock1
                2- mock3
                0- mock4
                Opcion:\040""";

        Assertions.assertEquals(expected, menu.mostrar());
    }

    private Menu<MockOpcion> menuSimple() {
        return Menu.nuevoMenu(MockOpcion.class)
                .mensajeInicial("Menu simple:")
                .prompt("Opcion: ")
                .opciones(OpcionMenu.opciones(
                        MockOpcion::getNombre,
                        MockOpcion.sinArgs(1),
                        MockOpcion.sinArgs(2),
                        MockOpcion.sinArgs(3),
                        MockOpcion.sinArgs(4)))
                .build();
    }

    private static abstract class MockOpcion {

        private final String nombre;

        private MockOpcion(int mock) {
            this.nombre = "mock"+mock;
        }

        String getNombre() {
            return nombre;
        }

        public static MockOpcion sinArgs(int mock) {
            return new Simple(mock);
        }
    }

    private static class Simple extends MockOpcion {

        private Simple(int mock) {
            super(mock);
        }

        public String mensaje() {
            return getNombre();
        }
    }
}

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
    void muestraTodasLasOpcionesConSusArgumentos() {
        String expected = """
                Menu con argumentos:
                1- mock1 arg1 arg2
                2- mock2 arg1 arg2
                3- mock3 arg1 arg2
                0- mock4 arg1 arg2
                Opcion:\040""";

        Assertions.assertEquals(expected, menuConArgs().mostrar("arg1", "arg2"));
    }

    @Test
    void muestraTodasLasOpcionesMenosLasInhabilitados() {
        Menu menu = menuSimple();
        menu.inhabilitarOpcion(menu.getOpcion(2));

        String expected = """
                Menu simple:
                1- mock1
                2- mock3
                0- mock4
                Opcion:\040""";

        Assertions.assertEquals(expected, menu.mostrar());
    }

    private Menu menuSimple() {
        return new Menu(
                "Menu simple:",
                "Opcion: ",
                MockOpcion.sinArgs(1),
                MockOpcion.sinArgs(2),
                MockOpcion.sinArgs(3),
                MockOpcion.sinArgs(4));
    }

    private Menu menuConArgs() {
        return new Menu(
                "Menu con argumentos:",
                "Opcion: ",
                MockOpcion.conArgs(1),
                MockOpcion.conArgs(2),
                MockOpcion.conArgs(3),
                MockOpcion.conArgs(4));
    }


    private static abstract class MockOpcion implements OpcionMenu {

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

        public static MockOpcion conArgs(int mock) {
            return new ConArgs(mock);
        }
    }

    private static class Simple extends MockOpcion {

        private Simple(int mock) {
            super(mock);
        }

        @Override
        public String mensaje(String... args) {
            return getNombre();
        }
    }

    private static class ConArgs extends MockOpcion {

        private ConArgs(int mock) {
            super(mock);
        }

        @Override
        public String mensaje(String... args) {
            return getNombre() + " " + args[0] + " " + args[1];
        }
    }
}

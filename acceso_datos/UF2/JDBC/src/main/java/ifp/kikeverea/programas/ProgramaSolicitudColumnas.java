package ifp.kikeverea.programas;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.ClausulaAtributo;
import ifp.kikeverea.bd.TipoAtributo;
import ifp.kikeverea.util.OpcionMenu;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.Menu;
import ifp.kikeverea.util.Presentador;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramaSolicitudColumnas {

    private enum Accion implements OpcionMenu {
        ANADIR("Añadir"),
        TERMINAR("Terminar");

        private final String mensaje;
        Accion(String mensaje) {
            this.mensaje = mensaje;
        }

        @Override
        public String mensaje(String... args) {
            return mensaje;
        }
    }

    public static void main(String[] args) {
        InputUsuario input = new InputUsuario(new Scanner(System.in));
        solicitarColumnas(input);
    }

    private enum OpcionTipos implements OpcionMenu {
        NUMERO("Número"),
        TEXTO("Texto"),
        CANCELAR("Cancelar");

        private final String mensaje;
        OpcionTipos(String mensaje) {
            this.mensaje = mensaje;
        }

        @Override
        public String mensaje(String... args) {
            return mensaje;
        }
    }

    public static Menu MENU_ACCIONES = new Menu("****** Añadir atributos ******", "Acción: ", Accion.values());
    public static Menu MENU_TIPOS = new Menu("Tipo de argumento", "Tipo: ", OpcionTipos.values());

    public static List<Atributo> solicitarColumnas(InputUsuario input) {
        List<Atributo> atributos = new ArrayList<>();

        do {
            Accion accion = (Accion) input.solicitarOpcionMenu(MENU_ACCIONES);

            if (accion == Accion.TERMINAR)
                return terminar(atributos);

            Atributo atributo = solicitarAtributo(input);

            if (atributo != null)
                atributos.add(atributo);
        }
        while (true);
    }

    private static List<Atributo> terminar(List<Atributo> atributos) {
        if (atributos.isEmpty()) {
            Programa.imprimirResultado("No se han añadido atributos");
        }
        else {
            String mensaje = Presentador.separadoPorComas(atributos, atributo -> "'"+atributo.getNombre()+"'");
            Programa.imprimirMensaje("Atributos añadidos: " + mensaje);
        }
        return atributos;
    }

    private static <T> T cancelar() {
        Programa.imprimirResultado("Operación cancelada");
        return null; // regresa null por diseño
    }

    private static Atributo solicitarAtributo(InputUsuario input) {
        String nombre = input.solicitarSoloTexto("Nombre del atributo: ");

        if (nombre.isBlank())
            return cancelar();

        OpcionTipos opcionTipo = (OpcionTipos) input.solicitarOpcionMenu(MENU_TIPOS);
        if (opcionTipo == OpcionTipos.CANCELAR)
            return cancelar();

        List<ClausulaAtributo> clausulas = ProgramaSolicitudClausulasColumnas.solicitarClausulas(input, opcionTipo == OpcionTipos.TEXTO);
        if (clausulas == null)
            return cancelar();

        TipoAtributo tipoAtributo = opcionTipo == OpcionTipos.NUMERO ? TipoAtributo.NUMERO : TipoAtributo.TEXTO;

        Atributo atributo = Atributo
                .nuevoAtributo(nombre)
                .deTipo(tipoAtributo, clausulas.toArray(new ClausulaAtributo[0]));

       Programa.imprimirResultado("Atributo creado: '" +
               atributo.getNombre() + "', " +
               opcionTipo.mensaje + " (" +
               ProgramaSolicitudClausulasColumnas.mostrarResultado() + ")");

        return atributo;
    }
}

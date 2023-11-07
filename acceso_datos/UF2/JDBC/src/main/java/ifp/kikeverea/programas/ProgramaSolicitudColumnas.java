package ifp.kikeverea.programas;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.ClausulaAtributo;
import ifp.kikeverea.bd.TipoAtributo;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.Menu;
import ifp.kikeverea.util.OpcionMenu;
import ifp.kikeverea.util.Presentador;

import java.util.ArrayList;
import java.util.List;

public class ProgramaSolicitudColumnas {

    private static final String TIPO_NUMERO = "Número";
    private static final String TIPO_TEXTO = "Texto";
    private static final String CANCELAR = "Cancelar";

    private static final String ACCION_ANADIR = "Añadir";
    private static final String ACCION_TERMINAR = "Terminar";

    public static Menu<String> MENU_ACCIONES = Menu.menuSimple()
            .prompt("Acción: ")
            .opciones(List.of(OpcionMenu.opcionSimple(ACCION_ANADIR)))
            .salida(OpcionMenu.opcionSimple(ACCION_TERMINAR))
            .build();

    public static Menu<String> MENU_TIPOS = Menu.menuSimple()
            .mensajeInicial("Tipo de argumento")
            .prompt("Tipo: ")
            .opciones(OpcionMenu.opcionesSimples(TIPO_NUMERO, TIPO_TEXTO))
            .salida(OpcionMenu.opcionSimple(CANCELAR))
            .build();

    public static List<Atributo> solicitarColumnas(InputUsuario input) {
        Programa.imprimirMensaje("Añadir atributos");
        List<Atributo> atributos = new ArrayList<>();

        do {
            String accion = input.solicitarOpcionMenu(MENU_ACCIONES);

            if (accion.equals(ACCION_TERMINAR))
                return terminar(atributos);

            Atributo atributo = solicitarAtributo(input);

            if (atributo != null)
                atributos.add(atributo);
        }
        while (true);
    }

    private static List<Atributo> terminar(List<Atributo> atributos) {
        if (atributos.isEmpty()) {
            Programa.imprimirMensaje("No se han añadido atributos");
        }
        else {
            String mensaje = Presentador.separadoPorComas(atributos, atributo -> "'"+atributo.getNombre()+"'");
            Programa.imprimirMensaje("Atributos añadidos: " + mensaje);
        }
        return atributos;
    }

    private static Atributo solicitarAtributo(InputUsuario input) {
        String nombre = input.solicitarSoloTexto("Nombre del atributo: ");

        if (nombre.isBlank())
            return Programa.operacionCancelada();

        String opcionTipo = input.solicitarOpcionMenu(MENU_TIPOS);

        if (opcionTipo.equals(CANCELAR))
            return Programa.operacionCancelada();

        List<ClausulaAtributo> clausulas =
                ProgramaSolicitudClausulasColumnas.solicitarClausulas(input, opcionTipo.equals(TIPO_TEXTO));

        if (clausulas == null)
            return Programa.operacionCancelada();

        TipoAtributo tipoAtributo = opcionTipo.equals(TIPO_NUMERO) ? TipoAtributo.NUMERO : TipoAtributo.TEXTO;

        Atributo atributo = Atributo
                .nuevoAtributo(nombre)
                .deTipo(tipoAtributo, clausulas.toArray(new ClausulaAtributo[0]));

        Programa.imprimirMensaje("Atributo creado: '" +
               atributo.getNombre() + "', " +
               opcionTipo + " (" +
               ProgramaSolicitudClausulasColumnas.mostrarResultado() + ")");

        return atributo;
    }
}

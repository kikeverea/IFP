package ifp.kikeverea;

import ifp.kikeverea.bd.ClausulaAtributo;
import ifp.kikeverea.util.InputUsuario;
import ifp.kikeverea.util.Menu;
import ifp.kikeverea.util.OpcionMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramaSolicitudClausulasColumnas {

    private enum OpcionClausulas implements OpcionMenu {
        ID("Id", "este atributo será utilizado para identificar los valores de esta tabla"),
        UNICO("Único", "los valores de este atributo deberán ser únicos"),
        AUTO_INCREMENT("Autoincremental", "los valores de este atributo autoincrementan por cada instancia de la entidad"),
        NOT_NULL("No nulo", "este atributo siempre debe tener un valor"),
        CANCELAR("(Cancelar)", "cancela la operación"),
        TERMINAR("(Terminar)", "añade la información al atributo");

        private final String mensaje;
        private final String descripcion;

        OpcionClausulas(String mensaje, String descripcion) {
            this.mensaje = mensaje;
            this.descripcion = descripcion;
        }

        @Override
        public String mensaje(String... args) {
            return mensaje+": "+descripcion;
        }
    }

    public static Menu MENU_CLAUSULAS = new Menu("Información adicional", "Añadir nuevo: ", OpcionClausulas.values());

    private static String resultado;

    public static List<ClausulaAtributo> solicitarClausulas(InputUsuario input, boolean tipoTexto) {
        List<OpcionClausulas> clausulas = new ArrayList<>();
        int numeroDeOpciones = MENU_CLAUSULAS.count() - 2;

        if (tipoTexto)
            MENU_CLAUSULAS.inhabilitarOpcion(OpcionClausulas.AUTO_INCREMENT);

        do {
            OpcionClausulas opcionClausula = (OpcionClausulas) input.solicitarOpcionMenu(MENU_CLAUSULAS);

            if (opcionClausula == OpcionClausulas.CANCELAR)
                return cancelar();

            if (opcionClausula == OpcionClausulas.TERMINAR)
                return terminar(clausulas);

            clausulas.add(opcionClausula);
            MENU_CLAUSULAS.inhabilitarOpcion(opcionClausula);

            if (clausulas.size() == numeroDeOpciones)
                return terminar(clausulas);

        }
        while (true);
    }

    private static <T> T cancelar() {
        MENU_CLAUSULAS.rehabilitarOpciones();
        return null;
    }

    private static List<ClausulaAtributo> terminar(List<OpcionClausulas> clausulas) {
        MENU_CLAUSULAS.rehabilitarOpciones();
        resultado = mostrarClausulas(clausulas);
        return mapearClausulas(clausulas);
    }
    private static List<ClausulaAtributo> mapearClausulas(List<OpcionClausulas> clausulas) {
        return clausulas
                .stream()
                .map(ProgramaSolicitudClausulasColumnas::mapearClausula)
                .collect(Collectors.toList());
    }

    private static ClausulaAtributo mapearClausula(OpcionClausulas clausula) {
        return switch (clausula) {
            case ID -> ClausulaAtributo.PRIMARY_KEY;
            case UNICO -> ClausulaAtributo.UNIQUE;
            case AUTO_INCREMENT -> ClausulaAtributo.AUTO_INCREMENT;
            case NOT_NULL -> ClausulaAtributo.NOT_NULL;
            default -> throw new IllegalArgumentException("No hay clausulas de tipo " + clausula.mensaje);
        };
    }

    public static String mostrarResultado() {
        return resultado;
    }

    private static String mostrarClausulas(List<OpcionClausulas> clausulas) {
        return clausulas
                .stream()
                .map(clausula -> clausula.mensaje)
                .collect(Collectors.joining(", "));
    }
}

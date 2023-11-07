package ifp.kikeverea.programas;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.TipoAtributo;
import ifp.kikeverea.datos.Objeto;
import ifp.kikeverea.datos.Repositorio;
import ifp.kikeverea.datos.ValorAtributo;
import ifp.kikeverea.util.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProgramaManipulacionDatos {

    private static final String CONSULTAR_TABLA = "Consultar tabla";
    private static final String CONSULTAR = "Consultar entrada";
    private static final String CREAR = "Crear";
    private static final String EDITAR = "Editar";
    private static final String BORRAR = "Borrar";
    private static final String REGRESAR = "Regresar";

    private static final Menu<String> MENU_DATOS = Menu.menuSimple()
            .prompt("Acción: ")
            .opciones(OpcionMenu.opcionesSimples(CONSULTAR_TABLA, CONSULTAR, CREAR, EDITAR, BORRAR))
            .salida(OpcionMenu.opcionSimple(REGRESAR))
            .build();

    public static void ejecutar(Repositorio repositorio, InputUsuario input) throws SQLException {
        do {
            Programa.imprimirMensaje(
                    "TABLA: " + repositorio.nombreEntidad() + " " +
                    "(" + Presentador.separadoPorComas(repositorio.atributos(), Atributo::getNombre) + ")");

            String accion = input.solicitarOpcionMenu(MENU_DATOS);

            if (accion.equals(REGRESAR))
                return;

            switch (accion) {
                case CONSULTAR_TABLA -> consultarTabla(repositorio);
                case CONSULTAR -> consultarPorAtributo(repositorio, input);
                case CREAR -> crearObjeto(repositorio, input);
                case EDITAR -> editarObjeto(repositorio, input);
                case BORRAR -> borrarObjeto(repositorio, input);
            }
        }
        while (true);
    }

    private static void consultarTabla(Repositorio repositorio) throws SQLException {
        List<Objeto> objetos = repositorio.listarTodo();

        if (objetos.isEmpty()) {
            Programa.imprimirMensaje("No hay registros en la tabla");
            return;
        }

        String textoObjetos = objetosATexto(objetos, repositorio.nombreEntidad());

        Programa.imprimirMensaje("ENTRADAS\n" + textoObjetos);
    }

    private static void consultarPorAtributo(Repositorio repositorio, InputUsuario input) throws SQLException {
        Programa.imprimirMensaje("Consultar por atributos");

        List<Atributo> atributos = repositorio.atributos();

        Menu<Atributo> menuAtributos = Menu.nuevoMenu(Atributo.class)
                .mensajeInicial("Atributos disponibles:")
                .prompt("Consultar por atributo: ")
                .opciones(OpcionMenu.opciones(atributos, Atributo::getNombre))
                .salida(OpcionMenu.opcionNull("Regresar"))
                .build();

        Atributo atributo = input.solicitarOpcionMenu(menuAtributos);

        if (atributo == null)
            return;

        Object valorQuery = solicitarValorParaAtributo(atributo.getNombre()+" a buscar: ", atributo.getTipo(), input);
        List<Objeto> objetos = repositorio.buscar(new ValorAtributo(atributo, valorQuery));

        if (objetos.isEmpty()) {
            Programa.imprimirMensaje("No hay registros con los parámetros deseados");
            return;
        }

        String textoObjetos = objetosATexto(objetos, repositorio.nombreEntidad());
        Programa.imprimirMensaje("ENTRADAS\n" + textoObjetos);

    }

    private static void crearObjeto(Repositorio repositorio, InputUsuario input) {
        Programa.imprimirMensaje("Crear nuevo");

        Objeto objeto = repositorio.nuevoObjeto();
        List<Atributo> atributos = repositorio.atributos();

        for (Atributo atributo : atributos) {

            if (atributo.esAutoIncremental()) // no es necesario solicitar un valor, éste se genera automáticamente
                continue;

            Object valor = solicitarValorParaAtributo(atributo.getNombre()+": ", atributo.getTipo(), input);
            objeto.setValor(atributo.getNombre(), valor);
        }

        try {
            repositorio.insertar(objeto);
            Programa.imprimirMensaje(
                    "Registro " + objetoATexto(objeto, repositorio.nombreEntidad()) + " creado con éxito");
        }
        catch (SQLException e) {
            Programa.imprimirError("No se ha podido crear la entrada.\nCausa: " + e.getMessage());
        }
    }

    private static Object solicitarValorParaAtributo(String mensaje, TipoAtributo tipo, InputUsuario input) {
        return tipo == TipoAtributo.NUMERO
                ? input.solicitarEntero(mensaje)
                : input.solicitarTexto(mensaje);
    }

    private static void editarObjeto(Repositorio repositorio, InputUsuario input) throws SQLException {
        Programa.imprimirMensaje("Editar registros");

        Objeto objeto = solicitarObjeto(repositorio, input, "Editar entrada: ");

        if (objeto == null) {
            Programa.operacionCancelada();
            return;
        }

        while (true) {
            ValorAtributo valorAtributo = solicitarValorAtributo(objeto, input);

            if (valorAtributo == null)
                break;

            Object nuevoValor = solicitarValorParaAtributo(valorAtributo.nombreAtributo()+": ", valorAtributo.tipo(), input);
            valorAtributo.setValor(nuevoValor);
        }

        try {
            repositorio.actualizar(objeto);
            Programa.imprimirMensaje(
                    "Registro " + objetoATexto(objeto, repositorio.nombreEntidad()) + " editada con éxito");
        }
        catch (SQLException e) {
            Programa.imprimirError("No se ha podido editar la entrada.\nCausa: " + e.getMessage());
        }
    }

    private static Objeto solicitarObjeto(Repositorio repositorio, InputUsuario input, String prompt) throws SQLException{
        List<Objeto> objetos = repositorio.listarTodo();

        Menu<Objeto> menuObjetos = Menu.nuevoMenu(Objeto.class)
                .mensajeInicial("Registros:")
                .prompt(prompt)
                .opciones(OpcionMenu.opciones(objetos, objeto -> objetoATexto(objeto, repositorio.nombreEntidad())))
                .salida(OpcionMenu.opcionNull("Cancelar"))
                .build();

        return input.solicitarOpcionMenu(menuObjetos);
    }

    private static ValorAtributo solicitarValorAtributo(Objeto objeto, InputUsuario input) {
        List<ValorAtributo> valoresAEditar = valoresAEditar(objeto);
        Menu<ValorAtributo> menuValores = Menu.nuevoMenu(ValorAtributo.class)
                .mensajeInicial("Valores:")
                .prompt("Editar valor: ")
                .opciones(OpcionMenu.opciones(valoresAEditar, Objects::toString))
                .salida(OpcionMenu.opcionNull("Terminar"))
                .build();

        return input.solicitarOpcionMenu(menuValores);
    }

    private static List<ValorAtributo> valoresAEditar(Objeto objeto) {
        return objeto.getValoresAtributos()
                .stream()
                .filter(atributo -> !atributo.esClavePrimaria())
                .collect(Collectors.toList());
    }

    private static void borrarObjeto(Repositorio repositorio, InputUsuario input) throws SQLException {
        Programa.imprimirMensaje("Borrar entrada");
        Objeto objetoAEliminar = solicitarObjeto(repositorio, input, "Eliminar entrada: ");;

        if (objetoAEliminar == null) {
            Programa.operacionCancelada();
            return;
        }
        
        boolean continuar = input.solicitarSioNo(
                "¿Borrar entrada: " +
                        objetoATexto(objetoAEliminar, repositorio.nombreEntidad()) + "'? [Si/No] ");

        if (!continuar) {
            Programa.operacionCancelada();
            return;
        }

        try {
            repositorio.eliminar(objetoAEliminar);
            Programa.imprimirMensaje("Registro borrado con éxito");
        }
        catch (SQLException e) {
            Programa.imprimirError("No se ha podido borrar la entrada.\nCausa: " + e.getMessage());
        }
    }

    private static String objetosATexto(List<Objeto> objetos, String nombreEntidad) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objetos.size(); i++) {
            Objeto objeto = objetos.get(i);

            String textoObjeto = objetoATexto(objeto, nombreEntidad);
            sb.append(textoObjeto);

            if (i < objetos.size() -1)
                sb.append("\n");
        }

        return sb.toString();
    }

    private static String objetoATexto(Objeto objeto, String nombreEntidad) {
        String atributos = Presentador.separadoPorComas(objeto.getValoresAtributos(), ValorAtributo::toString);
        return "("+nombreEntidad+") "+ atributos;
    }
}

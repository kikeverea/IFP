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
//                    case BORRAR -> ejecutarProgramaBorrado(bd, input);
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

        String textoObjetos = Presentador.objetosATexto(objetos, repositorio.nombreEntidad());

        Programa.imprimirMensaje("ENTRADAS\n" + textoObjetos);
    }

    private static void consultarPorAtributo(Repositorio repositorio, InputUsuario input) throws SQLException {
        Programa.imprimirMensaje("Consultar por atributos");

        List<Atributo> atributos = repositorio.atributos();
        Menu<Atributo> menuAtributos = Menu.nuevoMenu(Atributo.class)
                .mensajeInicial("Atributos disponibles:")
                .prompt("Consultar por atributo: ")
                .opciones(OpcionMenu.opciones(atributos, Atributo::getNombre))
                .salida(OpcionMenu.opcionNull("Salida"))
                .build();

        Atributo atributo = input.solicitarOpcionMenu(menuAtributos);
        Object valorQuery = atributo.getTipo() == TipoAtributo.NUMERO
                ? input.solicitarEntero(atributo.getNombre() + " a buscar: ")
                : input.solicitarTexto(atributo.getNombre() + " a buscar: ");

        List<Objeto> objetos = repositorio.buscar(new ValorAtributo(atributo, valorQuery));

        if (objetos.isEmpty()) {
            Programa.imprimirMensaje("No hay registros con los parámetros deseados");
            return;
        }

        String textoObjetos = Presentador.objetosATexto(objetos, repositorio.nombreEntidad());
        Programa.imprimirMensaje("ENTRADAS\n" + textoObjetos);

    }

    private static void crearObjeto(Repositorio repositorio, InputUsuario input) throws SQLException {
        Objeto objeto = repositorio.nuevoObjeto();
        List<Atributo> atributos = repositorio.atributos();

        for (Atributo atributo : atributos) {

            if (atributo.esAutoIncremental()) // no es necesario solicitar un valor, éste se genera automáticamente
                continue;

            Object valor = atributo.getTipo() == TipoAtributo.NUMERO
                    ? input.solicitarEntero(atributo.getNombre()+": ")
                    : input.solicitarTexto(atributo.getNombre()+": ");

            objeto.setValor(atributo.getNombre(), valor);
        }

        repositorio.insertar(objeto);
    }

    private static void editarObjeto(Repositorio repositorio, InputUsuario inputUsuario) throws SQLException {
        List<Objeto> objetos = repositorio.listarTodo();

        Menu<Objeto> menuObjetos = Menu.nuevoMenu(Objeto.class)
                .mensajeInicial("Entradas:")
                .prompt("Editar entrada: ")
                .opciones(OpcionMenu.opciones(objetos, objeto -> Presentador.objetoATexto(objeto, repositorio.nombreEntidad())))
                .salida(OpcionMenu.opcionNull("Cancelar"))
                .build();
        
        Objeto objeto = inputUsuario.solicitarOpcionMenu(menuObjetos);

        if (objeto == null) {
            Programa.operacionCancelada();
            return;
        }

        List<ValorAtributo> valoresAEditar = valoresAEditar(objeto);
        Menu<ValorAtributo> menuValores = Menu.nuevoMenu(ValorAtributo.class)
                .mensajeInicial("Valores:")
                .prompt("Editar valor: ")
                .opciones(OpcionMenu.opciones(valoresAEditar, Objects::toString))
                .salida(OpcionMenu.opcionNull("Cancelar"))
                .build();

        ValorAtributo valorAtributo = inputUsuario.solicitarOpcionMenu(menuValores);

        if (valorAtributo == null) {
            Programa.operacionCancelada();
            return;
        }

        Object nuevoValor = valorAtributo.tipo() == TipoAtributo.NUMERO
                ? inputUsuario.solicitarEntero(valorAtributo.nombreAtributo()+": ")
                : inputUsuario.solicitarTexto(valorAtributo.nombreAtributo()+": ");

        valorAtributo.setValor(nuevoValor);
        repositorio.actualizar(objeto);
    }

    private static List<ValorAtributo> valoresAEditar(Objeto objeto) {
        return objeto.getValoresAtributos()
                .stream()
                .filter(atributo -> !atributo.esClavePrimaria())
                .collect(Collectors.toList());
    }
}

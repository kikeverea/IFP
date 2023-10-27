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
                    repositorio.nombreEntidad() + " " +
                    "(" + Presentador.separadoPorComas(repositorio.atributos(), Atributo::getNombre) + ")");

            String accion = input.solicitarOpcionMenu(MENU_DATOS);

            if (accion.equals(REGRESAR))
                return;

            switch (accion) {
                case CONSULTAR_TABLA -> consultarTabla(repositorio);
//                    case CONSULTAR -> ejecutarProgramaManipulacionDatos(bd, input);
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

        String nombreEntidad = repositorio.nombreEntidad();
        StringBuilder instancias = new StringBuilder();
        for (int i = 0; i < objetos.size(); i++) {
            Objeto objeto = objetos.get(i);

            String textoObjeto = Presentador.objetoATexto(objeto, nombreEntidad);
            instancias.append(textoObjeto);

            if (i < objetos.size() -1)
                instancias.append("\n");
        }
        Programa.imprimirMensaje("ENTRADAS\n" + instancias.toString());
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
                .salida(OpcionMenu.opcion(null, obj -> "Cancelar"))
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
                .salida(OpcionMenu.opcion(null, obj -> "Cancelar"))
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

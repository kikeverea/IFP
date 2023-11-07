package ifp.kikeverea.datos;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.BaseDeDatos;
import ifp.kikeverea.bd.Entidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula toda la lógica de manipulación de datos de una tabla de la base de datos
 */
public class Repositorio {

    private final BaseDeDatos bd;
    private final Entidad entidad;

    public Repositorio(BaseDeDatos bd, Entidad entidad) {
        this.bd = bd;
        this.entidad = entidad;
    }

    public String nombreEntidad() {
        return entidad.getNombre();
    }

    public Objeto nuevoObjeto() {
        return Objeto.instanciaDe(entidad);
    }

    public List<Atributo> atributos() {
        return entidad.getAtributos();
    }

    /**
     * Crea un nuevo registro en la tabla, correspondiente a la instancia de Objeto.
     * El valor de clave primaria generada al crear el registro será asignada al Objeto
     * @param objeto El Objeto a insertar
     * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
     */
    public void insertar(Objeto objeto) throws SQLException {
        try (PreparedStatement statement = Statements
                .insertInto(entidad.getNombre())
                .valores(objeto.getValoresAtributos())
                .statement(bd.getConexion(), Statement.RETURN_GENERATED_KEYS))
        {
            statement.executeUpdate();

            int claveGenerada = obtenerClaveGenerada(statement);
            objeto.setClavePrimaria(claveGenerada);
        }
    }

    private int obtenerClaveGenerada(PreparedStatement statement) throws SQLException {
        ResultSet setClaveGenerada = statement.getGeneratedKeys();
        setClaveGenerada.next();
        int claveGenerada = setClaveGenerada.getInt(1);
        setClaveGenerada.close();

        return claveGenerada;
    }

    /**
     * Produce un listado de Objetos correspondientes a cada registro en la tabla
     * @return una lista de Objetos
     * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
     */
    public List<Objeto> listarTodo() throws SQLException {
        List<Objeto> resultado = new ArrayList<>();

        try (PreparedStatement statement = Statements.selectFrom(entidad.getNombre()).statement(bd.getConexion());
             ResultSet set = statement.executeQuery())
        {
            ResultSetMetaData meta = set.getMetaData();
            while (set.next()) {
                Objeto objeto = extraerObjeto(set, meta);
                resultado.add(objeto);
            }
        }

        return resultado;
    }

    /**
     * Busca el registro en la tabla correspondiente al id proporcionado
     * @param id El id del registro a buscar
     * @return Una instancia de Objeto correspondiente al registro de la tabla
     * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
     */
    public Objeto buscarPorId(int id) throws SQLException {
        Atributo clavePrimaria = entidad.getClavePrimaria();
        ValorAtributo valorClavePrimaria = new ValorAtributo(clavePrimaria);
        valorClavePrimaria.setValor(id);

        return buscar(valorClavePrimaria).get(0);
    }

    /**
     * Busca registros en la tabla que contengan el valor proporcionado, de un campo específico
     * @param valorAtributo El campo y su valor, utilizados para filtrar la búsqueda
     * @return Una instancia de Objeto por cada registro que coincida con los parámetros proporcionados
     * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
     */
    public List<Objeto> buscar(ValorAtributo valorAtributo) throws SQLException {
        try (PreparedStatement statement = Statements
                    .selectFrom(entidad.getNombre())
                    .where(valorAtributo)
                    .statement(bd.getConexion());
             ResultSet set = statement.executeQuery())
        {
            List<Objeto> objetos = new ArrayList<>();
            while (set.next())
                objetos.add(extraerObjeto(set, set.getMetaData()));

            return objetos;
        }
    }

    private Objeto extraerObjeto(ResultSet set, ResultSetMetaData meta) throws SQLException {
        Objeto objeto = Objeto.instanciaDe(entidad);
        for (int i = 0; i < meta.getColumnCount(); i++) {
            String nombreAtributo = meta.getColumnName(i + 1);
            Object valor = set.getObject(i + 1);
            objeto.setValor(nombreAtributo, valor);
        }
        return objeto;
    }

    /**
     * Actualiza registros en la base de datos
     * @param objeto El Objeto cuyos valores de campos van a ser actualizados
     * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
     */
    public void actualizar(Objeto objeto) throws SQLException {
      try (PreparedStatement statement = Statements
              .update(entidad.getNombre())
              .set(objeto)
              .where(objeto.getClavePrimaria())
              .statement(bd.getConexion()))
        {
            statement.executeUpdate();
        }
    }

    /**
     * Elimina registros en la base de datos
     * @param objeto El Objeto que va a ser eliminado
     * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
     */
    public void eliminar(Objeto objeto) throws SQLException {
        try (PreparedStatement statement = Statements
                .deleteFrom(entidad.getNombre())
                .where(objeto.getClavePrimaria())
                .statement(bd.getConexion()))
        {
            statement.executeUpdate();
        }
    }


}

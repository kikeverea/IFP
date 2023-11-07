package ifp.kikeverea.datos;

import ifp.kikeverea.bd.Atributo;
import ifp.kikeverea.bd.BaseDeDatos;
import ifp.kikeverea.bd.Entidad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public Objeto buscarPorId(int id) throws SQLException {
        Atributo clavePrimaria = entidad.getClavePrimaria();
        ValorAtributo valorClavePrimaria = new ValorAtributo(clavePrimaria);
        valorClavePrimaria.setValor(id);

        return buscar(valorClavePrimaria).get(0);
    }

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

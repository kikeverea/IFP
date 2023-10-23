package ifp.kikeverea;

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

    public void insertar(Objeto objeto) throws SQLException {
        String insert = "INSERT INTO " + entidad.getNombre() + " " + declaracionValores(objeto.getAtributos());

        try (PreparedStatement statement = bd.getConexion().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            establecerValores(statement, objeto.getAtributos());
            statement.executeUpdate();

            ResultSet claveGenerada = statement.getGeneratedKeys();
            claveGenerada.next();
            objeto.setClavePrimaria(claveGenerada.getInt(1));
        }
    }

    private String declaracionValores(List<ValorAtributo> atributos) {
        StringBuilder lista = new StringBuilder();
        StringBuilder valores = new StringBuilder();

        lista.append("(");
        valores.append(" VALUES(");

        for (int i = 0; i < atributos.size(); i++) {
            ValorAtributo valorAtributo = atributos.get(i);

            lista.append(valorAtributo.nombreAtributo());
            valores.append("?");

            if (i < atributos.size() - 1) { //añade coma a todos los nombres y valores menos los últimos
                lista.append(", ");
                valores.append(", ");
            }
        }

        lista.append(")");
        valores.append(")");

        return lista + " " + valores;
    }

    private void establecerValores(PreparedStatement statement, List<ValorAtributo> atributos) throws SQLException {
        for (int i = 0; i < atributos.size(); i++) {
            ValorAtributo valorAtributo = atributos.get(i);
            statement.setObject(i+1, valorAtributo.valor(), valorAtributo.tipoSql());
        }
    }

    public List<Objeto> listarTodo() throws SQLException {
        String declaration = "SELECT * FROM " + entidad.getNombre();
        List<Objeto> resultado = new ArrayList<>();

        try (PreparedStatement statement = bd.getConexion().prepareStatement(declaration);
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
        Atributo primaryKey = entidad.getClavePrimaria();
        return buscar(primaryKey.getNombre(), id, primaryKey.getTipo().getTipoSql());
    }

    public Objeto buscar(ValorAtributo atributo) throws SQLException {
        return buscar(atributo.nombreAtributo(), atributo.valor(), atributo.tipoSql());
    }

    private Objeto buscar(String nombreAtributo, Object valor, int tipoSql) throws SQLException {
        String declaracion = "SELECT * FROM " + entidad.getNombre() + " WHERE " + nombreAtributo + " = ?";

        try (PreparedStatement statement = bd.getConexion().prepareStatement(declaracion))
        {
            statement.setObject(1, valor, tipoSql);
            ResultSet set = statement.executeQuery();

            Objeto objeto = set.next() ? extraerObjeto(set, set.getMetaData()) : null;
            set.close();

            return objeto;
        }
    }

    public void actualizarObjeto(Objeto objeto) throws SQLException {
        List<ValorAtributo> atributos = objeto.getAtributos();
        ValorAtributo clavePrimaria = objeto.getClavePrimaria();
        String declaracion =
                "UPDATE " + entidad.getNombre() + declaracionValoresSet(atributos) +
                " WHERE " + clavePrimaria.nombreAtributo() + " = ?";

        try (PreparedStatement statement = bd.getConexion().prepareStatement(declaracion))
        {

            establecerValores(statement, atributos);
            statement.setObject(atributos.size(), clavePrimaria.valor(), clavePrimaria.tipoSql());
            statement.executeUpdate();
        }
    }

    public void eliminar(Objeto objeto) throws SQLException {
        ValorAtributo clavePrimaria = objeto.getClavePrimaria();
        String declaracion = "DELETE FROM " + entidad.getNombre() + " WHERE " + clavePrimaria.nombreAtributo() + " = ?";

        try (PreparedStatement statement = bd.getConexion().prepareStatement(declaracion))
        {

            statement.setObject(1, clavePrimaria.valor(), clavePrimaria.tipoSql());
            statement.executeUpdate();
        }
    }

    private String declaracionValoresSet(List<ValorAtributo> atributos) {
        StringBuilder sb = new StringBuilder(" SET ");
        int numeroValores = atributos.size() - 1; // valores, menos la clave primaria

        for (int i = 0; i < atributos.size(); i++) {
            ValorAtributo atributo = atributos.get(i);

            if (atributo.esClavePrimaria())
                continue;

            sb.append(atributo.nombreAtributo());
            sb.append(" = ");
            sb.append("?");

            if (i < numeroValores -1)
                sb.append(", ");
        }
        return sb.toString();
    }

    private Objeto extraerObjeto(ResultSet set, ResultSetMetaData meta) throws SQLException {
        Objeto objeto = entidad.nuevaInstancia();
        for (int i = 0; i < meta.getColumnCount(); i++) {
            String nombreAtributo = meta.getColumnName(i + 1);
            Object valor = set.getObject(i + 1);
            objeto.setValor(nombreAtributo, valor);
        }
        return objeto;
    }
}

package ifp.kikeverea.bd;

import ifp.kikeverea.util.Presentador;

import java.sql.*;
import java.util.*;

public class BaseDeDatos {

    private final String nombre;

    public BaseDeDatos(String nombre) {
        this.nombre = nombre;
    }

    private Connection conexion;

    public Connection getConexion() {
        return conexion;
    }

    public void connectar(String url) throws SQLException {
        conexion = DriverManager.getConnection(url);
    }

    public boolean isConectada() throws SQLException {
        return !conexion.isClosed();
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public void crearEntidad(Entidad entidad) throws SQLException {
        String definicionAtributos = Presentador.separadoPorComas(entidad.getAtributos(), Atributo::definicion);

        String definicion =
                "CREATE TABLE " + entidad.getNombre() + "(" + definicionAtributos + ")";

        try (PreparedStatement statement = conexion.prepareStatement(definicion)) {
            statement.executeUpdate();
        }
    }

    public void eliminarEntidad(String nombreEntidad) throws SQLException {
        String definicion = "DROP TABLE " + nombreEntidad;

        try (PreparedStatement statement = conexion.prepareStatement(definicion)) {
            statement.execute();
        }
    }

    public List<String> listarEntidades() throws SQLException {


        List<String> entidades = new ArrayList<>();

        try(PreparedStatement statement = statementListarEntidades();
            ResultSet set = statement.executeQuery())
        {
            while (set.next())
                entidades.add(set.getString(1));
        }

        return entidades;
    }

    private PreparedStatement statementListarEntidades() throws SQLException {
        String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";
        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setString(1, nombre);
        return statement;
    }

    public boolean entidadExiste(String nombreEntidad) throws SQLException {
        try (PreparedStatement query = statementEntidadExiste(nombreEntidad);
             ResultSet result = query.executeQuery())
        {
            return result.next();
        }
    }

    private PreparedStatement statementEntidadExiste(String nombreEntidad) throws SQLException {
        String declaracion = "SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?";
        PreparedStatement statement = conexion.prepareStatement(declaracion);
        statement.setString(1, nombreEntidad);

        return statement;
    }

    public Entidad buscarEntidad(String nombreEntidad) throws SQLException {

        try(PreparedStatement statement = statementBuscarAtributos(nombreEntidad);
            ResultSet set = statement.executeQuery())
        {
            List<Atributo> atributos = new ArrayList<>();

            while (set.next()) {
                String nombreAtributo = set.getString(set.findColumn("COLUMN_NAME"));
                TipoAtributo tipo = TipoAtributo.getTipo(set.getString(set.findColumn("DATA_TYPE")));
                List<ClausulaAtributo> clausulas = extraerClausulas(set);

                Atributo atributo = Atributo
                        .nuevoAtributo(nombreAtributo)
                        .deTipo(tipo, clausulas.toArray(new ClausulaAtributo[0]));

                atributos.add(atributo);
            }
            return atributos.isEmpty() ? null : new Entidad(nombreEntidad, atributos);
        }
    }

    private PreparedStatement statementBuscarAtributos(String nombreEntidad) throws SQLException {
        String query =
                "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_KEY, IS_NULLABLE " +
                        "FROM INFORMATION_SCHEMA.COLUMNS " +
                        "WHERE TABLE_NAME = ?";

        PreparedStatement statement = conexion.prepareStatement(query);
        statement.setString(1, nombreEntidad);

        return statement;
    }

    private List<ClausulaAtributo> extraerClausulas(ResultSet set) throws SQLException {

        String clave = set.getString(set.findColumn("COLUMN_KEY"));
        boolean clavePrimaria = clave.equals("PRI");
        boolean unique = clave.equals("UNI");
        boolean notNull = set.getString(set.findColumn("IS_NULLABLE")).equals("NO");

        List<ClausulaAtributo> clausulas = new ArrayList<>();

        if (clavePrimaria) {
            clausulas.add(ClausulaAtributo.PRIMARY_KEY);
            clausulas.add(ClausulaAtributo.AUTO_INCREMENT);
        }
        else if (unique)
            clausulas.add(ClausulaAtributo.UNIQUE);

        if (notNull)
            clausulas.add(ClausulaAtributo.NOT_NULL);

        return clausulas;
    }
}

package ifp.kikeverea.bd;

import ifp.kikeverea.datos.Repositorio;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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
        String definicionAtributos = extraerDefinicionDeAtributos(entidad.getAtributos());

        String definicion =
                "CREATE TABLE IF NOT EXISTS " + entidad.getNombre() + "(" + definicionAtributos + ")";

        try (PreparedStatement statement = conexion.prepareStatement(definicion))
        {
            statement.executeUpdate();
        }
    }

    private String extraerDefinicionDeAtributos(List<Atributo> atributos) {
        return atributos
                .stream()
                .map(Atributo::definicion)
                .collect(Collectors.joining(", "));
    }

    public Repositorio repositorioParaEntidad(Entidad entidad) {
        return new Repositorio(this, entidad);
    }

    public void eliminarEntidad(String nombreEntidad) throws SQLException {
        String definicion = "DROP TABLE IF EXISTS " + nombreEntidad;

        try (PreparedStatement statement = conexion.prepareStatement(definicion)) {
            statement.execute();
        }
    }

    public List<String> listarEntidades() throws SQLException {
        String statementTablas = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";

        List<String> entidades = new ArrayList<>();

        try(PreparedStatement statement = conexion.prepareStatement(statementTablas)) {
            statement.setString(1, nombre);
            ResultSet set = statement.executeQuery();

            while (set.next())
                entidades.add(set.getString(1));

            set.close();
        }

        return entidades;
    }

    public Entidad getEntidad(String nombreEntidad) throws SQLException {

        String declaracion =
                "SELECT COLUMN_NAME, DATA_TYPE, COLUMN_KEY, IS_NULLABLE " +
                        "FROM INFORMATION_SCHEMA.COLUMNS " +
                        "WHERE TABLE_NAME = ?";

        try(PreparedStatement statement = conexion.prepareStatement(declaracion)) {

            statement.setString(1, nombreEntidad);

            ResultSet set = statement.executeQuery();
            List<Atributo> atributos = new ArrayList<>();

            while (set.next()) {
                String nombreAtributo = set.getString(set.findColumn("COLUMN_NAME"));
                TipoAtributo tipo = TipoAtributo.getTipo(set.getString(set.findColumn("DATA_TYPE")));
                List<RestriccionAtributo> restricciones = extraerRestricciones(set);

                Atributo atributo = Atributo
                        .nuevoAtributo(nombreAtributo)
                        .deTipo(tipo, restricciones.toArray(new RestriccionAtributo[0]));

                atributos.add(atributo);
            }
            Entidad entidad = new Entidad(nombreEntidad, atributos);
            set.close();
            return entidad;
        }
    }

    private List<RestriccionAtributo> extraerRestricciones(ResultSet set) throws SQLException {

        String clave = set.getString(set.findColumn("COLUMN_KEY"));
        boolean clavePrimaria = clave.equals("PRI");
        boolean unique = clave.equals("UNI");
        boolean notNull = set.getString(set.findColumn("IS_NULLABLE")).equals("NO");

        List<RestriccionAtributo> restricciones = new ArrayList<>();

        if (clavePrimaria) {
            restricciones.add(RestriccionAtributo.PRIMARY_KEY);
            restricciones.add(RestriccionAtributo.AUTO_INCREMENT);
        } else if (unique)
            restricciones.add(RestriccionAtributo.UNIQUE);

        if (notNull)
            restricciones.add(RestriccionAtributo.NOT_NULL);

        return restricciones;
    }
}

package ifp.kikeverea;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class BaseDeDatos {

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

    public boolean anadirEntidad(Entidad entidad) throws SQLException {
        String definicionAtributos = extraerDefinicionDeAtributos(entidad.getAtributos());

        String definicion =
                "CREATE TABLE IF NOT EXISTS " + entidad.getNombre() + "(" + definicionAtributos + ")";

        try (PreparedStatement statement = conexion.prepareStatement(definicion))
        {
            return statement.executeUpdate() == 1;
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
}

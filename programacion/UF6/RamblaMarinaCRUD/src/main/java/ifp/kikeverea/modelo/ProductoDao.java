package ifp.kikeverea.modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {
    private static final String TABLE_NAME = "productos";
    private static final String COL_ID = "id";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_PRECIO = "precio";
    private static final String COL_CANTIDAD = "cantidad";

    private final Connection connection;

    public ProductoDao(Connection connection) {
        this.connection = connection;
    }

    public boolean insertar(Producto producto) {
        try {
            int insertados = connection.createStatement().executeUpdate(
                "INSERT INTO " + TABLE_NAME + "(" +
                    COL_NOMBRE + ", " +
                    COL_PRECIO + ", " +
                    COL_CANTIDAD + ")" +
                " VALUES (" +
                    "'" + producto.getNombre() + "' , " +
                    producto.getPrecio() + ", " +
                    producto.getCantidad() + ")");

            return insertados == 1;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public Producto consultarId(int id) {
        return consultar("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + id);
    }

    public Producto consultarUltimo() {
        return consultar("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID + " DESC LIMIT 1");
    }

    private Producto consultar(String query) {
        try (ResultSet resultado = connection.createStatement().executeQuery(query))
        {
            // mueve el cursor a la siguiente fila
            boolean hayFila = resultado.next();

            if (!hayFila)
                // no se obtuvo ningún resultado
                return null;

            return extraerProducto(resultado);
        }
        catch (SQLException e) {
            
            return null;
        }
    }

    private Producto extraerProducto(ResultSet resultado) throws SQLException {
        int id = resultado.getInt(resultado.findColumn(COL_ID));
        String nombre = resultado.getString(resultado.findColumn(COL_NOMBRE));
        float precio = resultado.getFloat(resultado.findColumn(COL_PRECIO));
        int cantidad = resultado.getInt(resultado.findColumn(COL_CANTIDAD));

        return new Producto(id, nombre, precio, cantidad);
    }

    public List<Producto> consultarTodos() {
        return consultarVarios("SELECT * FROM " + TABLE_NAME);
    }

    private List<Producto> consultarVarios(String query) {
        try (ResultSet resultado = connection.createStatement().executeQuery(query))
        {
            List<Producto> extraidos = new ArrayList<>();

            while (resultado.next())
                extraidos.add(extraerProducto(resultado));

            return extraidos;
        }
        catch (SQLException e) {
            
            return null;
        }
    }

    public boolean modificar(Producto product) {
        if (product.getId() < 1)
            throw new IllegalArgumentException("Product id less than 1");

        try {
            int actualizados = connection.createStatement().executeUpdate(
                "UPDATE " + TABLE_NAME +
                " SET " +
                    COL_NOMBRE + " = '" + product.getNombre() + "', " +
                    COL_PRECIO + " = " + product.getPrecio() + ", " +
                    COL_CANTIDAD +" = " + product.getCantidad() +
                " WHERE " + COL_ID + " = " + product.getId());

            return actualizados >= 1;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean eliminar(Producto producto) {
        if (producto.getId() < 1)
            throw new IllegalArgumentException("Product id less than 1");

        try {
            int eliminados = connection.createStatement().executeUpdate(
                "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + producto.getId());

            return eliminados >= 1;
        }
        catch (SQLException e) {
            return false;
        }
    }
}

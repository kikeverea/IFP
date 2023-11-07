package ifp.kikeverea.datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Statements {

    public static Insert insertInto(String nombreEntidad) {
        return new Insert(nombreEntidad);
    }

    public static Where selectFrom(String nombreEntidad) {
        return new Where("SELECT * FROM " + nombreEntidad);
    }

    public static Update update(String nombreEntidad) {
        return new Update(nombreEntidad);
    }

    public static Where deleteFrom(String nombreEntidad) {
        return new Where("DELETE FROM " + nombreEntidad);
    }

    public static class Insert {
        private final String nombreEntidad;

        public Insert(String nombreEntidad) {
            this.nombreEntidad = nombreEntidad;
        }

        /**
         * Añade la lista de campos y valores que se van a insertar
         * @param valoresAtributos la lista de valores y atributos
         * @return Un StatementBuilder que creará el statement INSERT con los parámetro proporcionados
         */
        public StatementBuilder valores(List<ValorAtributo> valoresAtributos) {
            StringBuilder atributos = new StringBuilder();  // lista de atributos
            StringBuilder valores = new StringBuilder();    // lista de valores

            // produce String con formato (atributo1, atributo2, ..., atributoN) VALUES (?,?,...,?)
            atributos.append("(");
            valores.append(" VALUES(");

            for (int i = 0; i < valoresAtributos.size(); i++) {
                ValorAtributo valorAtributo = valoresAtributos.get(i);

                atributos.append(valorAtributo.nombreAtributo());
                valores.append("?");

                if (i < valoresAtributos.size() - 1) { //añade coma a todos los nombres y valores, menos los últimos
                    atributos.append(", ");
                    valores.append(", ");
                }
            }

            atributos.append(")");
            valores.append(")");

            return new StatementBuilder("INSERT INTO " + nombreEntidad + " " + atributos + " " + valores, valoresAtributos);
        }
    }

    /**
     * Clase que encapsula la búsqueda de registros filtrada por un valor de un campo específico
     */
    public static class Where {
        private final String query;
        private final List<ValorAtributo> valores;

        public Where(String query) {
            this(query, new ArrayList<>());
        }

        public Where(String query, List<ValorAtributo> valores) {
            this.query = query;
            this.valores = valores;
        }

        /**
         * Añade el campo y valor con que se filtrará la búsqueda
         * @param valorAtributo el campo por el que se filtrará la búsqueda, con su valor asociado
         * @return Un StatementBuilder que creará un statement a partir de la 'query' pasada a esta intancia y
         * la clásula WHERE creada a partir del campo y su valor, pasados en este método
         */
        public StatementBuilder where(ValorAtributo valorAtributo) {
            valores.add(valorAtributo);
            return new StatementBuilder(query + " WHERE " + valorAtributo.nombreAtributo() + " = ?", valores);
        }

        /**
         * Crea un PreparedStatement directamente, sin añadir la cláusula WHERE a la 'query'
         * @param connection Conexión que creará la instancia de PreparedStatement
         * @return Una nueva instancia de PreparedStatement
         * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
         */
        public PreparedStatement statement(Connection connection) throws SQLException {
            return new StatementBuilder(query).statement(connection);
        }
    }

    public static class Update {

        private final String nombreEntidad;

        public Update(String nombreEntidad) {
            this.nombreEntidad = nombreEntidad;
        }

        /**
         * Asigna el Objeto cuyos valores serán actualizados en el registro
         * @param objeto El objeto a actualizar, con sus valores ya actualizados
         * @return Una instancia de Where, que obligará a indicar los registros a actualizar, para evitar actualziaciones
         * no deseadas de tablas enteras
         */
        public Where set(Objeto objeto) {
            // no se actualizarán valores de campos autoincrementales, por lo que son ignorados en este método
            List<ValorAtributo> atributos = atributosNoAutoincrementales(objeto);

            StringBuilder update = new StringBuilder();
            update.append("UPDATE ").append(nombreEntidad).append(" SET ");

            // produce String con formato SET atributo1 = valor1, atributo2 = valor2, ... atributoN = valorN

            for (int i = 0; i < atributos.size(); i++) {
                ValorAtributo atributo = atributos.get(i);

                update.append(atributo.nombreAtributo());
                update.append(" = ");
                update.append("?");

                if (i < atributos.size() -1)
                    update.append(", ");
            }
            return new Where(update.toString(), atributos);
        }

        private List<ValorAtributo> atributosNoAutoincrementales(Objeto objeto) {
            return objeto.getValoresAtributos()
                    .stream()
                    .filter(atributo -> !atributo.esAutoIncremental())
                    .collect(Collectors.toList());
        }
    }

    public static class StatementBuilder {
        private final String query;
        private final List<ValorAtributo> valores;

        public StatementBuilder(String query) {
            this(query, new ArrayList<>());
        }

        public StatementBuilder(String query, List<ValorAtributo> valores) {
            this.query = query;
            this.valores = valores;
        }
        /**
         * Crea una instancia de PreparedStatment
         * @param connection la conexión que creará la instancia de PreparedStatement
         * @param autoGeneratedKeys indica si se desea que el PreparedStatement regrese la clave auto-generada al ejectutar el statement
         * @return Una instancia de PreparedStatement
         * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
         */
        public PreparedStatement statement(Connection connection, int autoGeneratedKeys) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(query, autoGeneratedKeys);
            establecerValores(statement);
            return statement;
        }

        /**
         * Crea una instancia de PreparedStatment
         * @param connection la conexión que creará la instancia de PreparedStatement
         * @return Una instancia de PreparedStatement
         * @throws SQLException si ocurre un error de acceso a la base de datos, o este método es invocado con una conexión cerrada
         */
        public PreparedStatement statement(Connection connection) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(query);
            establecerValores(statement);
            return statement;
        }

        private void establecerValores(PreparedStatement statement) throws SQLException {
            for (int i = 0; i < valores.size(); i++) {
                ValorAtributo valorAtributo = valores.get(i);
                statement.setObject(i+1, valorAtributo.valor(), valorAtributo.tipoSql());
            }
        }
    }
}

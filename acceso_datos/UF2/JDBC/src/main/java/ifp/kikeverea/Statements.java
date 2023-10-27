package ifp.kikeverea;

import ifp.kikeverea.datos.Objeto;
import ifp.kikeverea.datos.ValorAtributo;

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

        public StatementBuilder valores(List<ValorAtributo> valoresAtributos) {
            StringBuilder atributos = new StringBuilder();
            StringBuilder valores = new StringBuilder();

            atributos.append("(");
            valores.append(" VALUES(");

            for (int i = 0; i < valoresAtributos.size(); i++) {
                ValorAtributo valorAtributo = valoresAtributos.get(i);

                atributos.append(valorAtributo.nombreAtributo());
                valores.append("?");

                if (i < valoresAtributos.size() - 1) { //añade coma a todos los nombres y valores menos los últimos
                    atributos.append(", ");
                    valores.append(", ");
                }
            }

            atributos.append(")");
            valores.append(")");

            return new StatementBuilder("INSERT INTO " + nombreEntidad + " " + atributos + " " + valores, valoresAtributos);
        }
    }

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

        public StatementBuilder where(ValorAtributo valorAtributo) {
            valores.add(valorAtributo);
            return new StatementBuilder(query + " WHERE " + valorAtributo.nombreAtributo() + " = ?", valores);
        }

        public PreparedStatement statement(Connection connection) throws SQLException {
            return new StatementBuilder(query).statement(connection);
        }
    }

    public static class Update {

        private final String nombreEntidad;

        public Update(String nombreEntidad) {
            this.nombreEntidad = nombreEntidad;
        }

        public Where set(Objeto objeto) {
            List<ValorAtributo> atributos = atributosNoAutoincrementales(objeto);

            StringBuilder update = new StringBuilder();
            update.append("UPDATE ").append(nombreEntidad).append(" SET ");

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

        public PreparedStatement statement(Connection connection, int autoGeneratedKeys) throws SQLException {
            PreparedStatement statement = connection.prepareStatement(query, autoGeneratedKeys);
            establecerValores(statement);
            return statement;
        }

        public PreparedStatement statement(Connection connection) throws SQLException {
            System.out.println("Statement: " + query);
            PreparedStatement statement = connection.prepareStatement(query);
            establecerValores(statement);
            return statement;
        }

        private void establecerValores(PreparedStatement statement) throws SQLException {
            System.out.println(valores);
            for (int i = 0; i < valores.size(); i++) {
                ValorAtributo valorAtributo = valores.get(i);
                statement.setObject(i+1, valorAtributo.valor(), valorAtributo.tipoSql());
            }
        }
    }
}

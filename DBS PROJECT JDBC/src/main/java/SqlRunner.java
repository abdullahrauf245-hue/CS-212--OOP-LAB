import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlRunner {
    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/?allowMultiQueries=true&useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USER = "nothing";
    private static final String DEFAULT_PASS = "Abdullah";
    private static final String DEFAULT_SQL_PATH = "Project DBMS.sql";

    public static void main(String[] args) throws Exception {
        String url = getenvOrDefault("DB_URL", DEFAULT_URL);
        String user = getenvOrDefault("DB_USER", DEFAULT_USER);
        String pass = getenvOrDefault("DB_PASS", DEFAULT_PASS);
        String sqlPath = args.length > 0 ? args[0] : DEFAULT_SQL_PATH;

        String sql = Files.readString(Path.of(sqlPath));
        List<String> statements = splitSql(sql);

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {
            for (String statement : statements) {
                if (statement.isBlank()) {
                    continue;
                }
                boolean hasResult = stmt.execute(statement);
                if (hasResult) {
                    printResultSet(stmt.getResultSet());
                }
            }
        }

        System.out.println("SQL script executed successfully.");
    }

    private static String getenvOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? fallback : value;
    }

    private static List<String> splitSql(String sql) {
        List<String> statements = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;

        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            char next = (i + 1 < sql.length()) ? sql.charAt(i + 1) : '\0';

            if (!inSingleQuote && !inDoubleQuote && c == '-' && next == '-') {
                i = skipLineComment(sql, i + 2);
                continue;
            }

            if (c == '\'' && !inDoubleQuote) {
                if (inSingleQuote && next == '\'') {
                    current.append(c).append(next);
                    i++;
                    continue;
                }
                inSingleQuote = !inSingleQuote;
            } else if (c == '"' && !inSingleQuote) {
                if (inDoubleQuote && next == '"') {
                    current.append(c).append(next);
                    i++;
                    continue;
                }
                inDoubleQuote = !inDoubleQuote;
            }

            if (c == ';' && !inSingleQuote && !inDoubleQuote) {
                String stmt = current.toString().trim();
                if (!stmt.isEmpty()) {
                    statements.add(stmt);
                }
                current.setLength(0);
                continue;
            }

            current.append(c);
        }

        String tail = current.toString().trim();
        if (!tail.isEmpty()) {
            statements.add(tail);
        }

        return statements;
    }

    private static int skipLineComment(String sql, int startIndex) {
        int i = startIndex;
        while (i < sql.length() && sql.charAt(i) != '\n') {
            i++;
        }
        return i;
    }

    private static void printResultSet(ResultSet rs) throws Exception {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        while (rs.next()) {
            StringBuilder row = new StringBuilder();
            for (int i = 1; i <= cols; i++) {
                if (i > 1) {
                    row.append(" | ");
                }
                row.append(rs.getString(i));
            }
            System.out.println(row);
        }
    }
}

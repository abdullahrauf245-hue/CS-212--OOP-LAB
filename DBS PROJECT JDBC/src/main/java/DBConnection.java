import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DEFAULT_URL = "jdbc:mysql://127.0.0.1:3306/kidney_transplant?allowMultiQueries=true&useSSL=false&serverTimezone=UTC";
    private static final String DEFAULT_USER = "root";
    private static final String DEFAULT_PASS = "Abdullah";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = getenvOrDefault("DB_URL", DEFAULT_URL);
        String user = getenvOrDefault("DB_USER", DEFAULT_USER);
        String pass = getenvOrDefault("DB_PASS", DEFAULT_PASS);
        return DriverManager.getConnection(url, user, pass);
    }

    private static String getenvOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? fallback : value;
    }
}

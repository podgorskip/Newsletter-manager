import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connection;

    static {
        String url = "jdbc:mysql://localhost:3306/newsletter";
        String user = "root";
        String password = "MySQL.password";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DatabaseConnection() {}
}

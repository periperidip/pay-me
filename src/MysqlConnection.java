
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MysqlConnection {
    Connection connection;
    Statement statement;

    public MysqlConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydatabase?characterEncoding=latin1",
                "saksham",
                "1234567890");
            statement = connection.createStatement();
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }
    public static void main(String[] args) {
        new MysqlConnection();
    }
}
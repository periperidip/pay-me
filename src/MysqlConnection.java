import java.sql.Connection;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MysqlConnection {
    MysqlDataSource dataSource;
    Connection connection;
    Statement statement;

    MysqlConnection() {
        try {
            dataSource = new MysqlDataSource();
            dataSource.setUser("saksham");
            dataSource.setPassword("1234567890");
            dataSource.setURL("jdbc:mysql://localhost:3306/mydatabase?characterEncoding=latin1");
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }
}

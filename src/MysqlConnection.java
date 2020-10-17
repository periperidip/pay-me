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
            dataSource.setUser("abhay");
            dataSource.setPassword("abhay");
            dataSource.setURL("jdbc:mysql://localhost:3306/payme?characterEncoding=latin1");
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        } catch (Exception exception) {
            System.err.println(exception);
        }
    }
}

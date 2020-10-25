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
            dataSource.setUser("wWmdXesrny");
            dataSource.setPassword("IZISIVykNb");
            dataSource.setURL("jdbc:mysql://remotemysql.com:3306/wWmdXesrny?characterEncoding=latin1");
            connection = dataSource.getConnection();
            statement = connection.createStatement();
        } catch (Exception exception) {
            System.err.println("got exception "+exception);
        }
    }
}

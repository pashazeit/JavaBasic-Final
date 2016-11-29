package DB;


import com.mysql.fabric.jdbc.FabricMySQLDriver;
import java.sql.*;

/**
 * Created by Zeit on 22.11.2016.
 */

public class DBconnect {

    private Connection connection;

    public DBconnect() throws SQLException {
        DriverManager.registerDriver(new FabricMySQLDriver());
    }

    public Connection conn(String url, String user, String pass) throws SQLException {
        if (connection != null)
            return connection;
        connection = DriverManager.getConnection(url, user, pass);
        return connection;
    }
}





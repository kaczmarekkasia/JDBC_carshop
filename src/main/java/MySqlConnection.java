import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnection {

    private MySqlConnectionParameters parameters;
    private MysqlDataSource dataSource;

    public MySqlConnection() throws IOException {
        parameters = new MySqlConnectionParameters();
        initialize();
    }


    //to będzie odpowiedzialne za załadowania danych
    private void initialize(){
        dataSource = new MysqlDataSource();

        dataSource.setPort(Integer.parseInt(parameters.getDbPort()));
        dataSource.setUser(parameters.getDbUsername());
        dataSource.setServerName(parameters.getDbHost());
        dataSource.setPassword(parameters.getDbPassword());
        dataSource.setDatabaseName(parameters.getDbName());

        try {
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

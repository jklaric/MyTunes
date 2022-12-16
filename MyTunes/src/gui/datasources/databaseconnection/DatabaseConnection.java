package gui.datasources.databaseconnection;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

public class DatabaseConnection {
    private SQLServerDataSource dataSource;

    /**
     * Constructor for database connection. Connects to a database with selected login info.
     * Keep in mind that if the connection is attempted outside of EASV grounds that the connection will fail.
     * Remember to use the provided VPN for such operations.
     */
    public DatabaseConnection(){
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MyTunesGroupThreeInternational");
        dataSource.setUser("CSe22B_13");
        dataSource.setPassword("JakovKlaric003");
        dataSource.setPortNumber(1433);
        dataSource.setTrustServerCertificate(true);
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}
import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter

public class MySqlConnectionParameters {
    private static final String PROPERTIES_FILE_NAME = "/jdbc.properties";
    private Properties properties;

    private String dbHost; //127.0.0.1
    private String dbPort;
    private String dbUsername;
    private String dbPassword;
    private String dbName ;

    public MySqlConnectionParameters() throws IOException {
        loadConfigurationFrom(PROPERTIES_FILE_NAME);

        dbHost = loadParameters("jdbc.database.host");
        dbPort = loadParameters("jdbc.database.port");
        dbUsername = loadParameters("jdbc.username");
        dbPassword = loadParameters("jdbc.password");
        dbName = loadParameters("jdbc.database.name");
    }

    private void loadConfigurationFrom(String resourceName) throws IOException {
        properties = new Properties();

        InputStream propertiesStream = this.getClass().getResourceAsStream(resourceName);
        if (propertiesStream==null){
            throw new FileNotFoundException("Resource file cannot be loaded.");
        }
        properties.load(propertiesStream);
    }

    private String loadParameters(String propertyName){
        return properties.getProperty(propertyName);
    }

}

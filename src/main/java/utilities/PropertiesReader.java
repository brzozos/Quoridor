package utilities;

import java.io.*;
import java.util.Properties;

public class PropertiesReader {
    private String connectionString;
    private String language;

    Properties properties;
    InputStream input = null;


    public PropertiesReader(){
        properties = new Properties();
        try {
            input = new FileInputStream("data/lunchOptions");
            properties.load(input);
            connectionString = properties.getProperty("dbconnectionstring");
            language = properties.getProperty("language");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

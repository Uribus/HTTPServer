package httpserver.config;

import java.io.FileReader;
import java.io.IOException;

import httpserver.utils.Json;
import tools.jackson.databind.JsonNode;

public class ConfigurationManager {

    private static ConfigurationManager configurationManager; // Singleton
    private static Configuration currentConfiguration;


    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if (configurationManager == null) 
            configurationManager = new ConfigurationManager();
        
        return configurationManager;
    }

    /**
     * Load configuration file by the path provided
     * @param filePath
     */
    public void loadConfigurationFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        StringBuffer sBuffer = new StringBuffer();

        int c;
        while ((c = fileReader.read()) != -1) {
            sBuffer.append((char) c);
        }

        JsonNode conf = Json.parse(sBuffer.toString());
        currentConfiguration = Json.fromJson(conf, Configuration.class);
    } 

    /**
     * Returns current loaded Configuration
     */
    public void getCurrentConfiguration() {

    }
    
}

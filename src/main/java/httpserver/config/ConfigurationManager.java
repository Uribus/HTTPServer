package httpserver.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import httpserver.utils.Json;
import tools.jackson.core.JacksonException;
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
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sBuffer = new StringBuffer();

        int c;
        try {
            while ((c = fileReader.read()) != -1) {
                sBuffer.append((char) c);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode conf;
        try {
            conf = Json.parse(sBuffer.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing Configuration File", e);
        }

        try {
        currentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JacksonException e) {
            throw new HttpConfigurationException("Error parsing configuration file, internal", e);
        }
    } 

    /**
     * Returns current loaded Configuration
     */
    public Configuration getCurrentConfiguration() {
        if (currentConfiguration == null) {
            throw new HttpConfigurationException("No current Configuration set.");
        }

        return currentConfiguration;
    }
    
}

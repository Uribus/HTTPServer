package httpserver;

import httpserver.config.Configuration;
import httpserver.config.ConfigurationManager;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Setting up server configuration...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Configuration details\n    Port: " 
            + conf.getPort() + "\n    WebRoot: " 
            + conf.getWebroot());
    }
}

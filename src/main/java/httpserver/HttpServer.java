package httpserver;

import httpserver.config.ConfigurationManager;

public class HttpServer {
    public static void main(String[] args) {
        System.out.println("Setting up server configuration...");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
    }
}

package httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

        
        try {
            // socket setup and listening ready to accept
            ServerSocket serverSocket = new ServerSocket(conf.getPort());
            Socket socket = serverSocket.accept();
            
            // For reading input
            InputStream inputStream = socket.getInputStream();
            // For reading output
            OutputStream outputStream = socket.getOutputStream();
        
            // Ready output
            String htmlPage = "<html>"
                                + "<head>"
                                    + "<title>Simpple HTTP Server</title>" 
                                + "</head>"
                                + "<body>"
                                    + "<h1>Content served by HTTP Server</h1>"
                                + "</body>"
                                + "</html>";
            final String cNf ="\r\n"; // Carriage return and feed line
            String response = "HTTP/1.1 200 OK"+ cNf // RESPONSE_MESSAGE
                        + "Content-Length: " + htmlPage.getBytes().length + cNf // HEADER
                        + cNf + htmlPage + cNf + cNf; // CONTENT

            // Write output
            outputStream.write(response.getBytes());

            inputStream.close();
            outputStream.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerListenerThread extends Thread {
    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webroot;
    ServerSocket serverSocket;

    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try (
            // socket setup and listening ready to accept
            Socket socket = serverSocket.accept();
            // For reading input
            InputStream inputStream = socket.getInputStream();
            // For reading output
            OutputStream outputStream = socket.getOutputStream();
        ) {
            
            LOGGER.info(" * Connection accepted: " + socket.getInetAddress());

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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

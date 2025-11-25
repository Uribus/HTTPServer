package httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnectionWorkerThread extends Thread  {
    private Socket socket;
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class); 

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    
    @Override
    public void run() {
        try (
            // For reading input
            InputStream inputStream = socket.getInputStream();
            // For reading output
            OutputStream outputStream = socket.getOutputStream();
        ) {
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


            outputStream.write(response.getBytes());

            LOGGER.info("Finished processing connection.");
        } catch (IOException e) {
            LOGGER.error("There was a problem processing the connection", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {}
        }
    }
}


package surya.project.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import javafx.scene.control.TextArea;
import surya.project.global.Global;

import java.io.*;
import java.net.HttpURLConnection;

import java.nio.file.Files;

import java.net.InetAddress;


public class ServerHandler implements HttpHandler {

    protected String pathDirectory;
    protected String requestDirectory;
    protected String logDirectory;

    protected static TextArea textarea;

    LogHandler loghandler;

    public ServerHandler(TextArea textarea) {
        this.pathDirectory = Global.start;
        this.logDirectory = Global.log;
        this.textarea = textarea;

        loghandler = new LogHandler(textarea);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String reqMethod = exchange.getRequestMethod();

        requestDirectory = exchange.getRequestURI().getPath();
        InetAddress localhost = InetAddress.getLocalHost();
        loghandler.logAccess(localhost.getHostAddress(), requestDirectory);
//        logAccess(localhost.getHostAddress(), requestDirectory);

        if (reqMethod.equals("GET")){
            this.requestGetHandle(exchange);
        }
        else{
            sendErrorResponse(exchange, HttpURLConnection.HTTP_BAD_METHOD, "Method Belum tersedia layananya");
        }
    }

    private void requestGetHandle(HttpExchange exchange) throws IOException {
        File file = new File(pathDirectory + requestDirectory);

        try{
            if(file.isFile() && file.exists()){
                System.out.println("Yang kamu akses adalah file!!!");
                sendFileResponse(exchange, file);
            }

            if(file.isDirectory() && file.exists()) {
                File indexFile = new File(file, "index.html");
                if (indexFile.exists() && indexFile.isFile()) {
                    sendFileResponse(exchange, indexFile);
                }
                else{
                    StringBuilder response = new StringBuilder("<html><body><h1>Files in " + file.getAbsolutePath() + ":</h1><ul>");
                    File[] files = file.listFiles();
                    if (!this.requestDirectory.equals("/")){
                        response.append("<a href=\"../\">...</a></li>");
                    }
                    response.append(Global.dirtag);
                    for (File f : files) {
                        String path = f.isDirectory() ? this.requestDirectory + f.getName() + "/" : this.requestDirectory + f.getName();
                        response.append("<li><a href='").append(path).append("'>").append(f.getName()).append("</a></li>");
                    }
                    response.append("</ul></body></html>");
                    sendResponse(exchange, HttpURLConnection.HTTP_OK, response.toString());
                }
            }
        }catch (Exception e){
            sendErrorResponse(exchange, HttpURLConnection.HTTP_NOT_FOUND, "Error 404 not found");
        }
    }

    private void sendFileResponse(HttpExchange exchange, File indexFile) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, indexFile.length());
        OutputStream outputStream = exchange.getResponseBody();
        Files.copy(indexFile.toPath(), outputStream);
        outputStream.close();
    }

    public void sendResponse(HttpExchange exchange, int status, String response) throws IOException {
        loghandler.logStatus(status);
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.toString().getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.flush();
        os.close();
    }

    public void sendErrorResponse(HttpExchange exchange, int status, String message) throws IOException {
        sendResponse(exchange, status, message);
    }

    private String getContentType(String filePath) {
        if (filePath.endsWith(".html") || filePath.endsWith(".htm")) {
            return "text/html";
        } else if (filePath.endsWith(".css")) {
            return "text/css";
        } else if (filePath.endsWith(".js")) {
            return "application/javascript";
        } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filePath.endsWith(".png")) {
            return "image/png";
        } else if (filePath.endsWith(".gif")) {
            return "image/gif";
        } else if (filePath.endsWith(".pdf")) {
            return "application/pdf";
        } else {
            return "application/octet-stream";
        }
    }
}

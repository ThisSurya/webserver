package surya.project.eb_swsmaven;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ResourceBundle;

import com.sun.net.httpserver.HttpServer;
import javafx.scene.control.TextArea;
import surya.project.global.Global;
import surya.project.handler.ServerHandler;

public class DashboardController implements Initializable {
    @FXML
    private TextArea logarea;
    @FXML
    private Button webserverbutton;
    @FXML
    private Button configbutton;

    private boolean result = false;
    private HttpServer server;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configbutton.getStyleClass().add("configbutton");
        webserverbutton.getStyleClass().add("startbutton");
    }

    @FXML
    protected void startHostServer() {
        if(!result){
            this.startServer();
            webserverbutton.setText("Stop");
            webserverbutton.getStyleClass().add("stopbutton");
        }
        else if(result == true){
            this.stopServer();
            webserverbutton.setText("Start");
            webserverbutton.getStyleClass().add("startbutton");
        }
    }

    private void startServer() {
        logarea.appendText("Start localhost.... \n");

        try {
            webserverbutton.disableProperty().set(true);
            server = HttpServer.create(new InetSocketAddress(Global.port), 0);
            server.createContext("/", new ServerHandler(logarea));
            server.setExecutor(null);
            server.start();
            logarea.appendText("Port localhost opened on " + Global.port + "\n");
            webserverbutton.disableProperty().set(false);

            this.result = true;
        } catch (IOException e) {
            webserverbutton.disableProperty().set(false);

            logarea.appendText(e.getMessage() + "\n");
            this.result = true;
        }

    }

    private void stopServer(){
        try{
            if(this.result == true){
                webserverbutton.disableProperty().set(true);

                server.stop(1);
                webserverbutton.disableProperty().set(false);

                logarea.appendText("Port localhost closed on " + Global.port + "\n");
                this.result = false;
            }
            else{
                webserverbutton.disableProperty().set(false);
                logarea.appendText("Kamu belum menyalakan localhost \n");
            }
        }catch(Exception e){
            logarea.appendText(e.getMessage() + "\n");
        }
    }

    @FXML
    private void configPage() throws IOException {
        new ConfigController().loadPage(new HelloApplication().primarystage);
    }
}
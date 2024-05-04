package surya.project.eb_swsmaven;

import javafx.fxml.FXML;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import surya.project.global.Global;
import surya.project.handler.ConfigHandler;

public class ConfigController implements Initializable {
    @FXML
    private TextField startdirectory;
    @FXML
    private TextField logdirectory;
    @FXML
    private TextField port;
    @FXML
    private Label labelstart;
    @FXML
    private Label labellog;
    @FXML
    private Label errorsavelabel;

    private boolean cansave = true;

    private String savepath;

    private Stage stage;

    public void initialize(URL url, ResourceBundle rb) {
        startdirectory.setText(Global.start);
        logdirectory.setText(Global.log);
        port.setText(String.valueOf(Global.port));
    }

    public void loadPage(Stage primarystage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("configform-view.fxml"));
        this.stage = new Stage();

        this.stage.initOwner(primarystage);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.setResizable(false);
        this.stage.setScene(new Scene(fxmlLoader.load(), 388, 243));
        this.stage.setTitle("Config");

        this.stage.show();
    }

    @FXML
    private void savetoDirectory(){
        if(this.cansave){
            boolean result = new ConfigHandler().saveConf(startdirectory, logdirectory, port);
            if(result){
                errorsavelabel.setVisible(false);
            }
        }
        else {
            errorsavelabel.setText("Tidak bisa menyimpan!");
            errorsavelabel.setVisible(true);
        }
    }

    @FXML
    private void refresh(){
        this.cansave = true;
        labelstart.setVisible(false);
        labellog.setVisible(false);
        errorsavelabel.setVisible(false);
    }

    @FXML
    private void checkStartDirectory(){
        String direktories = startdirectory.getText();
        this.checkEmpty(startdirectory);
        boolean check = new File(direktories).exists();
        this.checkEmpty(startdirectory);
        if(!check){
            System.out.println(direktories);
            labelstart.setText("path tidak tersedia");
            labelstart.setVisible(true);
            this.cansave = false;
        }
    }

    @FXML
    private void portCheck(){
        String text = port.getText();
        this.checkEmpty(port);
        if (!text.matches("[0-9]*")) {
            this.cansave = false;
            port.setText(text.substring(0, text.length() - 1));
        }

        int check = Integer.parseInt(text);
        if(check < 0 || check > 65535){
            this.cansave = false;
            port.setText(text.substring(0, text.length() - 1));
        }
    }

    @FXML
    private void checkLogDirectory(){
        String direktories = logdirectory.getText();
        boolean check = new File(direktories).exists();
        this.checkEmpty(logdirectory);
        if(!check){
            System.out.println(direktories);
            labellog.setText("path tidak tersedia");
            labellog.setVisible(true);
            this.cansave = false;
        }
    }

    public void checkEmpty(TextField field){
        if(field.getText().isEmpty()){
            this.cansave = false;
            errorsavelabel.setText("Semua harus diisi");
            errorsavelabel.setVisible(true);
        }
    }
}

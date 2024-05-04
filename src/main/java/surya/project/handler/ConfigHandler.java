package surya.project.handler;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import surya.project.global.Global;

import java.io.*;

public class ConfigHandler {
    private TextField startdirectory;
    private TextField logdirectory;
    private TextField port;

    private String savepath;

    public void loadConfig(){
        savepath = new File("").getAbsolutePath() + "\\config";
        String filename = "config.txt";
        String filepath = savepath + "\\" + filename;
        if(!new File(filepath).exists()){
            Global.default_value();
            this.makeConf();
        }
        if(new File(filepath).exists()){
            try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
                int x = 0;
                String[] data = new String[5];
                String i;
                while( (i = reader.readLine()) != null){
                    data[x] = i;
                    x++;
                }
                Global.start = data[0];
                Global.log = data[1];
                Global.port = Integer.parseInt(data[2]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean saveConf(TextField startdirectory, TextField logdirectory, TextField port){
        try{
            savepath = new File("").getAbsolutePath() + "\\config";
            String filename = "config.txt";
            String filepath = savepath + "\\" + filename;
            this.writeConf(filepath, startdirectory.getText(), logdirectory.getText(), port.getText());

            Global.start = startdirectory.getText();
            Global.log = logdirectory.getText();
            Global.port = Integer.parseInt(port.getText());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private void writeConf(String filepath, String start, String logdir, String port){
        try(FileWriter writer = new FileWriter(filepath)){
            writer.write(start +"\n");
            writer.write(logdir + "\n");
            writer.write(port + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeConf(){
        savepath = new File("").getAbsolutePath() + "\\config";
        File file = new File(savepath);
        System.out.println(file.getPath());
        if(!file.exists()){
            System.out.println("Make directories config....");
            file.mkdir();
        }
        String filename = "config.txt";
        String filepath = savepath + "\\" + filename;
        this.writeConf(filepath, Global.start, Global.log, String.valueOf(Global.port));
    }


}

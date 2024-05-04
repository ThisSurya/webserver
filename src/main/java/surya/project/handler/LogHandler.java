package surya.project.handler;

import surya.project.global.Global;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.TextArea;


public class LogHandler {
    private TextArea textarea;
    protected String logDirectory;

    public LogHandler(TextArea textarea) {
        this.textarea = textarea;
        this.logDirectory = Global.log;
    }

    public void logStatus(int status){
        String logtext = String.format("[%d] status code", status);
        this.textarea.appendText(logtext + "\n");
    }

    public void logAccess(String ipaddress, String requestpath) throws IOException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String logfilename = dateformat.format(new Date()) + ".log";
        String logpath = this.logDirectory + "\\" + logfilename;
        try{
            makeLog(logpath);
        }catch(Exception e){
            e.printStackTrace();
        }

        String logtext = String.format("[%d] timestamp - [%s] ipaddress - [%s] request path", System.currentTimeMillis(), ipaddress, requestpath);
        this.textarea.appendText(logtext + "\n");
        this.writeLog(logpath, logtext);
    }

    private void makeLog(String logpath) throws IOException {
        File file = new File(logpath);
        File dir = new File(this.logDirectory);
        if(!dir.exists()){
            dir.mkdir();
        }

        if(!file.exists()){
            file.createNewFile();
        }
    }

    private static void writeLog(String logpath, String logtext) {
        try(FileWriter filewriter = new FileWriter(logpath, true)){
            filewriter.write(logtext + "\n");
            filewriter.close();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}

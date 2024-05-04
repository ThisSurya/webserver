package surya.project.global;

import java.io.File;

public class Global {
    public static String start;
    public static String log;
    public static int port = 0 ;

    public static String dirtag = "<img  src=\"" + new File("").getAbsolutePath() + "\\diricon.png \" alt>" ;
    public static String filetag = "<img  src='filecon.png' alt=''>" ;

    public static void default_value(){
        start = new File("").getAbsolutePath();
        log = new File("").getAbsolutePath() + "\\" + "log";
        port = 8081;
    }

    public static String[] data = new String[5];
}

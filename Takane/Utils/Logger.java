package Takane.Utils;

import java.util.ArrayList;

/**
 * Created by Frostbyte on 8/4/16.
 */
public class Logger {
    ArrayList<String> log = new ArrayList<>();
    public void log(String string){
        log.add(string);
        System.out.println("[LOG]"+string);
    }

    public ArrayList<String> getLog() {
        return log;
    }
}

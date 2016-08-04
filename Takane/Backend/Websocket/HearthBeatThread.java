package Takane.Backend.Websocket;

import com.neovisionaries.ws.client.WebSocket;
import org.json.JSONObject;

/**
 * Created by Frostbyte on 8/4/16.
 */
public class HearthBeatThread extends Thread {
    public HearthBeatThread(Long interval, TakaneSocket socket){
        this.interval = interval;
        this.takaneSocket = socket;
    }
    TakaneSocket takaneSocket;
    Long interval;
    @Override
    public void run() {
        while (true){
            beat = null;
            ping();
            try {
                Thread.sleep(interval / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (beat == null){
                takaneSocket.close();
                break;
            }
            try {
                Thread.sleep(interval / 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public synchronized void start() {
        this.setName("HearthBeat");
        this.setDaemon(true);
        this.setPriority(MAX_PRIORITY);
        super.start();
    }

    void ping(){
        takaneSocket.send(new JSONObject().put("op",1).put("d",takaneSocket.getReps()).toString());
    }
    public void pong(){
        beat = System.currentTimeMillis();
    }
    Long beat;
}

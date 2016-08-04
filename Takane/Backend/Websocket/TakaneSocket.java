package Takane.Backend.Websocket;

import Takane.Backend.TakaneAPI;
import Takane.Utils.Logger;
import com.neovisionaries.ws.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Created by Frostbyte on 8/4/16.
 */
public class TakaneSocket extends WebSocketAdapter implements WebSocketListener {

    String Bottoken;
    TakaneAPI takaneAPI;
    WebSocket socket = null;
    public TakaneSocket(TakaneAPI api){
        takaneAPI = api;
        Bottoken = takaneAPI.getToken();
        try {
            socket = new WebSocketFactory().createSocket(new URI("wss://gateway.discord.gg?encoding=json&v=5")).addHeader("Accept-Encoding","gzip").addListener(this);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void connect(){
        try {
            socket.connect();
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }
    Logger logger = new Logger();
    public void send(String request){
        logger.log("[WS] <- "+request);
        socket.sendText(request);
    }
    public void Sendid(){
        JSONObject identify = new JSONObject()
                .put("op", 2)
                .put("d", new JSONObject()
                        .put("token", Bottoken)
                        .put("properties", new JSONObject()
                                .put("$os", System.getProperty("os.name"))
                                .put("$browser", "Takane")
                                .put("$device", "")
                                .put("$referring_domain", "")
                                .put("$referrer", "")
                        )
                        .put("v", 5)
                        .put("large_threshold", 250)
                        .put("compress", true));
        send(identify.toString());
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        Sendid();
    }

    public void close(){

    }

    public int getReps() {
        return reps;
    }

    int reps;
    HearthBeatThread hearthBeatThread;
    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JSONObject jsonObject = new JSONObject(text);
        int op = jsonObject.getInt("op");
        if (jsonObject.has("s") && !jsonObject.isNull("s"))
            reps = jsonObject.getInt("s");
        logger.log("[WS] -> "+jsonObject);
        switch (op){
            case 10:
                hearthBeatThread = new HearthBeatThread(jsonObject.getJSONObject("d").getLong("heartbeat_interval"), this);
                hearthBeatThread.start();
                break;
            case 11:
                hearthBeatThread.pong();
                break;

        }
    }
}

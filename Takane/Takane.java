package Takane;

import Takane.Backend.TakaneAPI;

/**
 * Created by Frostbyte on 8/4/16.
 */
public class Takane {

    String bottoken;
    TakaneAPI takaneAPI = null;

    public Takane(String bottoken){
        this.bottoken = bottoken;
        takaneAPI = new TakaneAPI(this);
    }

    public TakaneAPI getTakaneAPI() {
        return takaneAPI;
    }

    public String getBottoken() {
        return bottoken;
    }
    public void Start(){
        takaneAPI.getTakaneSocket().connect();
    }
}

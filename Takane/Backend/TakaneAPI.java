package Takane.Backend;

import Takane.Backend.Websocket.TakaneSocket;
import Takane.Takane;

/**
 * Created by Frostbyte on 8/4/16.
 */
public class TakaneAPI {

    Takane token = null;
    TakaneSocket takaneSocket;
    public TakaneAPI(Takane token){
        this.token = token;
        this.takaneSocket = new TakaneSocket(this);
    }

    public TakaneSocket getTakaneSocket() {
        return takaneSocket;
    }

    public String getToken() {
        return token.getBottoken();
    }
}

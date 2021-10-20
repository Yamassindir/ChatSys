package chatsystem;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatSystemSend {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Hello hello = new Hello(System.currentTimeMillis(), "fabio", true);
            SendMessage smess = new SendMessage();
            smess.send(hello,InetAddress.getByName("192.168.48.1"));
        } catch (UnknownHostException ex) {
            Logger.getLogger(ChatSystemSend.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;


/**
 * @author Fabio
 */
import java.io.Serializable;

public class Message implements Serializable {
    private long timestamp;
    public Message(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
package chatsystem;


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
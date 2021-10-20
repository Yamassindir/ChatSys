
package chatsystem;

public class File extends Message{
    private String filename;
    private int size;

    public File(long timestamp, String filename, int size) {
        super(timestamp);
        this.filename = filename;
        this.size = size;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    
    
}

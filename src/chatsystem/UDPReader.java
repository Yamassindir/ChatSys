package chatsystem;

import java.io.IOException;
import java.io.Reader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPReader extends Reader{

    private DatagramSocket ds;

    public UDPReader(DatagramSocket ds) {
        this.ds = ds;
    }
    
    
    @Override
    public int read(char[] charData, int offset, int len) throws IOException {
        byte[] data = new byte[len];
        DatagramPacket dp = new DatagramPacket(data, offset, len);
        ds.receive(dp);
        String s = new String(dp.getData());
        char[] arrayChars = s.toCharArray();
        for(int i=0; i<dp.getLength();i++){
            charData[i]= arrayChars[i];
        }
        return dp.getLength();
    }

    @Override
    public void close() throws IOException {
        ds.close();
    }
    
}

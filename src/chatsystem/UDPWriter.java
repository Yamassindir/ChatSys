package chatsystem;

import java.io.IOException;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPWriter extends Writer{
    private DatagramSocket ds;
    private InetAddress toIP;
    private int toPort;

    public UDPWriter(DatagramSocket ds, InetAddress toIP, int toPort) {
        this.ds = ds;
        this.toIP = toIP;
        this.toPort = toPort;
    }
    
    
    
    @Override
    public void write(char[] charData, int offset, int len) throws IOException {
        String s = new String(charData);
        byte[] data = s.getBytes("UTF-8");
        DatagramPacket dp = new DatagramPacket(data, offset, len);
        dp.setAddress(toIP);
        dp.setPort(toPort);
        ds.send(dp);
    }

    @Override
    public void flush() throws IOException {
        
    }

    @Override
    public void close() throws IOException {
        ds.close();
    }
    
}

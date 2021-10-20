package chatsystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SendMessage {
    private Object o;

    public void send(Object o, InetAddress toIP)  {
        try {
            ObjectOutputStream oo;
            // serializing the Message
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            
            try {
                oo = new ObjectOutputStream(bo);
                oo.writeObject(o);
                oo.flush();
                oo.close();
            } catch (IOException ex) {
                System.out.println("Error serializing the object " + ex);
            }
            
            // Sending the datagram
            DatagramPacket dp;
            byte[] data = bo.toByteArray();
            dp = new DatagramPacket(data, data.length);
//            byte[] ipAddr = new byte[]{(byte)192, (byte)168, (byte)48, (byte)1};
            dp.setAddress(toIP);
            dp.setPort(14000);
            DatagramSocket ds = null;
            
            try {
                ds = new DatagramSocket();
            } catch (SocketException ex) {
                System.out.println("Error creating  the socket " + ex);
            }
            
            try {
                System.out.println("Sending UDP message");
                ds.send(dp);
            } catch (IOException ex) {
                System.out.println("Error sending the datagram " + ex);
            }
        } catch (Exception ex) {
            Logger.getLogger(SendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
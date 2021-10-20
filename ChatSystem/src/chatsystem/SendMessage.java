/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Fabio
 */
public class SendMessage {
    private Object o;
    

    public void send(Object o)  {
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
        try {
            dp.setAddress(InetAddress.getByName("255.255.255.255"));
        } catch (UnknownHostException ex) {
            System.out.println("Error getting localhost  " + ex);
        }

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
    }
}
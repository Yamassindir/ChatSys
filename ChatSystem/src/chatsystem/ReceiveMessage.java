/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Fabio
 */
public class ReceiveMessage implements Runnable {

    private Thread t;

    public void receive() {
        t = new Thread(this);
        t.start();

    }

    @Override
    public void run() {
        // Create and wait for the datagram
        DatagramPacket dp = new DatagramPacket(new byte[1000], 1000);
        DatagramSocket ds = null;

        try {
            try {
                ds = new DatagramSocket(14000, InetAddress.getLocalHost());
            } catch (UnknownHostException ex) {
                System.out.println("Error getting localhost " + ex);
            }
        } catch (SocketException ex) {
            System.out.println("Error creating  the socket " + ex);
        }
        while (true) {
            System.out.println("Waiting UDP message");
            try {
                ds.receive(dp);
            } catch (IOException ex) {
                System.out.println("Error reading datagram " + ex);
            }

            // Deserialize the object received within the datagram payload
            ByteArrayInputStream bi = new ByteArrayInputStream(dp.getData());
            ObjectInputStream oi;
            Message m = null;

            try {
                oi = new ObjectInputStream(bi);
                try {
                    m = (Message) oi.readObject();
                    if (m instanceof Hello) {
                        System.out.println("Message received from UDP -> USERNAME : " + ((Hello) m).getUsername());
                    } else if (m instanceof Chat) {
                        System.out.println("Message received from UDP -> CHAT : " + ((Chat) m).getMessage());
                    } else if (m instanceof File) {
                        System.out.println("Message received from UDP -> FILENAME : " + ((File) m).getFilename());
                    } else if (m instanceof Bye) {
                        System.out.println("Message received from UDP -> BYE");
                    }
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error casting the message  " + ex);
                }
            } catch (IOException ex) {
                System.out.println("Error getting the object " + ex);
            }
        }
    }
}

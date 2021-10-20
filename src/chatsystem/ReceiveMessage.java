package chatsystem;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;

/**
 * @version 0.1 : à voir comment implémenter le paramétrage des éventuels
 * paramètres de connexion aux autres interlocuteurs
 */
public class ReceiveMessage implements Runnable {

    private Thread t;

    public void receive() {
        // COnstructeur de ReceiveMessage lance son thread
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        // Création et formation du datagramme UDP
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

            // Déserialization de l'objet reçu dans le payload
            ByteArrayInputStream bi = new ByteArrayInputStream(dp.getData());
            ObjectInputStream oi;
            Message m = null;

            try {
                oi = new ObjectInputStream(bi);
                try {
                    m = (Message) oi.readObject();
                    // Si message reçu est un Hello
                    if (m instanceof Hello) {
//                        if (!((Hello) m).getUsername().equals(ChatList.getUsername())) {
                        System.out.println("Message received from UDP -> USERNAME : " + ((Hello) m).getUsername());
                        if (((Hello) m).isResponseRequired()) {
                            SendMessage smess = new SendMessage();
                            smess.send(new Hello(System.currentTimeMillis(), ChatList.getUsername(), false), dp.getAddress());
                        }
                        ChatList.getMapUsers().put(((Hello) m).getUsername(), dp.getAddress());
                        // AJOUT A LA LISTE
                        DefaultListModel dlm = new DefaultListModel();
                        for (int i = 0; i < ChatList.getListUsers().size(); i++) {
                            dlm.addElement(ChatList.getListUsers().get(i));
                        }
                        ChatList.setListUsers(new ArrayList<String>());
                        ChatList.getListUsers().addAll((Set) ChatList.getMapUsers().keySet());
                        ChatList.getJListUsernames().setModel(dlm);
//                        }
                    }// Sinon si Chat
                    else if (m instanceof Chat) {
                        Comunica c1 = new Comunica(ChatList.getUsername());
                        c1.setVisible(true);
                        System.out.println("Message received from UDP -> CHAT : " + ((Chat) m).getMessage());
                        if (c1.newmess) {
                            ChatList.getListUsersMess().add(ChatList.getKeyByValue(ChatList.getMapUsers(), dp.getAddress()));
                            for (int j = 0; j < ChatList.getListUsers().size(); j++) {
                                if (ChatList.getKeyByValue(ChatList.getMapUsers(), dp.getAddress()).equals(ChatList.getListUsers().get(j))) {
                                    ChatList.getListUsers().remove(j);
                                }
                            }

                            DefaultListModel dlm = new DefaultListModel();
                            for (int i = 0; i < ChatList.getListUsersMess().size(); i++) {
                                dlm.addElement(ChatList.getListUsersMess().get(i));
                            }
                            ChatList.getJListMessage().setModel(dlm);
                            DefaultListModel dlm2 = new DefaultListModel();
                            for (int i = 0; i < ChatList.getListUsers().size(); i++) {
                                dlm2.addElement(ChatList.getListUsers().get(i));
                            }
                            ChatList.getJListUsernames().setModel(dlm2);
                            c1.newmess=false;
                        }
                        c1.getTextRec().setText(c1.getTextRec().getText() + ((Chat) m).getMessage().toString() + "\n");

                    } // Sinon si File
                    else if (m instanceof File) {
                        System.out.println("Message received from UDP -> FILENAME : " + ((File) m).getFilename());
                    } // Sinon si Bye
                    else if (m instanceof Bye) {
                        // On enlève l'user de la liste (map)
                        System.out.println("Message received from UDP -> BYE at " + ((Bye) m).getTimestamp());

                        InetAddress addressToRemove = dp.getAddress();
                        String key = ChatList.getKeyByValue(ChatList.getMapUsers(), addressToRemove);

                        ChatList.getMapUsers().remove(key);
                        ChatList.getListUsers().remove(key);
                        // ChatList.getListUsers() = (List) mapUsers.keySet();

                        // RETRAIT A LA LISTE
                        DefaultListModel dlm = new DefaultListModel();
                        for (int i = 0; i < ChatList.getListUsers().size(); i++) {
                            dlm.addElement(ChatList.getListUsers().get(i));
                        }
                        ChatList.setListUsers(new ArrayList<String>());
                        ChatList.getListUsers().addAll((Set) ChatList.getMapUsers().keySet());
                        ChatList.getJListUsernames().setModel(dlm);
                        SendMessage s = new SendMessage();
                        s.send(System.currentTimeMillis(), InetAddress.getByName("255.255.255.255"));
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

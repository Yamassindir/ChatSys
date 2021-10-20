package chatsystem;

import com.sun.xml.internal.ws.client.SenderException;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Comunica extends JFrame implements WindowListener, Runnable {

    String username;
    boolean newmess=true;
    //déclaration des variables 
    /*bouton pour revevoir le message*/
    private JButton bReceive;
    /*bouton pour envoyer le message*/
    private JButton bSend;
    /*label pour indique zone se saisie des messages recu*/
    private JLabel lmessrec;
    /*label pour indique zone des messages recu*/
    private JLabel lmesssend;
    /*textArea pour lire les messages recu*/
    private JTextArea textRec;
    /*textArea pour saisir les messages a envoyer */
    private JTextArea textTosend;
    private Thread CommunicaThread;

    public void run() {

    }

    //constructeur
    public Comunica(String username) {
        this.username = username;
        initComponents();
        CommunicaThread = new Thread(this);
        CommunicaThread.start();
        this.addWindowListener(this);
    }

    /**
     * Initialisalition de la Fenetre t
     */
    public void initComponents() {
        /*pour que le fenêtre reste active par dessus d'autres fenêtres   */
        this.setAlwaysOnTop(true);
        /*le titre de la fenetre*/
        setTitle("Communication window");
        lmesssend = new JLabel("Message to send : ");
        lmessrec = new JLabel("Received message : ");
        textTosend = new JTextArea(10, 10);
        JScrollPane scrollPaneSend = new JScrollPane(textTosend);
        textRec = new JTextArea(10, 10);
        textRec.setEditable(false);
        JScrollPane scrollPaneReceive = new JScrollPane(textRec);
        bSend = new JButton("Send");
        bReceive = new JButton("Receive");
        bReceive.setVisible(false);
        setSize(500, 300);
        //Trois lignes sur deux colonnes avec GridLayout
        setLayout(new GridLayout(3, 2));
        //On ajoute chaque composant au contentPane
        getContentPane().add(lmesssend);
        getContentPane().add(scrollPaneSend);
        getContentPane().add(bSend);
        getContentPane().add(bReceive);
        getContentPane().add(lmessrec);
        getContentPane().add(scrollPaneReceive);
        // the JFrame is visible now
        setVisible(true);

        bSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    if(newmess){
                    Chat chat = new Chat(System.currentTimeMillis(), textTosend.getText());
                    SendMessage s = new SendMessage();
                    InetAddress address = ChatList.getMapUsers().get(username);
                    System.out.println("On veut dire '" + chat.getMessage() + "'  à " + username + " à l'adresse " + address.getHostAddress());
                    s.send(chat, address);
                    textTosend.setText("");
                    }
                } catch (Exception ex) {

                }
            }
        });

//        bReceive.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed( ActionEvent evt) {
//                try {                  
//                    if(reader.ready()){
//                        textRec.setText(textRec.getText()+ reader.readLine()+"\n");
//                    }
//                    
//                    
//                } catch (Exception ex) {
//                   
//                }
//            }
//        });
    }

    public JTextArea getTextRec() {
        return textRec;
    }

    public void setTextRec(JTextArea textRec) {
        this.textRec = textRec;
    }

    
    
    public JTextArea getTextTosend() {
        return textTosend;
    }

    public void setTextTosend(JTextArea textTosend) {
        this.textTosend = textTosend;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

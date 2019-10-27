/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


/**
 *
 * @author voon.truongvan
 */
public class Chatbot extends JFrame implements ActionListener{
    String uname;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    JTextArea  taMessages;
    JTextField tfInput;
    JButton btnSend,btnExit;
    Socket client;
    
    public Chatbot(String uname,String servername) throws Exception {
        super(uname);  // set title for frame
        this.uname = uname;
        client  = new Socket(servername,3333);
        System.out.println("Connected " + client);
        dataInputStream = new DataInputStream(client.getInputStream());
        dataOutputStream = new DataOutputStream(client.getOutputStream());
        send(uname);
        buildInterface();
        new MessagesThread().start();  // create thread to listen for messages
    }
    
    public void buildInterface() {
        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        taMessages = new JTextArea();
        taMessages.setRows(10);
        taMessages.setColumns(50);
        taMessages.setEditable(false);
        tfInput  = new JTextField(50);
        JScrollPane sp = new JScrollPane(taMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp = new JPanel( new FlowLayout());
        bp.add(tfInput);
        bp.add(btnSend);
        bp.add(btnExit);
        add(bp,"South");
        btnSend.addActionListener(this);
        btnExit.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
    }
    
    private void send(String msg){
        try{
            dataOutputStream.writeUTF(msg);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == btnExit ) {
            send("end");
            System.exit(0);
        } else {
            // send message to server
            send(tfInput.getText());
            tfInput.setText("");
        }
    }
    
    public static void main(String ... args) {
    
        // take username from user
        String name = JOptionPane.showInputDialog(null,"Enter your name :", "Username",
             JOptionPane.PLAIN_MESSAGE);
        String servername = "localhost";  
        try {
            new Chatbot( name ,servername);
        } catch(Exception ex) {
            out.println( "Error --> " + ex.getMessage());
        }
        
    }

    class  MessagesThread extends Thread {
        public void run() {
            String line;
            try {
                while(true) {
                    line = dataInputStream.readUTF();
                    taMessages.append(line + "\n");
                } // end of while
            } catch(Exception ex) {}
        }
    }
    
}

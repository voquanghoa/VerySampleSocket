/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Dell
 */
public class MyServer {

    public static final int PORT = 3333;

    public static List<String> informationUser;
    public static List<String> gender = Arrays.asList("Male","Female");
    public static List<String> color = Arrays.asList("Read","Yellow","Black","green");

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);  
        Socket socket = serverSocket.accept();  
        DataInputStream dataInputStream=new DataInputStream(socket.getInputStream());  
        DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());  
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
        int count=0;
        String getFromClient="",str2="";  
        while(!getFromClient.equals("stop")){  

            getFromClient=dataInputStream.readUTF();
            System.out.println("client says: "+getFromClient);
            dataOutputStream.writeUTF(sendForclinet(getFromClient,count));
            System.out.println("Send for client says: "+sendForclinet(getFromClient,count));
            dataOutputStream.flush();  
        }  
        
        dataInputStream.close();  
        socket.close();  
        serverSocket.close();  
        
    }
    
    public static String sendForclinet(String getFromClient,int numberSMS) {

        String sendForClient="";
        switch(numberSMS) {
            case 0:
                sendForClient = "Please select color you likes.\n";
                if(checkCorrectString(getFromClient,gender)) {
                    for(int i=0;i<color.size();i++){
                       sendForClient+=color.get(i)+"\n";
                    }
                    return sendForClient;
                }
                return "";
            case 1:
                return "";
            case 2:
                return "";
        }
        
        return sendForClient;
    }
    
    public static boolean checkCorrectString(String getFromClient,List<String> list) {
        return list.contains(getFromClient);
    }
}

package sgbd;


import java.net.*;
import java.io.*;
import java.util.LinkedList;

import java.net.*;
import java.io.*;
import sgbd.FileManager;

public class Server {

    public static void main(String[] args)throws Exception{
            /*server config*/
            ServerSocket listener = new ServerSocket(3000);
            System.out.println("Server Running");
            Socket socket = listener.accept();
            System.out.println("Client connection");
            ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
            /*files config*/
            FileManager fm = new FileManager();
            
            boolean done = false;
            while(!done){
                
            byte messageType = -1;
            /*handling the EOF exception*/
            try{
                messageType = dIn.readByte();
            }catch(EOFException e){}   
            
            switch(messageType)
                {
                //case create database
                case 1:
                    String name = dIn.readUTF();
                    fm.CreateDB(name);
                    break;
                //example for objects
                case 2:
                    //Objeto ob2 = (Objeto)dIn.readObject();
                    //System.out.println("Object.x: " + ob2.x+ " Object.y: " +ob2.y);
                    break;
                //example for strings
                case 3:
                    System.out.println("Message C [1]: " + dIn.readUTF());
                    System.out.println("Message C [2]: " + dIn.readUTF());
                    break;
                default:
                    done = true;
                } 
            }

            dIn.close();
	}
}
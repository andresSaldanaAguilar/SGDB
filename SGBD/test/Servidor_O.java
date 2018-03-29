
import java.net.*;
import java.io.*;
import java.util.LinkedList;

import java.net.*;
import java.io.*;
import sgbd.FileManager;

public class Servidor_O {

    public static void main(String[] args)throws Exception{
            /*server config*/
            ServerSocket listener = new ServerSocket(3000);
            Socket socket = listener.accept();
            ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
            
            /*files config*/
            FileManager fm = new FileManager();
            
            boolean done = false;
            while(!done){
            
            byte messageType = dIn.readByte();

            switch(messageType)
                {
                case 1: // Type A
                    String name = dIn.readUTF();
                    break;
                case 2: // Type B
                    Objeto ob2 = (Objeto)dIn.readObject();
                    System.out.println("Object.x: " + ob2.x+ " Object.y: " +ob2.y);
                    break;
                case 3: // Type C
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
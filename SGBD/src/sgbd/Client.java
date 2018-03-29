package sgbd;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class Client {

    //request to create DB
    void createDB(String name) throws Exception{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send first message
        dOut.writeByte(1);
        dOut.writeUTF(name);
        dOut.flush(); 
        //dOut.close();
    }
    
    /*public static void main(String[] args)throws Exception{
            Socket socket = new Socket("localhost",3000);
            ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());

            // Send first message
            dOut.writeByte(1);
            dOut.writeUTF("This is the first type of message.");
            dOut.flush(); // Send off the data

            // Send the second message
            dOut.writeByte(2);
            Objeto obj = new Objeto(5,4);
            dOut.writeObject(obj);
            dOut.flush(); // Send off the data

            // Send the third message
            dOut.writeByte(3);
            dOut.writeUTF("This is the third type of message (Part 1).");
            dOut.writeUTF("This is the third type of message (Part 2).");
            dOut.flush(); // Send off the data

            // Send the exit message
            dOut.writeByte(-1);
            dOut.flush();

            dOut.close();
    }*/
    
    /*public static void main(String[] args)throws Exception{
        Client c= new Client();
        c.createDB("uno");
    }*/
}

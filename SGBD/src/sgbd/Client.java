package sgbd;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Client {

    //request to create DB
    void createDB(String name) throws Exception{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send first the action
        dOut.writeByte(1);
        dOut.writeUTF(name);
        dOut.flush(); 
        dOut.close();
    }
    
    void createTable(ArrayList<String> al) throws Exception{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send the action
        dOut.writeByte(2);
        dOut.writeObject(al);
        dOut.flush(); 
        dOut.close();
    }
    
    //gets all the databases
    ArrayList<String> getDBs() throws Exception{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send the action
        dOut.writeByte(3);
        dOut.flush();
        //get de dbs
        ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        ArrayList<String> databases = (ArrayList<String>) dIn.readObject();
        
        dIn.close();
        dOut.close();
        return databases; 
    }
    
    //gets all the tables from a specified db
    ArrayList<String> getTables(String database) throws Exception{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send the action
        dOut.writeByte(4);
        //send de database name
        dOut.writeUTF(database);
        dOut.flush();
        //get the tables
        ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        ArrayList<String> tables = (ArrayList<String>) dIn.readObject();
        
        dIn.close();
        dOut.close();
        return tables; 
    }
    
    //get all the tables from all the existing dbs
    

    
    public static void main(String[] args)throws Exception{
        Client c= new Client();
        ArrayList<String> dbs = c.getTables("db");
        for(String db: dbs){
            System.out.println(db);
        }
    }
}

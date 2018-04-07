package sgbd;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import static sgbd.DynamicCompiler.readObjects;

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
    
    //request to create DB
    void dropDB(String name) throws Exception{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send first the action
        dOut.writeByte(5);
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
    
    //request to create DB
    void dropTable(String name) throws Exception{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send first the action
        dOut.writeByte(6);
        dOut.writeUTF(name);
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
    
    //enviar algo asi: BD_tabla_elioth,19,59
    void createRegister(String reg) throws IOException{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send the action
        dOut.writeByte(7);
        dOut.writeUTF(reg);
        dOut.flush(); 
        dOut.close();            
    }
    
    //enviar bd_tabla
    ArrayList<Object> getRegisters(String table) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send the action
        dOut.writeByte(8);
        dOut.writeUTF(table);
        dOut.flush(); 
        //get the objects
        ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        ArrayList<Object> objects = (ArrayList<Object>) dIn.readObject();
        
        dIn.close();
        dOut.close();
        return objects; 
    }
    
    //traer lista necesaria para el metodo anterior
    ArrayList<String> showRegisters(String table) throws IOException, ClassNotFoundException{
        Socket socket = new Socket("localhost",3000);
        ObjectOutputStream dOut = new ObjectOutputStream(socket.getOutputStream());
        // Send the action
        dOut.writeByte(9);
        dOut.writeUTF(table);
        dOut.flush(); 
        //get the objects
        ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
        ArrayList<String> regs = (ArrayList<String>) dIn.readObject();
        
        dIn.close();
        dOut.close();
        return regs; 
    }

    
    public static void main(String[] args)throws Exception{
        Client c= new Client();
//        ArrayList<String> l= c.getDBs();
//        for(String db:l){
//            System.out.println(db);
//        }
        //c.createRegister("dos_moe_elioth,19,59");
//        tables working good
        
        //ArrayList<String> dbs = c.getTables("db");
        ArrayList<Object> objs =c.getRegisters("uno_vaso");
        System.out.println(objs.size());
        ArrayList<String> regs =c.showRegisters("uno_vaso");
        System.out.println(regs.size());

        ArrayList<String> li = readObjects(objs,regs);
        for(String l:li){
            System.out.println(l);
        }
    }
}

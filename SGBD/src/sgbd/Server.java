package sgbd;


import java.net.*;
import java.io.*;
import java.util.LinkedList;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.tools.JavaFileObject;
import static sgbd.DynamicCompiler.classBuilder;
import static sgbd.DynamicCompiler.compile;
import static sgbd.DynamicCompiler.getJavaFileObject;
import sgbd.FileManager;

public class Server {

    public static void main(String[] args)throws Exception{
            /*server config*/
            ServerSocket listener = new ServerSocket(3000);
            System.out.println("Server Running");
            FileManager fm = new FileManager();
            
            while(true){
            Socket socket = listener.accept();
            System.out.println("Client connection");
            ObjectInputStream dIn = new ObjectInputStream(socket.getInputStream());
            String name ="";
            //ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
            /*files config*/
            
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
                    name = dIn.readUTF();
                    fm.CreateDB(name);
                    break;
                //case create table
                case 2:
                    ArrayList<String> al = (ArrayList<String>) dIn.readObject();
                    
                    /*creando la clase (tabla)*/
                    String str = classBuilder(al);
                    JavaFileObject file = getJavaFileObject(str,al.get(0));
                    Iterable<? extends JavaFileObject> files = Arrays.asList(file);
                    compile(files);
                    
                    /*creando archivo que guardara los registros*/
                    fm.CreateTable(al);
                        
                    break;
                //sending databases
                case 3:
                    ArrayList<String> databases = fm.getDBs();
                    ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
                    dout.writeObject(databases);
                    dout.flush();
                    System.out.println("Data Sended");
                    break;
                 //sending selected tables
                case 4:
                    String dbname = dIn.readUTF();
                    ArrayList<String> tables = fm.getTables(dbname);
                    ObjectOutputStream dout1 = new ObjectOutputStream(socket.getOutputStream());
                    dout1.writeObject(tables);
                    dout1.flush();
                    System.out.println("Data Sended");
                    break;
                //deletes DB
                case 5:
                    name = dIn.readUTF();
                    fm.deleteDB(name);
                    break;
                //deletes Tables
                case 6:
                    name = dIn.readUTF();
                    fm.deleteTable(name);
                    break;                    
               
                case 7:
                    name = dIn.readUTF();
                    fm.createRegister(name);
                    break;
                /*case 8:
                    name = dIn.readUTF();
                    fm.createRegister(name);
                    break;  */                  
                    
                    
                default:
                    done = true;
                }
            
            }
            //dout.close();
            dIn.close();
        }
    }
}
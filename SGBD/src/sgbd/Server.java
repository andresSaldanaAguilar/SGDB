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
import static sgbd.DynamicCompiler.createObjects;
import static sgbd.DynamicCompiler.getJavaFileObject;
import static sgbd.DynamicCompiler.readObjects;
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
                    System.out.println("Create db action");
                    name = dIn.readUTF();
                    fm.CreateDB(name);
                    break;
                //case create table
                case 2:
                    System.out.println("Create table action");
                    ArrayList<String> al = (ArrayList<String>) dIn.readObject();
                    
                    for(String cad:al){
                        System.out.println("action 4:"+cad);
                    }
                    /*creando la clase (tabla)*/
                    String str = classBuilder(al);
                    JavaFileObject file = getJavaFileObject(str,al.get(0).replace(";",""));
                    Iterable<? extends JavaFileObject> files = Arrays.asList(file);
                    compile(files);
                    
                    /*creando archivo que guardara los registros*/
                    fm.CreateTable(al);
                        
                    break;
                //sending databases
                case 3:
                    System.out.println("Show databases action");
                    ArrayList<String> databases = fm.getDBs();
                    ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
                    dout.writeObject(databases);
                    dout.flush();
                    System.out.println("Data Sended");
                    break;
                 //sending selected tables
                case 4:
                    System.out.println("Show tables action");
                    String dbname = dIn.readUTF();
                    ArrayList<String> tables = fm.getTables(dbname);
                    ObjectOutputStream dout1 = new ObjectOutputStream(socket.getOutputStream());
                    dout1.writeObject(tables);
                    dout1.flush();
                    System.out.println("Data Sended");
                    break;
                //deletes DB
                case 5:
                    System.out.println("Delete database action");
                    name = dIn.readUTF();
                    fm.deleteDB(name);
                    break;
                //deletes Tables
                case 6:
                    System.out.println("Delete table action");
                    name = dIn.readUTF();
                    fm.deleteTable(name);
                    break;                    
                //creates a register on an archive
                case 7:
                    System.out.println("Create register action");
                    name = dIn.readUTF();
                    fm.createRegister(name);
                    break;
                //send all registers from a table;
                case 8:
                    System.out.println("Select * from");
                    name = dIn.readUTF();
                    String arr[] = name.split("_");
                    System.out.println("action 8:"+name);
                    
                    ArrayList<String> regs = fm.showRegisters(arr[0], name);
                    ArrayList<Object> objs = createObjects(regs,name);
                    //readObjects(objs,regs);
                    
                    ObjectOutputStream dout2 = new ObjectOutputStream(socket.getOutputStream());
                    dout2.writeObject(objs);
                    dout2.flush();
                    System.out.println("Object Sended");
                    
                    break;                
                //send attributes from table
                case 9:
                    System.out.println("Send attributes from table");
                    name = dIn.readUTF();
                    String arr1[] = name.split("_");
                    
                    ArrayList<String> regs1 = fm.showRegisters(arr1[0], name);
                    
                    ObjectOutputStream dout3 = new ObjectOutputStream(socket.getOutputStream());
                    dout3.writeObject(regs1);
                    dout3.flush();
                    System.out.println("Object Sended");
                    
                    break;     
                    
                default:
                    done = true;
                }
            
            }
            //dout.close();
            dIn.close();
        }
    }
}
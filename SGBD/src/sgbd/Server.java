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
            ObjectOutputStream dout = new ObjectOutputStream(socket.getOutputStream());
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
                    String name = dIn.readUTF();
                    fm.CreateDB(name);
                    break;
                //case create table
                case 2:
                    ArrayList<String> al = (ArrayList<String>) dIn.readObject();
                                
                    /*creando la clase (tabla)*/
                    String str = classBuilder(al);
                    JavaFileObject file = getJavaFileObject(str,al.get(0));
                    Iterable<? extends JavaFileObject> files = Arrays.asList(file);

                    //Compilando archivo
                    compile(files);

                    //corre la clase (en progreso)
                    //runIt(dt.get(0));
                    break;
                //sending databases
                case 3:
                    ArrayList<String> databases = fm.getDBs();
                    dout.writeObject(databases);
                    dout.flush();
                    dout.close();
                    System.out.println("Data Sended");
                    break;
                 //example the selected tables
                case 4:
                    String dbname = dIn.readUTF();
                    ArrayList<String> tables = fm.getTables(dbname);
                    dout.writeObject(tables);
                    dout.flush();
                    dout.close();
                    System.out.println("Data Sended");
                    break;
                default:
                    done = true;
                }
            
            }
            dIn.close();
        }
    }
}
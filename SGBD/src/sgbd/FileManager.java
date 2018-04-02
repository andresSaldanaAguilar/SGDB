package sgbd;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author andressaldana: esta clase lee los registros en el archivo y retorna una lista de objetos
 * 
 */
public class FileManager {

    public FileManager(){
        
    }

    //create DB like a directory
    boolean CreateDB(String name){       
        File file = new File("./DB/"+name);
        if (!file.exists()) {
            file.mkdir();
            return true;
        }
        else
            return false;
    }
    
    //provides all database names
    ArrayList<String> getDBs(){
        File folder = new File("./DB");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> listOfDirNames = new ArrayList();
        for (int i = 0; i < listOfFiles.length; i++){
            //this ignores the stored data folder on macOS
            if(listOfFiles[i].getName().equals(".DS_Store") || listOfFiles[i].getName().equals(".gitignore")){
                continue;
            }           
            listOfDirNames.add(listOfFiles[i].getName());
        }
        return listOfDirNames;
    }
    
    //creates the table archive
    boolean CreateTable(String database,String name,String[] columns){              
        //this file is just for validation terms
        File provfile = new File("./DB/"+database+"/"+name+".txt");
        if(!provfile.exists()){

        BufferedWriter writer = null;
        try {
            //important: the A letter tell us that is a table, not a register
            File file = new File("./DB/"+database+"/"+"A"+name+".txt");

            writer = new BufferedWriter(new FileWriter(file));
            for(int i = 0; i<columns.length; i++){
                writer.write(columns[i]+"\n");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }            
            return true;
        }
        else
            return false;
    }
    
    //provides all database names
    ArrayList<String> getTables(String database){
        File folder = new File("./build/classes/");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> list = new ArrayList();
        for (int i = 0; i < listOfFiles.length; i++){
            //this ignores the stored data folder on macOS
            if(listOfFiles[i].getName().contains(database)){
                String [] justname = listOfFiles[i].getName().split("\\.");
                list.add(justname[0]);
            }
        }
        return list;
    }
    
    //provides all database names
    String[] getColumns(String database,String table){
        
        if(table.charAt(0) == 'A'){

            BufferedReader br = null;
            String sCurrentLine;
            LinkedList<String> cols= new LinkedList();
            
            try {            
            br = new BufferedReader(new FileReader("./DB/"+database+"/"+table+".txt"));
            while ((sCurrentLine = br.readLine()) != null) {
                String[] arr = sCurrentLine.split("/n");
                //for the first line it'll print
                for(int i= 0; i < arr.length; i++){
                    cols.add(arr[i]); 
                }
            }
            } catch (Exception ex) {            
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            String[] columns = new String[cols.size()];
            for(int i= 0; i< cols.size(); i++){
                columns[i] = cols.get(i);
            }
            return columns;
        }
        else 
            return null;
        
    } 
    
    boolean deleteDB(String dbname){
        File file = new File("./DB/"+dbname);
        if (file.exists()) {
            //deleting db
            file.delete();
            //deleting classes
            File folder = new File("./build/classes/");
            File[] files = folder.listFiles();
            for(File namefile:files){
                if(namefile.getName().contains(dbname)){
                    namefile.delete();
                }
            }
            return true;
        }
        else return false; 
    }
    
    boolean deleteTable(String tablename){
        File folder = new File("./build/classes/");
        File[] files = folder.listFiles();
        for(File namefile:files){
            if(namefile.getName().equals(tablename+".class")){
                namefile.delete();
                return true;
            }
        }
        return false;
    }
    
    
    /*future work: registers*/
 
    /*public static void main(String args[]){
        
        FileManager r= new FileManager();
        r.getTables("dos");
        
    }*/

}

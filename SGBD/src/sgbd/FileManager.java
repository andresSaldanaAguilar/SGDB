package sgbd;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.tools.JavaFileObject;
import static sgbd.DynamicCompiler.classBuilder;
import static sgbd.DynamicCompiler.compile;
import static sgbd.DynamicCompiler.createObjects;
import static sgbd.DynamicCompiler.getJavaFileObject;
import static sgbd.DynamicCompiler.newInstance;
import static sgbd.DynamicCompiler.readObjects;



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
    boolean CreateTable(ArrayList<String> al){              
        //this file is just for validation terms
        String[] arr = al.get(0).split("_");
        String table = arr[1];
        String database = arr[0];         
        File provfile = new File("./DB/"+database+"/"+table+".txt");
        if(!provfile.exists()){

        BufferedWriter writer = null;
        try {
            //creating containing file
            File file = new File("./DB/"+database+"/"+table+".txt");
            file.getParentFile().mkdirs(); 
            file.createNewFile();
            writer = new BufferedWriter(new FileWriter(file));
            //saving the cols on the archive
            String cols = "";
            for(int i = 1; i<al.size(); i++){
                String [] arraux = al.get(i).split(" ");
                
                if(i == al.size()-1){
                    cols += arraux[0]+" "+arraux[1];
                }
                else{
                    cols += arraux[0]+" "+arraux[1]+"_";
                }
            }
            writer.write(cols);
            
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
        File folder = new File("./DB/"+database+"/");
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
    
    boolean createRegister(String reg){
        String arr[] = reg.split("_");
        String dbname = arr[0];
        String tbname = arr[1];
        String values = arr[2];
        String newvalues = values.replace(",", "_");
        
        try(FileWriter fw = new FileWriter("./DB/"+dbname+"/"+tbname+".txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print("\r\n"+newvalues);
            //more code
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        return false;
    }
    
    ArrayList<String> showRegisters(String dbname,String tbname){
        
    ArrayList<String> list = new ArrayList();    
    try (BufferedReader br = new BufferedReader(new FileReader("./DB/"+dbname+"/"+tbname+".txt"))) {

        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            list.add(currentLine);
        }
            
    } catch (IOException e) {
            e.printStackTrace();
    }
        return list;
    
    }

    
 
    public static void main(String args[]){
        
        //crea archivo
        FileManager r= new FileManager();
        ArrayList<String> al = new ArrayList();
        al.add("BD_tabla");
        al.add("nombre String");
        al.add("edad Integer");
        al.add("peso Integer");
        r.CreateTable(al);

        //crea clase
        ArrayList<String>  al1= new ArrayList();
        al1.add("BD_tabla");
        al1.add("String nombre ;");
        al1.add("Integer peso ;");
        al1.add("Integer edad ;");

        String str = classBuilder(al1);
           
        JavaFileObject file = getJavaFileObject(str,al1.get(0));
        Iterable<? extends JavaFileObject> files = Arrays.asList(file);
        
        //2.Compile your files by JavaCompiler
        compile(files);
        
        //crea registro
        //r.createRegister("BD_tabla_elioth,19,59");
                
        //traemos registros
        ArrayList<String> list2 = r.showRegisters("BD","tabla");
        System.out.println("tamaño:"+list2.size());
        
        //creamos objetos
        ArrayList<Object> objs = createObjects(list2,"BD_tabla");
        readObjects(objs,list2);
        
    }

}

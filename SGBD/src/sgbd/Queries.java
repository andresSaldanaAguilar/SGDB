package sgbd;

/*@author kaimorts*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.tools.JavaFileObject;
import static sgbd.DynamicCompiler.classBuilder;
import static sgbd.DynamicCompiler.compile;
import static sgbd.DynamicCompiler.getJavaFileObject;
import static sgbd.DynamicCompiler.runIt;

public class Queries {
    private String KEY_HASHMAP;
    private HashMap DATABASES;
    private Table TABLE;
    private Lexer LEXER;
    private Register REGISTER;
    private Client CL;
    
    public Queries(){
        KEY_HASHMAP = "";
        DATABASES = new HashMap();
        LEXER = new Lexer();
        REGISTER = new Register();
        TABLE = new Table();
        CL = new Client();
    }
    
    /*----------------- QUERIES OF DATABASES --------------------------*/
    public boolean CREATE_DB(String QUERY_CREATE_BD) throws Exception{  /*CREATE A NEW DATABASE*/
        boolean isCreated;
        KEY_HASHMAP = LEXER.getNameDB(QUERY_CREATE_BD);
        if (KEY_HASHMAP!=null) {
            if (DATABASES.containsKey(KEY_HASHMAP)){
                System.out.println("Database "+KEY_HASHMAP+" already exits");
                isCreated = false;
            }else{
                DATABASES.put(KEY_HASHMAP, TABLE);
                isCreated=true;
                CL.createDB(KEY_HASHMAP);
            }
        }else{
            System.out.println("ERROR: DATABASE CAN'T BE NULL");
            isCreated = false;    
        }
        return isCreated;
    }
    
    public void SHOW_DATABASES(){       /*SHOW EXISTING DATABASES*/
      /* Despliega el contenido usando un Iterador*/
      Set set = DATABASES.entrySet();
      Iterator iterator = set.iterator();
      System.out.println("+------------------------+\n"+
                         "| DATABASES              |\n"+
                         "+------------------------+");
      while(iterator.hasNext()) {
        Map.Entry mentry = (Map.Entry)iterator.next();
        System.out.println("|"+ mentry.getKey());
      }
      System.out.println("+------------------------+\n");
    }
    
    public boolean DROP_DATABASE(String QUERY_DROP_BD) throws Exception{  /*DROP A SPECIFIC DATABASE*/
        boolean isCreated;
        KEY_HASHMAP = LEXER.getNameDB(QUERY_DROP_BD);
        System.out.println("key_HashDROP: "+KEY_HASHMAP);
        if (KEY_HASHMAP != null) {
            if (!DATABASES.containsKey(KEY_HASHMAP)) {
                System.out.println("Can't drop database '"+KEY_HASHMAP+"'");
                System.out.println("Database doesn't exist");
                isCreated = false;
            } else {
                DATABASES.remove(KEY_HASHMAP);
                CL.dropDB(KEY_HASHMAP);
                isCreated = true;
            }
        } else {
            System.out.println("ERROR: DATABASE CAN'T BE NULL");
            isCreated = false;
        }
        return isCreated;
    }
    
    /*public HashMap USE_DATABASE(String NAME_BD){
        HashMap USE_BD = new HashMap();
    }*/
    
    public void USE_DATABASE(String QUERY_USE){
        TABLE = new Table();
        String MATCH_BD = "";
        MATCH_BD = LEXER.getBD_ToUse(QUERY_USE);
        TABLE.setNAME_DATABASE(MATCH_BD);
        if (!DATABASES.containsKey(MATCH_BD)) 
            System.out.println("Unkown database '"+TABLE.getNAME_DATABASE()+"'");
        else{
            TABLE.setTABLE((HashMap)DATABASES.get(TABLE.getNAME_DATABASE()));
            System.out.println("Database changed: "+TABLE.getNAME_DATABASE());
        }
    }
    /*------------------------------------------------------------------*/
    
    
    /*---------------------- QUERIES OF TABLES -------------------------*/
    
    public ArrayList<String> CREATE_TABLE(String QUERY_CREATE_TB) throws Exception{
        System.out.println("CREATE TABLE INSIDE "+TABLE.getNAME_DATABASE());
        String KEY_HASHMAP_TB="";
        KEY_HASHMAP_TB = LEXER.getNameTable(QUERY_CREATE_TB);
        System.out.println("KEY_HASHMAP_TB"+KEY_HASHMAP_TB);
        
        TABLE.setKEY_HASHMAP_TABLE( KEY_HASHMAP_TB );
        if (TABLE.getKEY_HASHMAP_TABLE()!=null) {
            if (TABLE.containsKey(TABLE.getKEY_HASHMAP_TABLE()))
                System.out.println("Table "+TABLE.getKEY_HASHMAP_TABLE()
                                       +" already exists inside "+TABLE.getNAME_DATABASE());
            else
                TABLE.getTABLE().put(TABLE.getKEY_HASHMAP_TABLE(), REGISTER);
            
            ArrayList<String> dt = LEXER.getDataTable(QUERY_CREATE_TB);
            dt.set(0, TABLE.getNAME_DATABASE()+dt.get(0));
            CL.createTable(dt);
            return dt;         /*Regresa un ArrayList con los atributos de la tabla*/
        }else{
            System.out.println("ERROR: TABLE CAN'T BE NULL");
            return null;
        }
    }
    
    public boolean isCreatedTable(Object verification){ 
        return verification!=null;
    }
    
    public void SHOW_TABLES(){      /*SHOW EXISTING TABLES INSIDE OF A DATABASE*/
        /* Despliega el contenido usando un Iterador*/
        Set set = TABLE.getTABLE().entrySet();
        Iterator iterator = set.iterator();
        System.out.println("+------------------------+\n"+
                           "| TABLE                  |\n"+
                           "+------------------------+");
        while(iterator.hasNext()) {
            Map.Entry mentry = (Map.Entry)iterator.next();
            System.out.println("|"+ mentry.getKey());
        }
        System.out.println("+------------------------+\n");
    }
    /*------------------------------------------------------------------*/
    
    
    /*------------------------ROOT------------------------*/
    public void fill_DataBases(String SQL_QUERY_ROOT){
        ArrayList<String> old_databases;
        try {
            old_databases = CL.getDBs();
            for (int i = 0; i < old_databases.size(); i++) 
                CREATE_DB("CREATE DATABASE "+old_databases.get(i));
            
            for (int i = 0; i < old_databases.size(); i++) {
                fill_Tables_ByDB(old_databases.get(i));
            }
            
            SHOW_DATABASES();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void fill_Tables_ByDB(String name_BD){
        ArrayList<String> old_Tables;
        try {
            old_Tables = CL.getTables(name_BD);
            for (int i = 0; i < old_Tables.size(); i++) {
                System.out.println(old_Tables.get(i));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    /*----------------------------------------------------*/
}

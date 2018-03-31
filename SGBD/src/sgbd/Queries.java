package sgbd;

/*@author kaimorts*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Queries {
    private String KEY_HASHMAP;
    private HashMap DATABASES;
    private Table TABLE;
    private Lexer LEXER;
    private Register REGISTER;
    
    public Queries(){
        KEY_HASHMAP = "";
        DATABASES = new HashMap();
        LEXER = new Lexer();
        REGISTER = new Register();
        TABLE = new Table();
    }
    
    /*----------------- QUERIES OF DATABASES --------------------------*/
    public boolean CREATE_DB(String QUERY_CREATE_BD){  /*CREATE A NEW DATABASE*/
        boolean isCreated;
        KEY_HASHMAP = LEXER.getNameDB(QUERY_CREATE_BD);
        if (KEY_HASHMAP!=null) {
            if (DATABASES.containsKey(KEY_HASHMAP)){
                System.out.println("Database "+KEY_HASHMAP+" already exits");
                isCreated = false;
            }else{
                DATABASES.put(KEY_HASHMAP, TABLE);
                isCreated=true;
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
    
    public void DROP_DATABASE(String NAME_BD){  /*DROP A SPECIFIC DATABASE*/
        DATABASES.remove(NAME_BD);
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
    
    public ArrayList<String> CREATE_TABLE(String QUERY_CREATE_TB){
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
            
            return LEXER.getDataTable(QUERY_CREATE_TB);         /*Regresa un ArrayList con los atributos de la tabla*/
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
}

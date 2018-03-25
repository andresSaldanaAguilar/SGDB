package sgbd;

/*@author kaimorts*/
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Queries {
    private String KEY_HASHMAP;
    private HashMap DATABASES = new HashMap();;
    private Table TABLE = new Table();
    
    public Queries(){
        KEY_HASHMAP = "";
    }
    
    public void CREATE_DB(String NAME_DB){  /*CREATE A NEW DATABASE*/
        KEY_HASHMAP = NAME_DB;
        if (DATABASES.containsKey(KEY_HASHMAP)) 
            System.out.println("Database "+KEY_HASHMAP+" already exits");
        else
            DATABASES.put(KEY_HASHMAP, TABLE);
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
    
    public void DROP_DATABASE(String NAME_DB){  /*DROP A SPECIFIC DATABASE*/
        DATABASES.remove(NAME_DB);
    }
    
    /*public HashMap USE_DATABASE(String NAME_BD){
        HashMap USE_BD = new HashMap();
    }*/
}

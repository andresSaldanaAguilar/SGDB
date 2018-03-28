package sgbd;

/*@author kaimorts */
import java.util.HashMap;

public class Table extends HashMap<String,Register>{
    private HashMap TABLE;
    private String NAME_DATABASE;
    private String KEY_HASHMAP_TABLE;
    
    public Table(){}
    
    public Table(String NAME_DATABASE){
        this.TABLE = new HashMap();
        this.NAME_DATABASE = NAME_DATABASE;
        this.KEY_HASHMAP_TABLE = "";
    }

    public HashMap getTABLE() {
        return TABLE;
    }

    public void setTABLE(HashMap TABLE) {
        this.TABLE = TABLE;
    }

    public String getNAME_DATABASE() {
        return NAME_DATABASE;
    }

    public void setNAME_DATABASE(String NAME_DATABASE) {
        this.NAME_DATABASE = NAME_DATABASE;
    }

    public String getKEY_HASHMAP_TABLE() {
        return KEY_HASHMAP_TABLE;
    }

    public void setKEY_HASHMAP_TABLE(String KEY_HASHMAP_TABLE) {
        this.KEY_HASHMAP_TABLE = KEY_HASHMAP_TABLE;
    }
}

package sgbd;

/*@author kaimorts*/
import java.util.HashMap;

public class Table extends HashMap<String,Register>{
    private String KEY_TABLE;
    private String NAME_TABLE;
    private String NAME_DATABASE;
    
    public Table(){}
    
    public Table(String KEY_TABLE, String NAME_TABLE,
                 String NAME_DATABASE){
        this.KEY_TABLE = KEY_TABLE;
        this.NAME_TABLE = NAME_TABLE;
        this.NAME_DATABASE = NAME_DATABASE;
    }

    public String getKEY_TABLE() {
        return KEY_TABLE;
    }

    public void setKEY_TABLE(String KEY_TABLE) {
        this.KEY_TABLE = KEY_TABLE;
    }

    public String getNAME_TABLE() {
        return NAME_TABLE;
    }

    public void setNAME_TABLE(String NAME_TABLE) {
        this.NAME_TABLE = NAME_TABLE;
    }

    public String getNAME_DATABASE() {
        return NAME_DATABASE;
    }

    public void setNAME_DATABASE(String NAME_DATABASE) {
        this.NAME_DATABASE = NAME_DATABASE;
    }
}


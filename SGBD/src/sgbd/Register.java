package sgbd;

/*@author kaimorts*/
import java.util.ArrayList;
import java.util.LinkedList;

public class Register extends ArrayList<String>{
    private String NAME_TABLE;
    private ArrayList<String> Registros = new ArrayList<>();

    public Register() {
    }

    public String getNAME_TABLE() {
        return NAME_TABLE;
    }

    public void setNAME_TABLE(String NAME_TABLE) {
        this.NAME_TABLE = NAME_TABLE;
    }

    public ArrayList<String> getRegistros() {
        return Registros;
    }

    public void setRegistros(ArrayList<String> Registros) {
        this.Registros = Registros;
    }
    
    
}

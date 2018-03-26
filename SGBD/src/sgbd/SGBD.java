package sgbd;

/*@authores andressaldana ft Kaimorts*/

import java.util.Scanner;
import sgbd.Auxiliar.*;


public class SGBD {
    public static String name_BD="";
    public static String name_TB="";
    
    public static void main(String[] args) {
        Lexer a = new Lexer();
        Queries dc = new Queries();
        boolean continua=true;
        String query="";
        Scanner teclado = new Scanner(System.in);
        do {
            System.out.print("|-> ");
            query="";
            query = teclado.nextLine();
            if (query.equals("exit") || query.equals("EXIT")){
                continua = false; //break;
            }
            if (query.contains("CREATE") || query.contains("create")) {
                
                if (query.contains("table")||query.contains("TABLE")) {
                    dc.CREATE_TABLE(query);
                }else if(query.contains("DATABASE") || query.contains("DATABASE")){
                    dc.CREATE_DB(query);
                    dc.SHOW_DATABASES();
                }
            }    
            if (query.contains("USE") || query.contains("use")) {
                dc.USE_DATABASE(query);
            }
            
            if (query.contains("SHOW") || query.contains("show")) {
                if (query.contains("TABLES")) {
                    dc.SHOW_TABLES();
                }else if(query.contains("DATABASES")){
                    dc.SHOW_DATABASES();
                }
            }
        } while (continua);
    }
}

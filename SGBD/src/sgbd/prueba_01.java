package sgbd;

import java.util.Scanner;
import sgbd.Auxiliar.*;
/*@author Kaimorts*/
public class prueba_01 {
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
            query = teclado.nextLine();
            if (query.equals("exit") || query.equals("EXIT")){
                continua = false; break;
            }
            if (query.contains("DATABASE") || 
                query.contains("database")) {
                name_BD = a.getNameDB(query);
                dc.CREATE_DB(name_BD);
                dc.SHOW_DATABASES();
            }
        } while (continua);
        
    }
    
}

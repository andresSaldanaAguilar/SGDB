package sgbd;

import java.util.Scanner;
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
            }else{
                if(query.contains("CREATE")|| query.contains("create")){
                    if(query.contains("DATABASE") || query.contains("database")){
                        dc.CREATE_DB(query);
                    }
                }else if(query.contains("SHOW") || query.contains("show")){
                    if (query.contains("DATABASES") || 
                        query.contains("databases")) {
                        dc.SHOW_DATABASES();
                    }
                }
            }
            
           /* if (query.contains("DATABASE") || 
                query.contains("database")) {
                name_BD = a.getNameDB(query);
                dc.CREATE_DB(name_BD);
                dc.SHOW_DATABASES();
            }*/
        } while (continua);
        
    }
    
}

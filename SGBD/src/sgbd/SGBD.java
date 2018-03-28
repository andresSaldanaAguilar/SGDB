package sgbd;

/*@authores andressaldana ft Kaimorts*/

import java.util.Scanner;

public class SGBD {   
    
    public static void main(String[] args) {
        Lexer a = new Lexer();
        Queries qr = new Queries();
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
                    qr.CREATE_TABLE(query);
                }else if(query.contains("database") || query.contains("DATABASE")){
                    qr.CREATE_DB(query);
                    qr.SHOW_DATABASES();
                }
            }    
            if (query.contains("USE") || query.contains("use")) {
                qr.USE_DATABASE(query);
            }
            
            if (query.contains("SHOW") || query.contains("show")) {
                if (query.contains("TABLES")) {
                    qr.SHOW_TABLES();
                }else if(query.contains("DATABASES")){
                    qr.SHOW_DATABASES();
                }
            }
        } while (continua);
    }
}

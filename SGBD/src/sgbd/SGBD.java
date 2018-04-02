package sgbd;

import java.util.ArrayList;
import java.util.Scanner;

/*@author Kaimorts*/
public class SGBD {

    public static String name_BD = "";
    public static String name_TB = "";

    public static void main(String[] args) throws Exception {
        Lexer a = new Lexer();
        Queries dc = new Queries();
        boolean continua = true;
        String query = "";
        Scanner teclado = new Scanner(System.in);

        System.out.print("|-> ");
        query = "";
        query = teclado.nextLine();

        if (query.equals("root")) {
            dc.fill_DataBases(query);
            
            do {
                System.out.print("|-> ");
                query = "";
                query = teclado.nextLine();
                if (query.equals("exit") || query.equals("EXIT")) {
                    continua = false; //break;
                }
                if (query.contains("CREATE") || query.contains("create")) {
                    if (query.contains("table") || query.contains("TABLE")) {
                        if (dc.isCreatedTable(dc.CREATE_TABLE(query))) {
                            ArrayList<String> data = dc.CREATE_TABLE(query);
                        } else {
                            System.out.println("Error");
                        }
                    } else if (query.contains("DATABASE") || query.contains("database")) {
                        if (dc.CREATE_DB(query)) {
                            dc.SHOW_DATABASES();
                        }
                    }
                }
                if (query.contains("USE") || query.contains("use")) {
                    dc.USE_DATABASE(query);
                }

                if (query.contains("SHOW") || query.contains("show")) {
                    if (query.contains("TABLES") || query.contains("tables")) {
                        dc.SHOW_TABLES();
                    } else if (query.contains("DATABASES") || query.contains("databases")) {
                        dc.SHOW_DATABASES();
                    }
                }
                if (query.contains("DROP") || query.contains("drop")) {
                    if (query.contains("DATABASE") || query.contains("database")) {
                        if (dc.DROP_DATABASE(query)) {
                            dc.SHOW_DATABASES();
                        }
                    }
                }
            } while (continua);
        } else {
            System.exit(0);
        }
    }
}

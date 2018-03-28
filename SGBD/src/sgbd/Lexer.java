package sgbd;
/*@author kaimorts*/
import java.util.StringTokenizer;

public class Lexer {   
    
    private String[] CREATE_BD = {"CREATE DATABASE ", "create database "};
    private String[] CREATE_TB = {"CREATE TABLE ", "create table "};
    private String[] USE_BD    = {"USE DATABASE ","use database "};
    
    public String getNameDB(String SQL_QUERY){
        String name_BD="";
        String segment[];
        
        if (SQL_QUERY.contains(CREATE_BD[0])) {
            segment = SQL_QUERY.split(CREATE_BD[0]);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token = new StringTokenizer(name_BD,";");
                name_BD = token.nextToken();
            }
        }else if(SQL_QUERY.contains(CREATE_BD[1])){
            segment = SQL_QUERY.split(CREATE_BD[1]);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token=new StringTokenizer(name_BD,";");
                name_BD = token.nextToken();
            }
        }else{
            System.out.println("Syntaxis error");
            name_BD = null;
        }
        return name_BD;
    }
    
    public String getNameTable(String SQL_QUERY){
        String name_Table="";
        String segment[];
        
        if (SQL_QUERY.contains(CREATE_TB[0])) {
            segment = SQL_QUERY.split(CREATE_TB[0]);
            name_Table = segment[1];
            if (name_Table.contains(";")) {
                StringTokenizer token=new StringTokenizer(name_Table,";");
                name_Table = token.nextToken();
            }
        }else if(SQL_QUERY.contains(CREATE_TB[1])){
            segment = SQL_QUERY.split(CREATE_TB[1]);
            name_Table = segment[1];
            if (name_Table.contains(";")) {
                StringTokenizer token=new StringTokenizer(name_Table,";");
                name_Table = token.nextToken();
            }
        }else{
            System.out.println("Syntaxis error");
            name_Table = null;
        }
        return name_Table;
    }
    
    public String getBD_ToUse(String SQL_QUERY){
        String BD_toUse = "";
        String segment[];
        if (SQL_QUERY.contains(USE_BD[0])) {  /*QUERY IN UPPER-CASE*/
            segment = SQL_QUERY.split(USE_BD[0]);
            BD_toUse = segment[1];
            if (BD_toUse.contains(";")) {
                StringTokenizer token = new StringTokenizer(BD_toUse,";");
                BD_toUse = token.nextToken();
            }
        }else if(SQL_QUERY.contains(USE_BD[1])){                      
            segment = SQL_QUERY.split(USE_BD[1]);
            BD_toUse = segment[1];
            if (BD_toUse.contains(";")) {
                StringTokenizer token = new StringTokenizer(BD_toUse,";");
                BD_toUse = token.nextToken();
            }
        }else{
            System.out.println("Syntaxis error");
            BD_toUse = null;
        }
        return BD_toUse;
    }
    
    
}

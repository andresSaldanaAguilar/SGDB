package sgbd;

import java.util.StringTokenizer;

/*@author kaimo*/
public class Lexer {
    private String CDB_MAYUS = "CREATE DATABASE ";
    private String CDB_minus = "create database ";
    private String CTB_MAYUS = "CREATE TABLE ";
    private String CTB_minus = "create table ";
    
    public String getNameDB(String SQL_QUERY){
        String name_BD="";
        String segment[];
        
        if (SQL_QUERY.contains(CDB_MAYUS)) {
            segment = SQL_QUERY.split(CDB_MAYUS);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token=new StringTokenizer(name_BD,";");
                name_BD = token.nextToken();
            }
            System.out.println("Name BD: "+name_BD);
        }else if(SQL_QUERY.contains(CDB_minus)){
            segment = SQL_QUERY.split(CDB_minus);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token=new StringTokenizer(name_BD,";");
                name_BD = token.nextToken();
            }
            System.out.println("Name BD: "+name_BD);
        }else{
            System.out.println("Syntaxis error");
            name_BD = null;
        }
        return name_BD;
    }
    
    public String getNameTable(String SQL_QUERY){
        String name_Table="";
        String segment[];
        
        if (SQL_QUERY.contains(CDB_MAYUS)) {
            segment = SQL_QUERY.split(CDB_MAYUS);
            name_Table = segment[1];
            if (name_Table.contains(";")) {
                StringTokenizer token=new StringTokenizer(name_Table,";");
                name_Table = token.nextToken();
            }
            System.out.println("Name Table: "+name_Table);
        }else if(SQL_QUERY.contains(CDB_minus)){
            segment = SQL_QUERY.split(CDB_minus);
            name_Table = segment[1];
            if (name_Table.contains(";")) {
                StringTokenizer token=new StringTokenizer(name_Table,";");
                name_Table = token.nextToken();
            }
            System.out.println("Name BD: "+name_Table);
        }else{
            System.out.println("Syntaxis error");
            name_Table = null;
        }
        return name_Table;
    }
}

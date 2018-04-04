package sgbd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/*@author kaimorts*/
public class Lexer {

    private static final short LENGTH_BOOLEAN = 7;
    private static final short LENGTH_INTEGER = 7;
    private static final short LENGTH_STRING = 6;
    private static final short LENGTH_DOUBLE = 6;
    private static final short LENGTH_SHORT = 5;
    private static final short LENGTH_FLOAT = 5;
    private static final short LENGTH_LONG = 4;
    
    private final String[] CREATE_BD = {"CREATE DATABASE ", "create database "};
    private final String[] CREATE_TB = {"CREATE TABLE ", "create table "};
    private final String[] USE_BD = {"USE DATABASE ", "use database "};
    private final String[] DROP_BD = {"DROP DATABASE ","drop database "};
    private final String[] INSERT_TB = {"INSERT INTO ","insert into "};
    private final String[] SELECT_FROM = {"SELECT * FROM ","select * from "};
    
    public String getNameDB(String SQL_QUERY) {
        String name_BD = "";
        String segment[];

        if (SQL_QUERY.contains(CREATE_BD[0])) {
            segment = SQL_QUERY.split(CREATE_BD[0]);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token = new StringTokenizer(name_BD, ";");
                name_BD = token.nextToken();
            }
        } else if (SQL_QUERY.contains(CREATE_BD[1])) {
            segment = SQL_QUERY.split(CREATE_BD[1]);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token = new StringTokenizer(name_BD, ";");
                name_BD = token.nextToken();
            }
        } else if (SQL_QUERY.contains(DROP_BD[0])) {
            segment = SQL_QUERY.split(DROP_BD[0]);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token = new StringTokenizer(name_BD, ";");
                name_BD = token.nextToken();
            }
        } else if (SQL_QUERY.contains(DROP_BD[1])) {
            segment = SQL_QUERY.split(DROP_BD[1]);
            name_BD = segment[1];
            if (name_BD.contains(";")) {
                StringTokenizer token = new StringTokenizer(name_BD, ";");
                name_BD = token.nextToken();
            }
        }else {
            System.out.println("Syntaxis error");
            name_BD = null;
        }
        return name_BD;
    }

    public String getNameTable(String SQL_QUERY) {
        String name_Table = "";
        String segment[];
        System.out.println("SQL->GETTABLA: "+SQL_QUERY);
        
        
        if (SQL_QUERY.contains(CREATE_TB[0])) {
            segment = SQL_QUERY.split(CREATE_TB[0]);
            name_Table = segment[1];
            if (name_Table.contains("(")) {
                StringTokenizer token = new StringTokenizer(name_Table, "(");
                name_Table = token.nextToken();
            }
        } else if (SQL_QUERY.contains(CREATE_TB[1])) {
            segment = SQL_QUERY.split(CREATE_TB[1]);
            name_Table = segment[1];
            if (name_Table.contains("(")) {
                StringTokenizer token = new StringTokenizer(name_Table, "(");
                name_Table = token.nextToken();
            }
        } else if (SQL_QUERY.contains(INSERT_TB[1])) {
            segment = SQL_QUERY.split(INSERT_TB[1]);
            name_Table = segment[1];
            if (name_Table.contains("VALUES")) {
                StringTokenizer token = new StringTokenizer(name_Table, "VALUES");
                name_Table = token.nextToken();
            }
        }else if (SQL_QUERY.contains(INSERT_TB[0])) {
            segment = SQL_QUERY.split(INSERT_TB[0]);
            name_Table = segment[1];
            if (name_Table.contains("VALUES")) {
                StringTokenizer token = new StringTokenizer(name_Table, "VALUES");
                name_Table = token.nextToken();
            }
        }else if (SQL_QUERY.contains(SELECT_FROM[0])) {
            segment = SQL_QUERY.split(SELECT_FROM[0]);
            name_Table = segment[1];
            if (name_Table.contains(";")) {
                StringTokenizer token = new StringTokenizer(name_Table, ";");
                name_Table = token.nextToken();
            }
        }else if (SQL_QUERY.contains(SELECT_FROM[1])) {
            segment = SQL_QUERY.split(SELECT_FROM[1]);
            name_Table = segment[1];
            if (name_Table.contains(";")) {
                StringTokenizer token = new StringTokenizer(name_Table, ";");
                name_Table = token.nextToken();
            }
        }else {
            System.out.println("Syntaxis error");
            name_Table = null;
        }
        return name_Table;
    }

    public String getBD_ToUse(String SQL_QUERY) {
        String BD_toUse = "";
        String segment[];
        if (SQL_QUERY.contains(USE_BD[0])) {
            /*QUERY IN UPPER-CASE*/
            segment = SQL_QUERY.split(USE_BD[0]);
            BD_toUse = segment[1];
            if (BD_toUse.contains(";")) {
                StringTokenizer token = new StringTokenizer(BD_toUse, ";");
                BD_toUse = token.nextToken();
            }
        } else if (SQL_QUERY.contains(USE_BD[1])) {
            segment = SQL_QUERY.split(USE_BD[1]);
            BD_toUse = segment[1];
            if (BD_toUse.contains(";")) {
                StringTokenizer token = new StringTokenizer(BD_toUse, ";");
                BD_toUse = token.nextToken();
            }
        } else {
            System.out.println("Syntaxis error");
            BD_toUse = null;
        }
        return BD_toUse;
    }

    public ArrayList<String> getDataTable(String SQL_QUERY) {
        List<String> data;          /*Lista de datos a mandar*/
        String[] segment;           /*Segmentos de la consulta*/
        String sentence = "", flag = "";    /*sentencia de query y bandera para formato*/

        /*QUITA LA SENTENCIA GENERADORA DE TABLA: 'CREATE TABLE'*/
        if (SQL_QUERY.contains(CREATE_TB[0])) {
            segment = SQL_QUERY.split(CREATE_TB[0]);
            sentence = segment[1];
        } else if (SQL_QUERY.contains(CREATE_TB[1])) {
            segment = SQL_QUERY.split(CREATE_TB[1]);
            sentence = segment[1];
        } else {
            System.out.println("Syntaxis error");
            sentence = null;
        }
        /*-----------------------------------------------------*/
        
        if (sentence != null) {
            flag = getNameTable(SQL_QUERY) + "(";
            try {
                sentence = sentence.replace(flag, "");   /*Obtiene los datos de la tabla*/
            } catch (Exception e) {
                System.err.println(e.getCause());
            }

            segment = null;
            flag = "";
            segment = sentence.split("(\\)|\\(|;)");    /*Separa los datos de la tabla si encuentra: ';' , ')' , ')', ','
                                                        y los asigna al segmento reservado*/
            sentence = "";
            for (int i = 0; i < segment.length; i++) {
                if (!isNumeric(segment[i])) {
                    sentence = sentence + segment[i];       /*Quita/Omite el formato de varchar(num)*/
                }
            }
            segment = null;
            segment = sentence.split(",");
            
            data = Arrays.asList(segment);  /*Asigna los elementos del segmento a la lista*/

            /*--CASTEO DE UN OBJETO LIST A UN ARRAYLIST--*/
            ArrayList<String> listOfData = new ArrayList<>(data.size());
            listOfData.addAll(data);
            /*-------------------------------------------*/

            listOfData.add(0, getNameTable(SQL_QUERY));        /*Siempre el primer elemento será el nombre de la tabla*/            
            listOfData = convertData(listOfData);            /*Convierte los tipos de datos para ser interpretados por JAVA*/         
            listOfData = correctFormat(listOfData);            /*Establece el formato typo_de_dato,nombre_de_dato y se guardan*/            
            
            return listOfData;
        }else{
            System.err.println("Syntaxis Error");
            return null;
        }
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static ArrayList<String> convertData(ArrayList<String> listOfData) {
        String flag = "";
        for (int i = 1; i < listOfData.size(); i++) {
            if (listOfData.get(i).contains("varchar")) {                /*Verifica VARCHAR/varchar*/
                flag = listOfData.get(i).replace("varchar", "String");  
            } else if (listOfData.get(i).contains("VARCHAR")) {
                flag = listOfData.get(i).replace("VARCHAR", "String");
            } else if (listOfData.get(i).contains("FLOAT")) {           /*Verifica FLOAT/float*/
                flag = listOfData.get(i).replace("FLOAT", "Float");
            } else if (listOfData.get(i).contains("float")) {
                flag = listOfData.get(i).replace("float", "Float");
            } else if (listOfData.get(i).contains("INT")) {             /*Verifica INT/int*/
                flag = listOfData.get(i).replace("INT", "Integer");
            } else if (listOfData.get(i).contains("int")) {
                flag = listOfData.get(i).replace("int", "Integer");
            } else if (listOfData.get(i).contains("SHORT")) {            /*Verifica SHORT/short*/
                flag = listOfData.get(i).replace("SHORT", "Short");
            } else if (listOfData.get(i).contains("short")) {
                flag = listOfData.get(i).replace("short", "Short");
            } else if (listOfData.get(i).contains("LONG")) {             /*Verifica LONG/long*/
                flag = listOfData.get(i).replace("LONG", "Long");
            } else if (listOfData.get(i).contains("long")) {            
                flag = listOfData.get(i).replace("long", "Long");
            } else if (listOfData.get(i).contains("DOUBLE")) {          /*Verifica DOUBLE/double*/
                flag = listOfData.get(i).replace("DOUBLE", "Double");
            } else if (listOfData.get(i).contains("double")) {
                flag = listOfData.get(i).replace("double", "Double");
            } else if (listOfData.get(i).contains("BOOL")) {            /*Verifica BOOL/bool*/
                flag = listOfData.get(i).replace("BOOL", "Boolean");
            } else if (listOfData.get(i).contains("bool")) {
                flag = listOfData.get(i).replace("bool", "Boolean");
            }
            listOfData.set(i, flag);
            flag = "";
        }
        return listOfData;
    }
    
    private static ArrayList<String> correctFormat(ArrayList<String> listOfData) {
        String TypeDataPrim = "";
        String NameDataPrim = "";
        for (int i = 1; i < listOfData.size(); i++) {
            int ln = listOfData.get(i).length();
            if (listOfData.get(i).contains("String")) {
                TypeDataPrim = listOfData.get(i).substring(ln - LENGTH_STRING);
                NameDataPrim = listOfData.get(i).substring(0, ln - LENGTH_STRING);
            } else if (listOfData.get(i).contains("Integer")) {
                TypeDataPrim = listOfData.get(i).substring(ln - LENGTH_INTEGER);
                NameDataPrim = listOfData.get(i).substring(0, ln - LENGTH_INTEGER);
            } else if (listOfData.get(i).contains("Short")) {
                TypeDataPrim = listOfData.get(i).substring(ln - LENGTH_SHORT);
                NameDataPrim = listOfData.get(i).substring(0, ln - LENGTH_SHORT);
            } else if (listOfData.get(i).contains("Boolean")) {
                TypeDataPrim = listOfData.get(i).substring(ln - LENGTH_BOOLEAN);
                NameDataPrim = listOfData.get(i).substring(0, ln - LENGTH_BOOLEAN);
            } else if (listOfData.get(i).contains("Double")) {
                TypeDataPrim = listOfData.get(i).substring(ln - LENGTH_DOUBLE);
                NameDataPrim = listOfData.get(i).substring(0, ln - LENGTH_DOUBLE);
            } else if (listOfData.get(i).contains("Long")) {
                TypeDataPrim = listOfData.get(i).substring(ln - LENGTH_LONG);
                NameDataPrim = listOfData.get(i).substring(0, ln - LENGTH_LONG);
            } else if (listOfData.get(i).contains("Float")) {
                TypeDataPrim = listOfData.get(i).substring(ln - LENGTH_FLOAT);
                NameDataPrim = listOfData.get(i).substring(0, ln - LENGTH_FLOAT);
            }
            listOfData.set(i, TypeDataPrim + " " + NameDataPrim + ";");
            TypeDataPrim = "";
            NameDataPrim = "";
        }
        return listOfData;
    }
    
    public String getRegisters(String SQL_QUERY){
        String[] segment; 
        String values ="",sentence="";
        
        /*QUITA LA SENTENCIA GENERADORA DE TABLA: 'CREATE TABLE'*/
        if (SQL_QUERY.contains(INSERT_TB[0])) {
            segment = SQL_QUERY.split(INSERT_TB[0]);
            sentence = segment[1];
        } else if (SQL_QUERY.contains(INSERT_TB[1])) {
            segment = SQL_QUERY.split(INSERT_TB[1]);
            sentence = segment[1];
        } else {
            System.out.println("Syntaxis error");
            sentence = null;
        }
        /*-----------------------------------------------------*/
        //System.out.println("getregi:"+sentence);
        segment = null;
        if (sentence!=null) {
            if (sentence.contains("values")) {
                //System.out.println("Sentence: "+sentence);
                segment = sentence.split("values");
            }else if(sentence.contains("VALUES")){
                //System.out.println("Sentence: "+sentence);
                segment = sentence.split("VALUES");
            }
            sentence = "";
            sentence= segment[1];
            //System.out.println("sentence2: "+sentence);
            segment = null;
            segment = sentence.split("\\(|\\)");
            sentence = "";
            sentence = segment[1];
            //System.out.println("sentence3:"+sentence);
            values = sentence;
        }else{
            System.out.println("Error. Registers didn't create");
            values=null;
        }
        return values;
    }

    public static void main(String[] args) {
        String sql = "CREATE TABLE UNO(uno INT, name VARCHAR(20), desc varchar(10), nosequeponer varchar(5), precio FLOAT);";
        System.out.println(sql);
        new Lexer().getDataTable(sql);
        System.out.println("----");
        String SQL = "CREATE TABLE A_UNO( nombre varchar(20), edad int, apellido varchar(10),precio FLOAT);";
        System.out.println(SQL);
        new Lexer().getDataTable(SQL);
        /*String slx = "INSERT INTO UNO(10,12);";
        new Lexer().getRegisters(SQL, "UNO");*/
        
    }
}

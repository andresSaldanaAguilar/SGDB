import java.io.*;
import java.util.ArrayList;
class Objeto{ //The serializable class converts an object in a bunch of bytes


        String str = "/*package math;*/"+
                "public class Tabla1 { "
                +"public int id,edad;"
                +"public String nombre;"
                + "  public int getId(){ "
                + "    return this.id; }"
                + "  public String getNombre(){ "
                + "    return this.nombre; }"
                + "  public int getEdad(){ "
                + "    return this.edad; }"
                + "  void setId(int id){ "
                + "    this.id=id; }"
                + "  public void setNombre(String nombre){ "
                + "    this.nombre=nombre; }"
                + "  public void setEdad(int edad){ "
                + "    this.edad=edad; }"
                + "  public static void main(String[] args) { "
                + "    Tabla1 t = new Tabla1(); "
                + "    t.setId(1); t.setNombre(\"Pepe\"); t.setEdad(40); "
                +"   System.out.println(\"Termina codigo en memoria\");"
                + "  } " + "} ";

/*public class nada {
    public int uno ;
    public int dos ;
    public void setuno(int uno){
        this.uno=uno;
    }
    public int getuno(){
        return this.uno;
    }
    public void setdos(int dos){
        this.dos=dos;
    }
    public int getdos(){
         return this.dos;
    }*/
}
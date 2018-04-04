package sgbd;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
 
import javax.tools.Diagnostic;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
 
/**
 * Dynamic java class compiler and executer  <br>
 * Demonstrate how to compile dynamic java source code, <br>
 * instantiate instance of the class, and finally call method of the class <br>
 *
 * http://www.beyondlinux.com
 *
 *
 */
public class DynamicCompiler
{
    /** where shall the compiled class be saved to (should exist already) */
    private static String classOutputFolder = "./build/classes";
    
    public DynamicCompiler(String classOutputFolder){
        this.classOutputFolder += classOutputFolder;
    }
    
    public static class MyDiagnosticListener implements DiagnosticListener<JavaFileObject>
    {
        public void report(Diagnostic<? extends JavaFileObject> diagnostic)
        {
            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("code->" + diagnostic.getCode());
            System.out.println("Message->"
                               + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Source->" + diagnostic.getSource());
            System.out.println(" ");
        }
    }
 
    /** java File Object represents an in-memory java source file <br>
     * so there is no need to put the source file on hard disk  **/
    public static class InMemoryJavaFileObject extends SimpleJavaFileObject
    {
        private String contents = null;
 
        public InMemoryJavaFileObject(String className, String contents) throws Exception
        {
            super(URI.create("string:///" + className.replace('.', '/')
                             + Kind.SOURCE.extension), Kind.SOURCE);
            this.contents = contents;
        }
 
        public CharSequence getCharContent(boolean ignoreEncodingErrors)
                throws IOException
        {
            return contents;
        }
    }
 
    /** Get a simple Java File Object ,<br>
     * It is just for demo, content of the source code is dynamic in real use case */
    public static JavaFileObject getJavaFileObject(String str,String classname)
    {
        String contents = str;
        JavaFileObject jfo = null;
        try
        {
            jfo = new InMemoryJavaFileObject("/*math."+classname+"*/"+classname, contents);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return jfo;
    }
 
    /** compile your files by JavaCompiler */
    public static void compile(Iterable<? extends JavaFileObject> files)
    {
        //get system compiler:
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 
        // for compilation diagnostic message processing on compilation WARNING/ERROR
        MyDiagnosticListener c = new MyDiagnosticListener();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(c,
                                                                              Locale.ENGLISH,
                                                                              null);
        //specify classes output folder
        Iterable options = Arrays.asList("-d", classOutputFolder);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
                                                             c, options, null,
                                                             files);
        Boolean result = task.call();
        if (result == true)
        {
            System.out.println("Succeeded");
        }
    }
    
    
    public static Object newInstance(String classname){
        
        File file = new File(classOutputFolder);
        Object obj = null;
        try
        {
                    // Convert File to a URL
            URL url = file.toURL(); // file:/classes/demo
            URL[] urls = new URL[] { url };
            System.out.println("dentro del metodo invoke..");
            // Create a new class loader with the directory
            ClassLoader loader = new URLClassLoader(urls);
            System.out.println("crea cargador de clase");
            // Load in the class; Class.childclass should be located in
            // the directory file:/class/demo/
            Class thisClass = loader.loadClass(classname);
            Class params[] = {};
            System.out.println("Cargo bien la clase");

            String ClassName = classname;
            Class<?> tClass = Class.forName(ClassName); // convert string classname to class
            obj = tClass.newInstance(); // invoke empty constructor
            System.out.println("Genero bien instancia "+obj.getClass().getName());
        }catch (MalformedURLException e)
            {
                System.out.println("malformedURL");
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("class not found");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        return obj;
    }
 
    public static void runSet(String primitive,String column,String value,Object obj)
    { 
        try
        {
            String methodName = "";

            // with single parameter, return void
            methodName = "set"+column;
            if(primitive.endsWith("String")){
                Method setNameMethod = obj.getClass().getMethod(methodName,String.class);
                setNameMethod.invoke(obj,value); // pass arg      
            }
            else{               
                Method setNameMethod = obj.getClass().getMethod(methodName,Integer.class);
                setNameMethod.invoke(obj,Integer.parseInt(value)); // pass arg
            }
            System.out.println("Succesful set");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
    }
    
    public static String runGet(String primitive,String column,Object obj)
    {
        String finalvalue ="";
        try
        {
            String methodName = "";

            // with single parameter, return void
            methodName = "get"+column;
            if(primitive.endsWith("String")){
                Method getNameMethod = obj.getClass().getMethod(methodName);
                String value = (String) getNameMethod.invoke(obj); // explicit cast
                System.out.println("Valor devuelto por metodo:"+value);  
                finalvalue += value;
            }
            else{               
                Method getNameMethod = obj.getClass().getMethod(methodName);
                Integer value = (Integer) getNameMethod.invoke(obj); // explicit cast
                System.out.println("Valor devuelto por metodo:"+value);
                finalvalue += value;
            }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        return finalvalue;
    }
    
    /*creates the specified class on a string*/
    public static String classBuilder(ArrayList<String> genclass){
        String stringb= "/*package math;*/ public class "+genclass.get(0).replace(";","")+" implements java.io.Serializable {";
        //concatenate the attributes
        for(int i = 1; i < genclass.size(); i++){
            stringb+= "public " + genclass.get(i);
        }
        //getting the attribute name and datatype for getters an setters
        ArrayList<String> attributes = new ArrayList();
        ArrayList<String> datatypes = new ArrayList();
        for(int i = 1; i < genclass.size(); i++){
            String[] parts = genclass.get(i).split(" ");          
            attributes.add(parts[1]);
            datatypes.add(parts[0]);
        }

        for(int i = 0; i < attributes.size(); i++){
            //setter
            stringb+= "public void set"+ attributes.get(i) +"("+datatypes.get(i)+" "+attributes.get(i)+"){this."+ attributes.get(i)+"="+attributes.get(i)+";}";                
            //getter
            stringb+= "public "+ datatypes.get(i) +" get"+ attributes.get(i) +"(){ return this."+ attributes.get(i) +";}";               
        }
        stringb+="}";
        System.out.println(stringb);
        return stringb;
    }
    
    public static ArrayList<Object> createObjects(ArrayList<String> registers,String classname){
        //separa los registros
        String[] headers =registers.get(0).split("_");

        ArrayList<String> datatypes = new ArrayList();
        ArrayList<String> names = new ArrayList();
        for(String header: headers){
            String[] aux = header.split(" ");
            names.add(aux[1]);
            datatypes.add(aux[0]);            
        }
        ArrayList<Object> objects = new ArrayList();
        for(int i =1; i<registers.size();i++){
            Object obj = newInstance(classname);
            String[] reg = registers.get(i).split("_");
  
            for(int j =0; j<datatypes.size();j++){
                runSet(datatypes.get(j),names.get(j),reg[j],obj);
            }
            objects.add(obj);
        }
        return objects;     
    }
    
    public static ArrayList<String> readObjects(ArrayList<Object> objects,ArrayList<String> registers){
        String[] headers =registers.get(0).split("_");
        ArrayList<String> datatypes = new ArrayList();
        ArrayList<String> names = new ArrayList();
        for(String header: headers){
            String[] aux = header.split(" ");
            names.add(aux[1]);
            datatypes.add(aux[0]);            
        }
        ArrayList<String> li = new ArrayList();
        for(int i =0; i<objects.size();i++){
            String aux = "";
            for(int j =0; j<datatypes.size();j++){
                if(j==datatypes.size()-1){
                    aux += runGet(datatypes.get(j),names.get(j),objects.get(i));
                }
                else{
                    aux += runGet(datatypes.get(j),names.get(j),objects.get(i))+"_";                    
                }

            }
            li.add(aux);
        }   
        return li;
    }    
 
    public static void main(String[] args) throws Exception
    {
        //1.Construct an in-memory java source file from your dynamic code
        ArrayList<String>  al= new ArrayList();
        al.add("BD_tabla");
        al.add("String nombre ;");
        al.add("Integer peso ;");
        al.add("Integer edad ;");

        String str = classBuilder(al);
           
        JavaFileObject file = getJavaFileObject(str,al.get(0));
        Iterable<? extends JavaFileObject> files = Arrays.asList(file);
        
        //2.Compile your files by JavaCompiler
        compile(files);
 
        //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
        /*Object obj = newInstance("uno_pollito");
        runSet("Integer","edad","12",obj);
        runGet("Integer","edad",obj);*/
      }
}
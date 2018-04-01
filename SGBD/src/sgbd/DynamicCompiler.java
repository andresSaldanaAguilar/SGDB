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
 
    /** run class from the compiled byte code file by URLClassloader */
    public static void runIt(String classname)
    {
        // Create a File object on the root of the directory
        // containing the class file
        File file = new File(classOutputFolder);
 
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
            //Object paramsObj[] = {};
            /*Object o = (Object)new String("Juancho");
            Object[] param = new Object[]{o};
            Object[] paramsObj = new Object[]{param};
            Object instance = thisClass.newInstance();
            Method thisMethod = thisClass.getDeclaredMethod("setNombre", params);
            */
            /*******************/
        String ClassName = classname;
        Class<?> tClass = Class.forName(ClassName); // convert string classname to class
        Object tabla = tClass.newInstance(); // invoke empty constructor
            System.out.println("Genero bien instancia "+tabla.getClass().getName());
        String methodName = "";

        // with single parameter, return void
        methodName = "setuno";
        Method setNameMethod = tabla.getClass().getMethod(methodName, String.class);
        setNameMethod.invoke(tabla, 1); // pass arg

        // without parameters, return string
        methodName = "getuno";
        Method getNameMethod = tabla.getClass().getMethod(methodName);
        String name = (String) getNameMethod.invoke(tabla); // explicit cast
            System.out.println("Valor devuelto por metodo:"+name);
//            String p = "Juancho";
//            Method thisMethod = thisClass.getDeclaredMethod("setNombre", params)
            /*********************/
            // run the testAdd() method on the instance:
//            thisMethod.invoke(instance, paramsObj);
//            Method m1 = thisClass.getMethod("getNombre", null);
//            Object ob = m1.invoke(instance, null);
//            System.out.println("Dato devuelto: "+ob);
        }
        catch (MalformedURLException e)
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
    }
    
    /*creates the specified class on a string*/
    public static String classBuilder(ArrayList<String> genclass){
        String stringb= "/*package math;*/ public class "+genclass.get(0)+" {";
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
    
 
    public static void main(String[] args) throws Exception
    {
        //1.Construct an in-memory java source file from your dynamic code
        ArrayList<String>  al= new ArrayList();
        al.add("nada");
        al.add("int uno ;");
        al.add("int dos ;");
        String str = classBuilder(al);
           
        JavaFileObject file = getJavaFileObject(str,al.get(0));
        Iterable<? extends JavaFileObject> files = Arrays.asList(file);
        
        //2.Compile your files by JavaCompiler
        compile(files);
 
        //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
        runIt(al.get(0));
        System.out.println("fin del programa..");
      }
}
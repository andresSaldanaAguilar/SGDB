import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
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
public class Servidor_O
{
    /** where shall the compiled class be saved to (should exist already) */
    private static String classOutputFolder = "./build/classes";
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
    private static JavaFileObject getJavaFileObject()
    {
        StringBuilder contents = new StringBuilder(
        "/*package math;*/"
        +"public class nada{"
        +"public Integer uno;"
        +"public Integer getUno(){"
        +"return this.uno;}"
        +"public void setUno(Integer uno){"
        +"this.uno=uno;}"
        + "}");
        
        JavaFileObject jfo = null;
        try
        {
            jfo = new InMemoryJavaFileObject("/*math.nada*/nada", contents.toString());
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
    public static void runIt()
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
            Class thisClass = loader.loadClass("nada");
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
        String ClassName = "nada";
        Class<?> tClass = Class.forName(ClassName); // convert string classname to class
        Object tabla = tClass.newInstance(); // invoke empty constructor
            System.out.println("Genero bien instancia "+tabla.getClass().getName());
        //String methodName = "";

        // with single parameter, return void
        //methodName = "setUno";
        Method [] m = tabla.getClass().getMethods();
        System.out.println(m[1].getName());
        System.out.println(m[0].getName());
        Method setNameMethod = tabla.getClass().getMethod(m[1].getName(), Integer.class);
        setNameMethod.invoke(tabla, 1); // pass arg


        // without parameters, return string
        //methodName = "getUno";
        Method getNameMethod = tabla.getClass().getMethod(m[0].getName());
        Integer name = (Integer) getNameMethod.invoke(tabla); // explicit cast
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
        }
        catch (ClassNotFoundException e)
        {
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
 
    public static void main(String[] args) throws Exception
    {
        //1.Construct an in-memory java source file from your dynamic code
        JavaFileObject file = getJavaFileObject();
        Iterable<? extends JavaFileObject> files = Arrays.asList(file);
 
        //2.Compile your files by JavaCompiler
        compile(files);
 
        //3.Load your class by URLClassLoader, then instantiate the instance, and call method by reflection
        runIt();
        System.out.println("fin del programa..");
      }
}
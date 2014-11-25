package independentcopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This ExternalSpecFactory decouples the specifications for building
 * Reader and Writer objects from the Factory class. Instead, the specs are
 * read from an external text file that has key=value pairs providing the
 * specifications. Once read in, the factory class can build objects using
 * Java Reflection techniques.
 * 
 * IMPORTANT: to run this program you must place the config.properties file
 * in the appropriate directory and then modify the file path below to
 * use that directory.
 *
 * @author jlombardo
 */
public abstract class ExternalSpecFactory {

    public static Reader getReaderInstance() {
        Reader r = null;

        // First read config setting in properties file
//        File file = new File("/temp/config.properties");
        File file = new File("src/config.properties");
        
        //props will contain key value pairs on properties file.
        Properties props = new Properties();
        
        
        FileInputStream inFile;
        try {
            inFile = new FileInputStream(file);
            props.load(inFile);
            inFile.close();
        
            // Next use Java reflection to create instance
            String className = props.getProperty("reader");
            Class clazz = Class.forName(className);
            r = (Reader)clazz.newInstance();
            
        } catch (Exception ex) {
            System.out.println("ERROR: you must place a copy of the\n "
                    + "config.properties file in the 'c:/temp' directory of "
                    + "your computer's main hard drive.");
        }
        
        return r;
    }

    public static Writer getWriterInstance() {
        Writer w = null;

        // First read config setting in properties file
        Properties props = new Properties();
        FileInputStream inFile;
        try {
//            inFile = new FileInputStream("/temp/config.properties");
            inFile = new FileInputStream("src/config.properties");
            props.load(inFile);
            inFile.close();
        
            // Next use Java reflection to create instance
            String className = props.getProperty("writer");
            Class clazz = Class.forName(className);
            w = (Writer)clazz.newInstance();
            
        } catch (Exception ex) {
            Logger.getLogger(ExternalSpecFactory.class.getName()).log(Level.SEVERE, null, ex);

        }
        
        return w;
    }
}

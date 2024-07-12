package fetch.sdetchallenge.utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    //static initializer runs the block only once for the whole project
    //instance initializer runs the block for every object creation from class
    private static Properties properties;

    static {
        //filePath -> the directory of your properties file
        String filePath = "src/test/resources/properties/config.properties"; //Provide the path to the file

        //this a class that enable you to read files
        //it throws a checked exception

        //FileInputStream class is used for reading binary data from a file.

        FileInputStream input = null;
        try {
            input = new FileInputStream(filePath); //go read this file
            properties = new Properties(); //initializing properties
            properties.load(input); //take everything you read and load it to this properties

        } catch (IOException e) {
            System.out.println("File not Found");
        }
        //Always close the `FileInputStream` once you're done with it to release system resources.
        // This is typically done in a `finally` block to ensure proper cleanup.

        finally {
            try {
                input.close(); //clean up method
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load configuration file.");
            }
        }
    }
    public static String getPropertiesValue(String key){
        return properties.getProperty(key);
    }
}

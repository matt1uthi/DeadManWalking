package deadManWalking.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *Bunch of helper functions that can assist us in anywhere
 * in our game
 * @author matthewluthi
 */
public class Utils {
    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            //Current line we are working on
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + "\n");
            }
            
            br.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return builder.toString();
    }
    
    //This method takes in a string like "5" and convert it
    //to an integer 5
    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) { //In case string is not a num
            e.printStackTrace();
            return 0;//return default value to avoid program crash
        }
    }
}

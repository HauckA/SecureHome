package webcam.securehome;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This file creates and reads config files.
 */
public class fileHandler {


    public void saveFile(String fileName, String fileContent, Context ctx) {

        try {

            FileOutputStream fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(fileContent.getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    };

    public String getWebcamID(Context ctx) {
        String str = null;
        StringBuilder sb = new StringBuilder();
        try{
            FileInputStream is = ctx.openFileInput("webcam_id.config");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            is.close();
        } catch(OutOfMemoryError om){
            om.printStackTrace();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        str = sb.toString();

        return str;

    }

    /*
    public void readConfigFiles(Context ctx) {


        try {
            // open the file for reading
            InputStream instream = ctx.openFileInput("myfilename.txt");

            // if file the available for reading
            if (instream) {
                // prepare the file for reading
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);

                String line;

                // read every line of the file into the line-variable, on line at the time
                while (( line = buffreader.readLine())) {
                    // do something with the settings from the file
                }

            }

            // close the file again
            instream.close();
        } catch (IOException e) {
            // do something if the myfilename.txt does not exits
        }

    }*/

}

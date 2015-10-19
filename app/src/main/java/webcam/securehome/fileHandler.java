package webcam.securehome;

import android.content.Context;

import java.io.BufferedReader;
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

    private Integer webcamID = null;

    public Integer getWebcamID() {
        return this.webcamID;
    }

    public void saveFile(String fileName, String fileContent, Context ctx) {

        try {

            FileOutputStream fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(fileContent.getBytes());
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    };

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

    }

}

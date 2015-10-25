package webcam.securehome;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This file creates and reads config files.
 */
public class FileHandler {

    private boolean checkIfFileExists(String fileName, Context ctx) {

        File file = ctx.getApplicationContext().getFileStreamPath(fileName);
        return file.exists();
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

    public String getFileContent(String fileName, Context ctx) {

        String str = null;

        //Check if file exists
        if (checkIfFileExists(fileName, ctx) == true) {

            StringBuilder sb = new StringBuilder();
            try {
                FileInputStream is = ctx.openFileInput(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                is.close();
            } catch (OutOfMemoryError om) {
                om.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            str = sb.toString();

        } else {

            str = "";

        }

        return str.trim();

    }
}

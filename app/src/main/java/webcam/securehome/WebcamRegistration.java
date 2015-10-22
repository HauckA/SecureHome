package webcam.securehome;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import webcam.securehome.helper.JSONParser;

/**
 * Created by Sandro on 20.10.2015.
 */
public class WebcamRegistration extends AsyncTask<String, Void, JSONObject> {

    private JSONParser jsonParser;
    private WebcamRegistrationActivity webcamRegistrationActivity;
    //Bridged
    //private static String loginURL = "http://192.168.1.113/SecureHome/index.php";
    //Host-Only
    private static String webcam_registration_URL = "http://192.168.83.128/SecureHome/index.php";
    private static String webcam_registration_TAG = "webcam_registration";


    // constructor
    public WebcamRegistration(WebcamRegistrationActivity webcamRegistrationActivity){
        this.webcamRegistrationActivity = webcamRegistrationActivity;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {}


    @Override
    protected void onPostExecute(JSONObject jsonFromDoInBg) {

        // check for login response
        String res = null;
        String error = null;
        try {
            res = jsonFromDoInBg.getString("success");
            if (Integer.parseInt(res) == 1) {
                //TODO neue Webcam in der Datenbank speichern (dafür wird noch die ID des angemeldeten Benutzers benötigt!)
                // TODO die ID der gespeicherten Webcam abrufen und dynamisch übergeben. Hie rwird als beispiel einfach die ID 1 eingetragen.

                //Save the new ID in the local config file
                //fileHandler fh = new fileHandler();
                //  fh.saveFile("webcam_id.config", "1", getApplicationContext());

                // Webcam is not registrated: Go to Webcam Registration Activity
                Intent intent = new Intent(webcamRegistrationActivity, WebcamPreviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                webcamRegistrationActivity.startActivity(intent);
            }
            else{
                error = jsonFromDoInBg.getString("error");
                if(Integer.parseInt(error) == 1){
                   // Log.e("Login Error", "Username or Password is incorrect");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        List<NameValuePair> paramsPack = new ArrayList<>();
        paramsPack.add(new BasicNameValuePair("tag", webcam_registration_TAG));
        paramsPack.add(new BasicNameValuePair("userid", String.valueOf(Login.userid)));
        paramsPack.add(new BasicNameValuePair("webcam_description", params[0]));
        JSONObject json = jsonParser.getJSONFromUrl(webcam_registration_URL, paramsPack);

        // return json
        Log.e("JSON", json.toString());
        return json;
    }
}

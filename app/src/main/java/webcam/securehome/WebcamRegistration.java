package webcam.securehome;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

    private static String webcam_registration_URL = null;
    private static String webcam_registration_TAG = "webcam_registration";
    private static final String MSG_ERROR1 = "Error occured in Webcam Registration";

    //FileHandler
    FileHandler fh = new FileHandler();


    // constructor
    public WebcamRegistration(WebcamRegistrationActivity webcamRegistrationActivity){
        this.webcamRegistrationActivity = webcamRegistrationActivity;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        //Read URL from Config file
        webcam_registration_URL = fh.getFileContent("webservice_url.config", this.webcamRegistrationActivity.getApplicationContext());

    }


    @Override
    protected void onPostExecute(JSONObject jsonFromDoInBg) {

        // check for login response
        String res = null;
        String error = null;
        int webcamId = 0;

        try {
            res = jsonFromDoInBg.getString("success");
            if (Integer.parseInt(res) == 1) {

                webcamId = jsonFromDoInBg.getInt("webcam_id");
                Log.i("Registrated Cam ID", String.valueOf(webcamId));

                //Save the new ID in the local config file
                fh.saveFile("webcam_id.config", Integer.toString(webcamId), this.webcamRegistrationActivity.getApplicationContext());

                // Go To WebcamPreview Activity
                Intent intent = new Intent(webcamRegistrationActivity, WebcamPreviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                webcamRegistrationActivity.startActivity(intent);
            }
            else{
                error = jsonFromDoInBg.getString("error");
                if(Integer.parseInt(error) == 1){
                    Log.e("Login Error", "Error occured in Webcam Registration");
                    Toast.makeText(webcamRegistrationActivity.getApplicationContext(), MSG_ERROR1, Toast.LENGTH_LONG).show();
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
        paramsPack.add(new BasicNameValuePair("user_id", String.valueOf(Login.userid)));
        paramsPack.add(new BasicNameValuePair("webcam_description", params[0]));
        JSONObject json = jsonParser.getJSONFromUrl(webcam_registration_URL, paramsPack);

        // return json
        Log.e("JSON", json.toString());
        return json;
    }
}

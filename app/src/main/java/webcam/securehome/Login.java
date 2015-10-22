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
public class Login extends AsyncTask<String, Void, JSONObject> {

    private JSONParser jsonParser;
    private LoginActivity loginActivity;

    //Static Logged UserID
    public static int userid = 0;

    //Bridged
    //private static String loginURL = "http://192.168.1.113/SecureHome/index.php";
    //Host-Only
    private static String loginURL = "http://192.168.83.128/SecureHome/index.php";
    private static String login_tag = "login";


    // constructor
    public Login(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
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
                JSONObject json_user = jsonFromDoInBg.getJSONObject("user");
                String username = json_user.getString("username");
                String email = json_user.getString("email");
                Log.i("Login successful! ", "User: " + username + " Email: " + email);


                // TODO schauen ob Gerät registriert, falls ja auf webcamactivity weiterleiten, sonst auf webcamregistration
                //Check if device is already registrated
                fileHandler fh = new fileHandler();
                //String webcamID = fh.getWebcamID(loginActivity.getApplicationContext());
                //Only for testing
                String webcamID = "1";

                //Check if Device is already registrated as webcam
                if (!webcamID.equals("")) {
                    // Webcam is not registrated: Go to Webcam Registration Activity
                    Intent intent = new Intent(loginActivity, WebcamRegistrationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    loginActivity.startActivity(intent);
                } else {
                    //Start a new activity from an non-activity class!!!! Yiii HAAAAHHHH :)
                    Intent intent = new Intent(loginActivity, WebcamPreviewActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    loginActivity.startActivity(intent);
                }
            }
            else{
                error = jsonFromDoInBg.getString("error");
                if(Integer.parseInt(error) == 1){
                    Log.e("Login Error", "Username or Password is incorrect");
                }
                else if(Integer.parseInt(error) == 2){
                    Log.e("Login Error", "Error occured in Registration");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        List<NameValuePair> paramsPack = new ArrayList<>();
        paramsPack.add(new BasicNameValuePair("tag", login_tag));
        paramsPack.add(new BasicNameValuePair("username", params[0]));
        paramsPack.add(new BasicNameValuePair("password", params[1]));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, paramsPack);

        // return json
        Log.e("JSON", json.toString());
        return json;
    }
}

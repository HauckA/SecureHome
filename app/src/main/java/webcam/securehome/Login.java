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
public class Login extends AsyncTask<String, Void, JSONObject> {

    private JSONParser jsonParser;
    private LoginActivity loginActivity;

    private static final String MSG_ERROR1 = "Username or Password is incorrect!";
    private static final String MSG_ERROR2 = "Error occured in registration!";


    //Static Logged UserID
    public static int userid = 0;
    //Bridged
    private static String loginURL = null;

    private static String login_tag = "login";

    private FileHandler fh = new FileHandler();



    // constructor
    public Login(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {

        //get URL from config-file
        loginURL = fh.getFileContent("webservice_url.config", this.loginActivity.getApplicationContext()).trim();
        Log.i("Login URL", loginURL);

    }


    @Override
    protected void onPostExecute(JSONObject jsonFromDoInBg) {

        // check for login response
        String res = null;
        String error = null;

        try {
            res = jsonFromDoInBg.getString("success");
            if (Integer.parseInt(res) == 1) {
                JSONObject json_user = jsonFromDoInBg.getJSONObject("user");

                userid = jsonFromDoInBg.getInt("uid");

                //Speichere userid ins ConfigFile
                fh.saveFile("userid.config", String.valueOf(userid), loginActivity.getApplicationContext());

                Log.i("userid", String.valueOf(userid));
                String username = json_user.getString("username");
                String email = json_user.getString("email");
                Log.i("Login successful! ", "Userid: " + userid + " Username: " + username + " Email: " + email);


                //Read webcamID from config-file and check if it is set or not.
               // String webcamID = fh.getFileContent("webcam_id.config", loginActivity.getApplicationContext()).trim();
                String webcamID = fh.getFileContent("webcam_id.config", loginActivity.getApplicationContext()).trim();
Log.i("Webcamid", webcamID);
                //Check if Device is already registrated as webcam
                if (webcamID.equals("")) {
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
                    Toast.makeText(loginActivity.getApplicationContext(), MSG_ERROR1, Toast.LENGTH_LONG).show();

                }
                else if(Integer.parseInt(error) == 2){
                    Log.e("Login Error", "Error occured in Registration");
                    Toast.makeText(loginActivity.getApplicationContext(), MSG_ERROR2, Toast.LENGTH_LONG).show();
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

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
public class Registration extends AsyncTask<String, Void, JSONObject> {

    private JSONParser jsonParser;
    private UserRegistrationActivity userRegistrationActivity;

    private static String registerURL = null;
    private static String registerTag = "register";

    private FileHandler fh = new FileHandler();


    // constructor
    public Registration(UserRegistrationActivity userRegistrationActivity){
        this.userRegistrationActivity = userRegistrationActivity;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        //Read URL from Config-File
        registerURL = fh.getFileContent("ip_adress.config", userRegistrationActivity.getApplicationContext()).trim();
    }


    @Override
    protected void onPostExecute(JSONObject jsonFromDoInBg) {

        // check for login response
        String res = null;
        String error = null;
        try {
            res = jsonFromDoInBg.getString("success");
            if (Integer.parseInt(res) == 1) {
                Intent intent = new Intent(userRegistrationActivity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                userRegistrationActivity.startActivity(intent);
            }
            else{
                error = jsonFromDoInBg.getString("error");
                if(Integer.parseInt(error) == 1){
                    Log.e("Registration Error", "");
                }
                else if(Integer.parseInt(error) == 2){
                    Log.e("Registration Error", "Error occured in Registration");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        //firstname, lastname, email, username, password1

        List<NameValuePair> paramsPack = new ArrayList<>();
        paramsPack.add(new BasicNameValuePair("tag", registerTag));
        paramsPack.add(new BasicNameValuePair("firstname", params[0]));
        paramsPack.add(new BasicNameValuePair("lastname", params[1]));
        paramsPack.add(new BasicNameValuePair("email", params[2]));
        paramsPack.add(new BasicNameValuePair("username", params[3]));
        paramsPack.add(new BasicNameValuePair("password", params[4]));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, paramsPack);

        // return json
        Log.e("JSON", json.toString());
        return json;
    }
}

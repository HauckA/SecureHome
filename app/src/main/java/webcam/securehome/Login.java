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
    private MainActivity mainActivity;

    private static String loginURL = "http://192.168.1.113/SecureHome/index.php";
    private static String login_tag = "login";


    // constructor
    public Login(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {}


    @Override
    protected void onPostExecute(JSONObject jsonFromDoInBg) {

        // check for login response
        String res = null;
        try {
            res = jsonFromDoInBg.getString("success");
            if(Integer.parseInt(res) == 1) {
                JSONObject json_user = jsonFromDoInBg.getJSONObject("user");
                String username = json_user.getString("username");
                String email = json_user.getString("email");
                Log.i("Login successful! ", "User: " + username + " Email: " + email);

                //Start a new activity from an non-activity class!!!! Yiii HAAAAHHHH :)
                Intent intent = new Intent(mainActivity, WebcamActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mainActivity.startActivity(intent);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    @Override
    protected JSONObject doInBackground(String... params) {
        List<NameValuePair> paramsPack = new ArrayList<NameValuePair>();
        paramsPack.add(new BasicNameValuePair("tag", login_tag));
        paramsPack.add(new BasicNameValuePair("username", params[0]));
        paramsPack.add(new BasicNameValuePair("password", params[1]));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, paramsPack);

        // return json
        //Log.e("JSON", json.toString());
        return json;
    }
}

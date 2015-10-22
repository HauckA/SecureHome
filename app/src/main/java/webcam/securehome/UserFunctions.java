package webcam.securehome;

/**
 * Created by Sandro on 17.10.2015.
 */

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import webcam.securehome.helper.JSONParser;

public class UserFunctions {

    private JSONParser jsonParser;

    private static String loginURL = "http://192.168.1.53/SecureHome/index.php";
    private static String registerURL = "http://192.168.1.53/SecureHome/index.php";

    private static String login_tag = "login";
    private static String register_tag = "register";

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * function make Login Request
     * @param username
     * @param password
     * */
    public JSONObject loginUser(String username, String password){
        // Building Parameters
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }

    /**
     * function make Login Request
     * @param username
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String username, String email, String password){
        // Building Parameters
        List params = new ArrayList();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        // return json
        return json;
    }


}

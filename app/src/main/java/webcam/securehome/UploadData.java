package webcam.securehome;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import webcam.securehome.helper.CameraView;
import webcam.securehome.helper.JSONParser;

/**
 * Created by Sandro on 20.10.2015.
 */
public class UploadData extends AsyncTask<String, Void, JSONObject> {

    private JSONParser jsonParser;
    private CameraView cameraView;


    private static final String MSG_ERROR1 = "Couldn't connect to the server";


    //Static Logged UserID
    private static int userid = 0;
    private static String URL = null;
    private static String webcamid = null;

    private FileHandler fh = new FileHandler();

    // constructor
    public UploadData(CameraView cameraView){
        this.cameraView = cameraView;
        jsonParser = new JSONParser();
    }

    @Override
    protected void onPreExecute() {
        URL = fh.getFileContent("webservice_url.config", cameraView.getContext());

        userid = Login.userid;
        webcamid = fh.getFileContent(String.valueOf(Login.userid)+".config", cameraView.getContext()).trim();
    }


    @Override
    protected void onPostExecute(JSONObject jsonFromDoInBg) {
        // check for login response
        String res = null;
        String error = null;

        try {
            res = jsonFromDoInBg.getString("success");
            if (Integer.parseInt(res) == 1) {
                Toast.makeText(cameraView.getContext(), "Foto wurde gesendet", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        List<NameValuePair> paramsPack = new ArrayList<>();
        paramsPack.add(new BasicNameValuePair("tag", "uploaddata"));
        paramsPack.add(new BasicNameValuePair("userid", String.valueOf(userid)));
        paramsPack.add(new BasicNameValuePair("webcamid", String.valueOf(webcamid)));
        paramsPack.add(new BasicNameValuePair("imagestr", params[0]));
        JSONObject json = jsonParser.getJSONFromUrl(URL, paramsPack);

        return json;
    }
}

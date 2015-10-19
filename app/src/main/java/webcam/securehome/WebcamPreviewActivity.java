package webcam.securehome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class show a preview of the Webcam. Here the user can start to upload files to the webserver.
 */
public class WebcamPreviewActivity extends AppCompatActivity {

    //Sring declaration
    private String webcamID = null;
    private String webcamDescription = null;

    //TextView declaration
    private TextView txtWebcamDescription = null;

    //Button declaration
    private Button btnStartBroadcast = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcampreview_layout);

        //TextView generation
        this.txtWebcamDescription = (TextView) findViewById(R.id.txtWebcamDescription);

        //Button generation
        this.btnStartBroadcast = (Button) findViewById(R.id.btnStartBroadcast);

        //Set description-text
        //TODO Read Webcam Description from Database
        this.txtWebcamDescription.setText(webcamDescription);


    };


}
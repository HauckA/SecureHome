package webcam.securehome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * This class show a preview of the Webcam. Here the user can start to upload files to the webserver.
 */
public class WebcamPreviewActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcampreview_layout);
    };

}
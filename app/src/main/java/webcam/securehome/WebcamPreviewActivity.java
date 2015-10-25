package webcam.securehome;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import webcam.securehome.helper.CameraView;

/**
 * This class show a preview of the Webcam. Here the user can start to upload files to the webserver.
 */
public class WebcamPreviewActivity extends AppCompatActivity {

    //Camera stuff
    private Camera mCamera = null;
    private CameraView mCameraView = null;

    //Sring declaration
    private String webcamID = null;
    private String webcamDescription = null;

    //TextView declaration
    private TextView txtWebcamDescription = null;

    //Button declaration
    private Button btnStartBroadcast = null;

    //ImagePreview
    private ImageView imageView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcampreview_layout);

        //TextView generation
        this.txtWebcamDescription = (TextView) findViewById(R.id.txtWebcamDescription);

        //Button generation
        this.btnStartBroadcast = (Button) findViewById(R.id.btnStartBroadcast);

        //ImageView generation
        this.imageView = (ImageView) findViewById(R.id.imageView);

        //Set description-text
        //TODO Read Webcam Description from Database
        this.txtWebcamDescription.setText(webcamDescription);

        try{
            mCamera = Camera.open();//you can use open(int) to use different cameras
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if(mCamera != null) {
            mCameraView = new CameraView(this, mCamera);//create a SurfaceView to show camera data
            FrameLayout camera_view = (FrameLayout)findViewById(R.id.frameLayout);
            camera_view.addView(mCameraView);//add the SurfaceView to the ImageView
        }



    };


}
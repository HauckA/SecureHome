package webcam.securehome.helper;

import android.content.Context;
import android.hardware.Camera;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import webcam.securehome.UpdateStatus;
import webcam.securehome.UploadData;

/**
 * Created by Alexander on 25.10.2015.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback{

    Button btnStartBroadcast = null;
    Button btnCancelBroadcast = null;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private boolean finish = false;

    public CameraView(Context context, Camera camera, Button btnStartBroadcast, Button btnCancelBroadcast){
        super(context);
        this.btnStartBroadcast = btnStartBroadcast;
        this.btnCancelBroadcast = btnCancelBroadcast;
        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        //get the holder and set this class as the callback, so we can get camera data here
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);

        //Set onClick Listener
        this.btnStartBroadcast.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("Info", "Button: Übertragung starten gedrückt");
                        finish=false;
                        new UpdateStatus(CameraView.this).execute(String.valueOf(finish));
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );
        this.btnCancelBroadcast.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("Info", "Button: Cancel gedrückt");
                        //Beende Upload
                        finish=true;
                        //Update Database
                        new UpdateStatus(CameraView.this).execute(String.valueOf(finish));
                    }
                }
        );

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        try{
             //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        //before changing the application orientation, you need to stop the preview, rotate and then start it again
        if(mHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;
        try{
            mCamera.stopPreview();
            finish = true;
        } catch (Exception e) {
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try{
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            finish = false;
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //our app has only one screen, so we'll destroy the camera in the surface
        //if you are unsing with more screens, please move this code your activity
        finish = true;
        mCamera.stopPreview();
        mCamera.release();

    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //TODO Upload File to Server
            String strImage= Base64.encodeToString(data, Base64.DEFAULT); // image1 is your byte[]

            mCamera.startPreview(); //Wenn das bei mir fehlt, schliesst es die App nach einem übertragenen Bild...
           if(!finish) {
               new UploadData(CameraView.this).execute(strImage);
               try {
                   //Take all 10 sec a photo
                   Thread.sleep(5000);
                   mCamera.takePicture(null, null, mPicture);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
        }
    };
}

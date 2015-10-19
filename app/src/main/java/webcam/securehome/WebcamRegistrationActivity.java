package webcam.securehome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Alexander on 15.10.2015.
 */
public class WebcamRegistrationActivity extends AppCompatActivity {

    //String declaration
    private String webcamDescription = null;

    //EditText declaration
    private EditText txtWebcamDescription = null;

    //TextView declaration
    private TextView txtErrorMessage = null;

    //Button declaration
    private Button btnRegistrateWebcam = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcamregistration_layout);

        //EditText generation
        this.txtWebcamDescription = (EditText) findViewById(R.id.txtWebcamDescription);

        //TextView generation
        this.txtErrorMessage = (TextView) findViewById(R.id.txtErrorMessageWebcamRegistration);

        //Button generation
        this.btnRegistrateWebcam = (Button) findViewById(R.id.btnRegistrateWebcam);

        this.btnRegistrateWebcam.setOnClickListener(
             new Button.OnClickListener() {

                 @Override
                 public void onClick(View v) {
                     txtErrorMessage.setText("");

                    webcamDescription = txtWebcamDescription.getText().toString();

                     //Check if value is set
                     if(webcamDescription.equals("")) {
                         txtErrorMessage.setText("Bitte alle Felder ausfüllen!");
                     } else {

                         //TODO neue Webcam in der Datenbank speichern (dafür wird noch die ID des angemeldeten Benutzers benötigt!)
                        // TODO die ID der gespeicherten Webcam abrufen und dynamisch übergeben. Hie rwird als beispiel einfach die ID 1 eingetragen.

                         //Save the new ID in the local config file
                         fileHandler fh = new fileHandler();
                         fh.saveFile("webcam_id.config", "1", getApplicationContext());

                         //Go to WebcamPreview Activity
                         Intent goToWebcamPreviewActivity = new Intent(WebcamRegistrationActivity.this, WebcamPreviewActivity.class);
                         startActivity(goToWebcamPreviewActivity);

                     }


                 }

             }
        );

    };
}

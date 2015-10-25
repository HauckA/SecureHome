package webcam.securehome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alexander on 15.10.2015.
 */
public class WebcamRegistrationActivity extends AppCompatActivity {

    //Toast Error Message
    private static final String MSG_NO_INPUT = "Bitte Beschreibung angeben!";

    //String declaration
    private String webcamDescription = null;

    //EditText declaration
    private EditText txtWebcamDescription = null;

    //Button declaration
    private Button btnRegistrateWebcam = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webcamregistration_layout);

        //EditText generation
        this.txtWebcamDescription = (EditText) findViewById(R.id.txtWebcamDescription);

        //Button generation
        this.btnRegistrateWebcam = (Button) findViewById(R.id.btnRegistrateWebcam);

        this.btnRegistrateWebcam.setOnClickListener(
             new Button.OnClickListener() {

                 @Override
                 public void onClick(View v) {

                    webcamDescription = txtWebcamDescription.getText().toString();

                     //Check if value is set
                     if(webcamDescription.equals("")) {
                         Toast.makeText(v.getContext(), MSG_NO_INPUT, Toast.LENGTH_LONG).show();
                     } else {

                         new WebcamRegistration(WebcamRegistrationActivity.this).execute(webcamDescription);
                     }


                 }

             }
        );

    };
}

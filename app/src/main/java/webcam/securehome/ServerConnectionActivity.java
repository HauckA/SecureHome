package webcam.securehome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Diese Klasse stellt die Verbindung zu Webserver/Datenbank her.
 * Ist der Startpunkt der App.
 */
public class ServerConnectionActivity extends AppCompatActivity {

   //Deklaration
    private String serverIpAdress = null;
    private Button btnStartConnection = null;
    private EditText txtIpAdress = null;

    //FileHandler
    private FileHandler fh = new FileHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serverconnection_layout);

        //Generation
        this.btnStartConnection = (Button) findViewById(R.id.btnServerConnection);
        this.txtIpAdress = (EditText) findViewById(R.id.txtIpAdress);

        //Read IP Adress vom Config-File
        String ipAdress = fh.getFileContent("ip_adress.config", getApplicationContext()).trim();
        txtIpAdress.setText(ipAdress);

        //Listener
        this.btnStartConnection.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {

                    serverIpAdress = txtIpAdress.getText().toString().trim();

                    //Save the given IP Adress in Config File for use in future
                    fh.saveFile("ip_adress.config", serverIpAdress, getApplicationContext());

                    //TODO Verbindung zu Server herstellen, wenn OK weiterleiten auf Login
                    new Loadcategory().execute();
                }
            }
        );

    }
    class Loadcategory extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Intent intent = new Intent(ServerConnectionActivity.this, LoginActivity.class);
            startActivity(intent);
            return null;
        }

        protected void onPostExecute(Void param) { }
    } // AsyncTask over
}//main class over


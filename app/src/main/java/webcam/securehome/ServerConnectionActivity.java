package webcam.securehome;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serverconnection_layout);

        //Generation
        this.btnStartConnection = (Button) findViewById(R.id.btnServerConnection);
        this.txtIpAdress = (EditText) findViewById(R.id.txtIpAdress);

        //Listener
        this.btnStartConnection.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {

                    serverIpAdress = txtIpAdress.getText().toString();

                    //TODO Verbindung zu Server herstellen, wenn OK weiterleiten auf Login

                    Intent intent = new Intent(ServerConnectionActivity.this, LoginActivity.class);
                    startActivity(intent);


                }
            }
        );
    }
}

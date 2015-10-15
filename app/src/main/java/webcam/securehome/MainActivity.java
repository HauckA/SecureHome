package webcam.securehome;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import webcam.securehome.R;

public class MainActivity extends AppCompatActivity {

    //EditText deklarieren
    private EditText txtUsername = null;
    private EditText txtPassword = null;

    //Button deklarieren
    private Button btnLogin = null;

    //TODO Config-File einlesen und auswerten
    //TODO Verbindung zum Webserver herstellen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //EditText generieren
        this.txtUsername = (EditText) findViewById(R.id.txtUsername);
        this.txtPassword = (EditText) findViewById(R.id.txtPassword);

        //Button generieren
        this.btnLogin = (Button) findViewById(R.id.btnLogin);

        //Login-Button Listener
        this.btnLogin.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        // TODO Login-Funktionalität
                    }
                }
        );

    }



}

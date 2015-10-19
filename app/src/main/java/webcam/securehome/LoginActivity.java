package webcam.securehome;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Diese Klasse ist zuständig für das Login.
 */
public class LoginActivity extends AppCompatActivity {

    private String username = null;
    private String password = null;

    //EditText deklarieren
    private EditText txtUsername = null;
    private EditText txtPassword = null;

    //TextView deklarieren
    private TextView txtErrorMessage = null;

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

        //TextView generieren
        this.txtErrorMessage = (TextView) findViewById(R.id.txtErrorMessage);

        //Button generieren
        this.btnLogin = (Button) findViewById(R.id.btnLogin);

        //Login-Button Listener
        this.btnLogin.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        username = txtUsername.getText().toString();
                        password = txtPassword.getText().toString();

                        if(username.equals("") || password.equals("")) {
                            txtErrorMessage.setText("Bitte alle Felder ausfüllen.");
                        } else {
                            txtErrorMessage.setText("");

                            // TODO Login-Funktionalität
                            // TODO schauen ob Gerät registriert, falls ja auf webcamactivity weiterleiten, sonst auf webcamregistration

                            Intent intent = new Intent(LoginActivity.this, WebcamActivity.class);

                            startActivity(intent);

                        }


                    }
                }
        );

    }



}

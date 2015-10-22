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

    //String declaration
    private String username = null;
    private String password = null;

    //EditText declaration
    private EditText txtUsername = null;
    private EditText txtPassword = null;

    //TextView declaration
    private TextView txtErrorMessage = null;
    private TextView txtNoAccount = null;

    //Button declaration
    private Button btnLogin = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //EditText generation
        this.txtUsername = (EditText) findViewById(R.id.txtUsername);
        this.txtPassword = (EditText) findViewById(R.id.txtPassword);

        //TextView generation
        this.txtErrorMessage = (TextView) findViewById(R.id.txtErrorMessage);
        this.txtNoAccount = (TextView) findViewById(R.id.txtNoAccount);

        //Button generation
        this.btnLogin = (Button) findViewById(R.id.btnLogin);

        //TODO Datei einlesen, um zu überprüfen ob das aktuelle Gerät bereits als Webcam registriert wurde

        //Login-Button Listener
        this.btnLogin.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {

                    txtErrorMessage.setText("");
                    username = txtUsername.getText().toString();
                    password = txtPassword.getText().toString();

                    if(username.equals("") || password.equals("")) {
                        txtErrorMessage.setText("Bitte alle Felder ausfüllen.");
                    } else {

                        // TODO Überprüfen ob Login-Daten korrekt
                        new Login(LoginActivity.this).execute(username, password);
                    }
                }
            }
        );

        this.txtNoAccount.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {

                    Intent goToUserRegistration = new Intent(LoginActivity.this, UserRegistrationActivity.class);
                    startActivity(goToUserRegistration);
                }
            }
        );

    }

}

package webcam.securehome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Diese Klasse ist zuständig für das Login.
 */
public class LoginActivity extends AppCompatActivity {

    //Toast Error Message
    private static final String MSG_NO_INPUT = "Bitte alle Felder ausfüllen!";

    //String declaration
    private String username = null;
    private String password = null;

    //EditText declaration
    private EditText txtUsername = null;
    private EditText txtPassword = null;

    //TextView declaration
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
        this.txtNoAccount = (TextView) findViewById(R.id.txtNoAccount);

        //Button generation
        this.btnLogin = (Button) findViewById(R.id.btnLogin);

        //Login-Button Listener
        this.btnLogin.setOnClickListener(
            new Button.OnClickListener() {
                public void onClick(View v) {

                    username = txtUsername.getText().toString();
                    password = txtPassword.getText().toString();

                    //Check if all fields are filled with values
                    if(username.equals("") || password.equals("")) {
                        Toast.makeText(v.getContext(), MSG_NO_INPUT, Toast.LENGTH_LONG).show();
                    } else {

                        //Check login
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

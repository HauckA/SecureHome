package webcam.securehome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import webcam.securehome.R;

/**
 * Diese Klasse ist zuständig für die Registrierung eines neuen Benutzers
 */
public class UserRegistrationActivity extends AppCompatActivity {

    //Strings declaration
    private String firstname = null;
    private String lastname = null;
    private String email = null;
    private String username = null;
    private String password1 = null;
    private String password2 = null;

    //EditText declaration
    private EditText txtFirstname = null;
    private EditText txtLastname = null;
    private EditText txtEmail = null;
    private EditText txtUsername = null;
    private EditText txtPassword1 = null;
    private EditText txtPassword2 = null;

    //Textview declaration
    private TextView txtErrorMessage = null;

    //Button declaration
    private Button btnRegistrateUser = null;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.userregistration_layout);

        //EditText generation
        this.txtFirstname = (EditText) findViewById(R.id.txtFirstname);
        this.txtLastname = (EditText) findViewById(R.id.txtLastname);
        this.txtEmail = (EditText) findViewById(R.id.txtEmail);
        this.txtUsername = (EditText) findViewById(R.id.txtUsername);
        this.txtPassword1 = (EditText) findViewById(R.id.txtPassword1);
        this.txtPassword2 = (EditText) findViewById(R.id.txtPassword2);

        //TextView generation
        this.txtErrorMessage = (TextView) findViewById(R.id.txtErrorMessageRegistration);

        //Button generation
        this.btnRegistrateUser = (Button) findViewById(R.id.btnRegistrateUser);

        //Listener for Registration-Button
        this.btnRegistrateUser.setOnClickListener(
           new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Delete ErrorMessage
                txtErrorMessage.setText("");

                //Assign EditText values to the strings
                firstname = txtFirstname.getText().toString();
                lastname = txtLastname.getText().toString();
                email = txtEmail.getText().toString();
                username = txtUsername.getText().toString();
                password1 = txtPassword1.getText().toString();
                password2 = txtPassword2.getText().toString();

                //Check if all values are set
                if(firstname.equals("") || lastname.equals("") || email.equals("") || username.equals("") || password1.equals("") || password2.equals("")) {
                    txtErrorMessage.setText("Bitte alle Felder ausfüllen!");
                }

                // Check if Password1 and Password2 have the same value
                else if (!password1.equals(password2)) {
                    txtErrorMessage.setText("Die Passwörter stimmen nicht überein!");
                    txtPassword1.setText("");
                    txtPassword2.setText("");
                }
                else {
                    //TODO Fill in this values to the database, show to the user that everything is ok and go back to the Login Screen.
                }
            }
        }
        );




    };

}

package webcam.securehome;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String username = null;
    private String password = null;

    //EditText deklarieren
    private EditText txtUsername = null;
    private EditText txtPassword = null;

    //TextView deklarieren
    private TextView txtErrorMessage = null;

    //Button deklarieren
    private Button btnLogin = null;





    UserFunctions userFunction = new UserFunctions();

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
                            //Execute Login and show next View
                            new Login(MainActivity.this).execute(username, password);
                        }


                    }
                }
        );

    }



}

package individual.kpi.project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by masrina on 3/9/14.
 */
public class LogoutActivity extends Activity {
    private EditText username = null;
    private EditText password = null;
    private Button loginBtn;
    int counter =  3;
    private TextView attempts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // to make current activity full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.btnLogin);

    }

    public void login(View view){
        if(username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Wrong Username or Password",
                    Toast.LENGTH_SHORT).show();
//            counter--;
//            attempts.setText(Integer.toString(counter));
//            if(counter==0){
//                loginBtn.setEnabled(false);
//            }
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//
//    }
}

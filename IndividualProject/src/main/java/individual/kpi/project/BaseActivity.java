package individual.kpi.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by masrina on 5/18/14.
 */
public class BaseActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        authenticate(this, getIntent());
    }
    public static void authenticate(final Context context, final Intent intent){
        Intent i = new Intent(context, LoginActivity2.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(String.valueOf(LoginActivity2.DUMMY_CREDENTIALS), intent);
        context.startActivity(i);
    }
}

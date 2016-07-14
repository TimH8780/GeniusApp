package unknown.thegeniusapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Unknown on 7/13/2016.
 */
public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onBackPressed(){
        finish();
    }
}

package unknown.thegeniusapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 *Created by Unknown on 7/11/2016.
 */
public class OfflineMode extends AppCompatActivity{

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_mode_window);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //Testing the random number generator to see if it gives same output every time
        UnknownFunctionGenerator test = new UnknownFunctionGenerator();
        int testing = test.RandomGenerator();
        Log.d("Number", Double.toString(testing));
        while (testing > 0){
            testing = test.RandomGenerator();
            Log.d("Number", Double.toString(testing));
        }

    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void back(View view){
        Intent intent = new Intent(this, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }
}

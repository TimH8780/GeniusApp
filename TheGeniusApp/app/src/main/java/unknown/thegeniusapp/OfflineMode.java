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

    private long final_answer;
    int tempCounter = 100;
    int testing;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_mode_window);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        //Initializes the class and generates the answer, can be used to compare with players' answers
        UnknownFunctionGenerator test = new UnknownFunctionGenerator();
        final_answer = test.unknownFunctionGenerator();

        //Used to test the random number generator, UnknownFunctionGenerator::randomGenerator()
//        while (tempCounter > 0){
//            testing = test.randomGenerator();
//            Log.d("Number", Long.toString(testing));
//            tempCounter--;
//        }
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

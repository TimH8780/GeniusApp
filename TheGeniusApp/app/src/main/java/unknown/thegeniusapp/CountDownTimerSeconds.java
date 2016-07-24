package unknown.thegeniusapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Unknown on 7/17/2016.
 */
public class CountDownTimerSeconds extends AppCompatActivity {

    long time_left = 0;
    private CountDownTimer counter;
    private static long frequency = 1000;
    boolean pause = false;


    public void start(long total_seconds){
        counter = new CountDownTimer(total_seconds*frequency, frequency) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left = millisUntilFinished/frequency;
                Log.d("start: ", Long.toString(time_left));
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void stop(){
        counter.cancel();
        Log.d("stop: ", Long.toString(time_left));
    }

    public void resume() {
        Log.d("stop: ", Long.toString(time_left));
        counter = new CountDownTimer(time_left*frequency, frequency) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left = millisUntilFinished/frequency;
                Log.d("resume: ", Long.toString(time_left));
            }

            @Override
            public void onFinish() {
                time_left = 0;
            }
        }.start();

    }
}

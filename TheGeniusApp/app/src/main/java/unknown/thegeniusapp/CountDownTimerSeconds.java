package unknown.thegeniusapp;

import android.os.CountDownTimer;
import android.util.Log;

/**
 *Created by Unknown on 7/17/2016.
 */
public class CountDownTimerSeconds {

    public static final long SECOND_TO_MILLISECOND = 1000;
    public static final long FREQUENCY = 200;

    private long time_left;
    private Timer counter;
    private boolean pause;
    private boolean ongoing;
    private String id;

    private class Timer extends CountDownTimer{

        private Timer(long millisInFuture) {
            super(millisInFuture, FREQUENCY);
            time_left = millisInFuture;
            pause = false;
            ongoing = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            time_left = millisUntilFinished;
            Log.d("Ongoing - " + id, Double.toString(time_left / 1000.0));
        }

        @Override
        public void onFinish() {
            time_left = 0;
            ongoing = false;
            Log.d("FINISH - " + id, "");
        }
    }

    public CountDownTimerSeconds(long total_second, String id){
        counter = new Timer(total_second * SECOND_TO_MILLISECOND);
        this.id = id;
    }

    public void start(){
        if(!ongoing) {
            counter.start();
            pause = false;
            ongoing = true;
        }
        Log.d("start - " + id, Double.toString(time_left / 1000.0));
    }

    public void stop(){
        if(ongoing) {
            counter.cancel();
            pause = false;
            time_left = 0;
            ongoing = false;
        }
        Log.d("stop - " + id, Double.toString(time_left / 1000.0));
    }

    public void pause(){
        if(ongoing) {
            counter.cancel();
            pause = true;
            ongoing = false;
        }
        Log.d("pause - " + id, Double.toString(time_left / 1000.0));
    }

    public void resume(){
        if(pause && !ongoing) {
            counter = new Timer(time_left);
            counter.start();
            pause = false;
            ongoing = true;
        }
        Log.d("resume - " + id, Double.toString(time_left / 1000.0));
    }

    public boolean isPaused(){ return  pause; }
    public boolean isFinished(){
        return time_left == 0;
    }
    public long getTimerTime(){ return time_left; }
}

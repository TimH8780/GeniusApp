package unknown.thegeniusapp;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import static unknown.thegeniusapp.OfflineMode.*;

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
    private OfflineMode game;

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
            if(id.equals(ROUND_ID) && game != null){
                Toast.makeText(game.getApplicationContext(), "Time Up for this Round!", Toast.LENGTH_SHORT).show();
                game.nextRound();
            }
        }
    }

    public CountDownTimerSeconds(long total_second, String id, OfflineMode game){
        counter = new Timer(total_second * SECOND_TO_MILLISECOND);
        this.id = id;
        this.game = game;
    }

    public CountDownTimerSeconds(long total_second, String id){
        counter = new Timer(total_second * SECOND_TO_MILLISECOND);
        this.id = id;
        this.game = null;
    }

    public void start(){
        if(!ongoing) {
            counter.start();
            pause = false;
            ongoing = true;
        }
    }

    public void stop(){
        if(ongoing) {
            counter.cancel();
            pause = false;
            time_left = 0;
            ongoing = false;
        }
    }

    public void pause(){
        if(ongoing) {
            counter.cancel();
            pause = true;
            ongoing = false;
        }
    }

    public void resume(){
        if(pause && !ongoing) {
            counter = new Timer(time_left);
            counter.start();
            pause = false;
            ongoing = true;
        }
    }

    public boolean isPaused(){ return  pause; }
    public boolean isFinished(){
        return time_left == 0;
    }
    public long getTimerTime(){ return time_left; }
}

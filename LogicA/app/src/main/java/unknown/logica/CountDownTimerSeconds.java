package unknown.logica;

import android.os.CountDownTimer;

import static unknown.logica.Game.*;

public class CountDownTimerSeconds {

    public static final long SECOND_TO_MILLISECOND = 1000;
    public static final long FREQUENCY = 200;

    private long timeLeft;
    private Timer counter;
    private boolean isPaused;
    private boolean isCountingDown;
    private String id;
    private Game game;

    private class Timer extends CountDownTimer{

        private Timer(long millisInFuture) {
            super(millisInFuture, FREQUENCY);
            timeLeft = millisInFuture;
            isPaused = true;
            isCountingDown = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(timeLeft != millisUntilFinished){
                switch (id){
                    case ROUND_ID:
                        game.updateGameTime(millisUntilFinished);
                        break;
                    case HINT_ID:
                        game.updateHintTime(millisUntilFinished);
                        break;
                    case ANSWER_ID:
                        game.updateAnswerTime(millisUntilFinished);
                        break;
                    case PENALTY_ID:
                        game.updatePenaltyTime(millisUntilFinished);
                        break;
                }
            }
            timeLeft = millisUntilFinished;
        }

        @Override
        public void onFinish() {
            timeLeft = 0;
            isCountingDown = false;
            if(isGameTimer()){
                game.nextRound();
            } else if(isPenaltyTimer()){
                game.unlockAnswerButton();
            }
        }
    }

    private boolean isGameTimer(){
        return id.equals(ROUND_ID);
    }

    private boolean isPenaltyTimer(){
        return id.equals(PENALTY_ID);
    }

    public CountDownTimerSeconds(long total_second, String id, Game game){
        counter = new Timer(total_second * SECOND_TO_MILLISECOND);
        this.id = id;
        this.game = game;
    }

    public void start(){
        if(!isCountingDown) {
            counter.start();
            isPaused = false;
            isCountingDown = true;
        }
    }

    public void stop(){
        if(isCountingDown) {
            counter.cancel();
            isPaused = false;
            timeLeft = 0;
            isCountingDown = false;
        }
    }

    public void pause(){
        if(isCountingDown) {
            counter.cancel();
            isPaused = true;
            isCountingDown = false;
        }
    }

    public void resume(){
        if(isPaused && !isCountingDown) {
            counter = new Timer(timeLeft);
            counter.start();
            isPaused = false;
            isCountingDown = true;
        }
    }

    public boolean isPaused(){ return  isPaused; }
    public boolean isFinished(){
        return timeLeft == 0;
    }
    public long getTimerTime(){ return timeLeft; }
    public boolean isRunning(){ return  isCountingDown; }
}

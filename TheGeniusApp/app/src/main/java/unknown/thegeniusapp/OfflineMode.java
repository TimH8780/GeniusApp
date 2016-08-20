package unknown.thegeniusapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import static unknown.thegeniusapp.CountDownTimerSeconds.*;
import static unknown.thegeniusapp.ModeSelection.*;

public class OfflineMode extends AppCompatActivity{

    public static final int NUMBER_OF_HINTS = 6;
    public static final int ROUND_MAX_VALUE = 50;
    public static final int HINT_MAX_VALUE = 100;
    public static final int SECOND_PER_ROUND = 181;
    public static final int HINT_INPUT_WAIT_TIME = 20;
    public static final int ANSWER_WAIT_TIME = 10;
    public static final int PENALTY_TIME = 30;
    public static final String ROUND_ID = "Round";
    public static final String HINT_ID = "Hint";
    public static final String ANSWER_ID = "Answer";
    public static final String PENALTY_ID = "Penalty";

    private Button player1_button;
    private Button player2_button;
    private TextView player1_score;
    private TextView player2_score;
    private TextView player1_answerTime;
    private TextView player2_answerTime;
    private TextView input1_view;
    private TextView input2_view;
    private TextView timeout;
    private ImageView settings_button;
    private TextView[] hint_inputs = new TextView[12];
    private TextView[] hint_answer = new TextView[6];
    private int answerActive = 0;

    private UnknownFunctionGenerator unknownFunction;
    private CountDownTimerSeconds[] countDown;
    private ObtainHintsForRound hintTimer;
    private Timer timer;
    private long final_answer;
    private int input1;
    private int input2;
    private int hintIndex;
    private HashMap<TextView, Boolean> hintTable;
    private AlertDialog dialog;
    private String gameType;
    private int RoundCounter;
    private int gameLimit;
    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offline_mode_window);

        // Remove action bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        createPlayBGM();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            gameType = bundle.getString("Type");
            gameLimit = bundle.getInt("Limit");
        } else {
            // For debug purpose
            throw new Error("No Extra Bundle");
        }
        RoundCounter = 1;

        // Obtain two random inputs and display them
        input1 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);
        input2 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);

        timeout = (TextView) findViewById(R.id.timeout);

        View questionContainer = findViewById(R.id.question);
        input1_view = (TextView) questionContainer.findViewById(R.id.input1);
        input2_view = (TextView) questionContainer.findViewById(R.id.input2);
        input1_view.setText(String.valueOf(input1));
        input2_view.setText(String.valueOf(input2));

        // Add Click event and touch effect to the settings button (image)
        settings_button = (ImageView) questionContainer.findViewById(R.id.settings_button);
        settings_button.setOnClickListener(pause_button);
        settings_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        view.setImageResource(R.drawable.pause_icon_blue);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        view.setImageResource(R.drawable.pause_icon_black);
                        break;
                }
                return false;
            }
        });

        // Initialize players' answer buttons and their function
        player1_button = (Button) findViewById(R.id.player1_button);
        player2_button = (Button) findViewById(R.id.player2_button);
        player1_button.setOnClickListener(answer_button);
        player2_button.setOnClickListener(answer_button);

        // Initialize player's score field
        player1_score = (TextView) findViewById(R.id.player1_score);
        player2_score = (TextView) findViewById(R.id.player2_score);
        player1_answerTime = (TextView) findViewById(R.id.player1_answer_time);
        player2_answerTime = (TextView) findViewById(R.id.player2_answer_time);

        // Initialize all hint input 'buttons'
        View hint1 = findViewById(R.id.hint_1);
        View hint2 = findViewById(R.id.hint_2);
        View hint3 = findViewById(R.id.hint_3);
        View hint4 = findViewById(R.id.hint_4);
        View hint5 = findViewById(R.id.hint_5);
        View hint6 = findViewById(R.id.hint_6);
        hint_inputs[0] = (TextView) hint1.findViewById(R.id.num1);
        hint_inputs[1] = (TextView) hint1.findViewById(R.id.num2);
        hint_inputs[2] = (TextView) hint2.findViewById(R.id.num1);
        hint_inputs[3] = (TextView) hint2.findViewById(R.id.num2);
        hint_inputs[4] = (TextView) hint3.findViewById(R.id.num1);
        hint_inputs[5] = (TextView) hint3.findViewById(R.id.num2);
        hint_inputs[6] = (TextView) hint4.findViewById(R.id.num1);
        hint_inputs[7] = (TextView) hint4.findViewById(R.id.num2);
        hint_inputs[8] = (TextView) hint5.findViewById(R.id.num1);
        hint_inputs[9] = (TextView) hint5.findViewById(R.id.num2);
        hint_inputs[10] = (TextView) hint6.findViewById(R.id.num1);
        hint_inputs[11] = (TextView) hint6.findViewById(R.id.num2);
        for(TextView view: hint_inputs){
            view.setClickable(false);
        }

        // Initialize all hint answer fields
        hint_answer[0] = (TextView) hint1.findViewById(R.id.ans);
        hint_answer[1] = (TextView) hint2.findViewById(R.id.ans);
        hint_answer[2] = (TextView) hint3.findViewById(R.id.ans);
        hint_answer[3] = (TextView) hint4.findViewById(R.id.ans);
        hint_answer[4] = (TextView) hint5.findViewById(R.id.ans);
        hint_answer[5] = (TextView) hint6.findViewById(R.id.ans);

        // Initializes the function generator and generates the answer, can be used to compare with players' answers
        unknownFunction = new UnknownFunctionGenerator();
        final_answer = unknownFunction.getResult(input1, input2);

        // Initializes four CountDownTimers
        countDown = new CountDownTimerSeconds[4];
        countDown[0] = new CountDownTimerSeconds(SECOND_PER_ROUND, ROUND_ID, this);             // 0: Round Timer
        countDown[1] = new CountDownTimerSeconds(HINT_INPUT_WAIT_TIME, HINT_ID, this);         // 1: HintChecker Timer
        countDown[2] = new CountDownTimerSeconds(ANSWER_WAIT_TIME, ANSWER_ID, this);           // 2: Answer Timer
        countDown[3] = new CountDownTimerSeconds(PENALTY_TIME, PENALTY_ID, this);              // 3: Penalty Timer

        // Start round timer and obtain first hint inputs
        hintIndex = 0;
        countDown[0].start();
        callHintTimer();
    }

    @Override
    public void onBackPressed(){
        // Disable back button
    }

    @Override
    protected void onPause(){
        super.onPause();
        musicPlayer.pause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        musicPlayer.release();
    }

    @Override
    protected void onResume(){
        super.onResume();
        createPlayBGM();
    }

    private void createPlayBGM(){
        if(musicPlayer != null) {
            try {
                musicPlayer.reset();
            } catch(IllegalStateException e){
                musicPlayer = null;
            }
        }
        musicPlayer = MediaPlayer.create(OfflineMode.this, R.raw.sample2);
        musicPlayer.start();
        musicPlayer.setLooping(true);
    }

    // The onClickListener for the two answer buttons
    private View.OnClickListener answer_button = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            // Ensure only one button can invoke the listener
            if(answerActive == 0) {
                answerActive = getPlayerIndex(v);

                // Pause timers
                countDown[0].pause();
                countDown[1].pause();
                countDown[3].pause();

                // Build the answer dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMode.this);
                String name = getPlayerName(v);
                builder.setTitle("Enter your (" + name + ") answer:");
                builder.setCancelable(false);
                LayoutInflater inflater = getLayoutInflater();
                View popupView = inflater.inflate(R.layout.answer_popup, null);
                final EditText answer = (EditText) popupView.findViewById(R.id.answer);
                builder.setView(popupView);

                // Submit event
                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check answer
                        int change;
                        if (answer.getText().length() > 0 && Long.valueOf(answer.getText().toString()) == final_answer) {
                            change = 1;
                            Toast.makeText(OfflineMode.this, "Congratulation! Correct Answer!", Toast.LENGTH_LONG).show();
                        } else {
                            change = -1;
                            setPenalty((Button)v);
                            Toast.makeText(OfflineMode.this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
                        }

                        // Update score
                        updateScore(change, v);
                        countDown[2].stop();

                        // Resume all paused timers
                        countDown[0].resume();
                        countDown[1].resume();
                        countDown[3].resume();
                        answerActive = 0;
                        if (change == 1) {
                            nextRound();
                        }
                    }
                });

                // Display dialog and execute AnswerChecker
                AlertDialog alertDialog = builder.create();
                new AnswerChecker(v, alertDialog).execute();
                alertDialog.show();
            }
        }
    };

    private void setPenalty(Button v){
        Button otherPlayerButton = (v == player1_button)? player2_button: player1_button;
        if(!otherPlayerButton.isEnabled()){
            countDown[3].stop();
            otherPlayerButton.setEnabled(true);
            otherPlayerButton.setText("Answer");
        }

        countDown[3] = new CountDownTimerSeconds(PENALTY_TIME, PENALTY_ID, OfflineMode.this);
        v.setEnabled(false);
        v.setText(String.format(Locale.US, "Disabled\n00:%02d", PENALTY_TIME));
        countDown[3].start();
    }

    // Update the score on either player
    private void updateScore(int change, View view){
        TextView score = (view == player1_button)? player1_score: player2_score;
        int currentScore = Integer.valueOf(score.getText().toString());
        score.setText(String.valueOf(currentScore + change));
    }

    // Obtain the name of either player
    private String getPlayerName(View view){
        return (view == player1_button)? "Player 1": "Player 2";
    }

    // Obtain the player #
    private int getPlayerIndex(View view){
        return (view == player1_button)? 1: 2;
    }

    // The onClickListener for the settings (pause) buttons
    private View.OnClickListener pause_button = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            // Pause all timers
            for(CountDownTimerSeconds timer: countDown) {
                timer.pause();
            }

            // Build the settings dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMode.this);
            builder.setTitle("Game Paused");
            builder.setCancelable(false);
            LayoutInflater inflater = getLayoutInflater();
            View popupView = inflater.inflate(R.layout.game_pause_popup, null);
            builder.setView(popupView);
            final AlertDialog alertDialog = builder.create();

            // Function of resume button
            Button resume = (Button) popupView.findViewById(R.id.resume_button);
            resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Resume all paused timers and close dialog
                    for(CountDownTimerSeconds timer: countDown) {
                        timer.resume();
                    }
                    alertDialog.dismiss();
                }
            });

            // Function of settings button
            Button settings = (Button) popupView.findViewById(R.id.settings_button);
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OfflineMode.this, Settings.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            });

            // Function of quit button
            Button quit = (Button) popupView.findViewById(R.id.quit_button);
            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Stop all timers, quit game, and return to main page
                    for(CountDownTimerSeconds timer: countDown) {
                        timer.stop();
                    }
                    alertDialog.dismiss();
                    timer.cancel();
                    finish();
                }
            });

            // Display dialog
            alertDialog.show();
        }
    };

    // Clear all fields and timers for next round
    protected void nextRound(){
        // Stop timer for getting hints
        hintTimer.cancel(true);

        // Regenerate two numbers, unknown function and answer
        input1 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);
        input2 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);
        input1_view.setText(String.valueOf(input1));
        input2_view.setText(String.valueOf(input2));
        unknownFunction = new UnknownFunctionGenerator();
        final_answer = unknownFunction.getResult(input1, input2);

        // Clear all fields
        for(TextView view: hint_inputs){
            view.setText("");
        }
        for(TextView view: hint_answer){
            view.setText("");
        }

        // Stop all timers
        for(CountDownTimerSeconds timer: countDown) {
            timer.stop();
        }

        // Check whether there is a next round
        if(!endGame()) {
            // Reset and restart round timer and timer for getting hints
            RoundCounter++;
            countDown[0] = new CountDownTimerSeconds(SECOND_PER_ROUND, ROUND_ID, this);
            hintIndex = 0;
            timeout.setText(String.format(Locale.US, "Round_%s - %d Seconds (%d)", RoundCounter, SECOND_PER_ROUND, HINT_INPUT_WAIT_TIME));
            countDown[0].start();
        } else {
            endGameDialog();
        }
    }

    protected void updateGameTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / 1000);
        timeout.setText(String.format(Locale.US, "Round_%s - %d Seconds (%d)", RoundCounter, second, HINT_INPUT_WAIT_TIME));
        player1_answerTime.setText(String.format(Locale.US, "00:%02d", ANSWER_WAIT_TIME));
        player2_answerTime.setText(String.format(Locale.US, "00:%02d", ANSWER_WAIT_TIME));
    }

    protected void updateHintTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / 1000);
        String temp = timeout.getText().toString();
        temp = temp.substring(0, temp.indexOf('('));
        timeout.setText(String.format(Locale.US, "%s(%d)", temp, second));
    }

    protected void updateAnswerTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / 1000);
        switch (answerActive){
            case 1:
                player1_answerTime.setText(String.format(Locale.US, "00:%02d", second));
                break;
            case 2:
                player2_answerTime.setText(String.format(Locale.US, "00:%02d", second));
                break;
        }
    }

    protected void updatePenaltyTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / 1000);
        if(!player1_button.isEnabled()){
            player1_button.setText(String.format(Locale.US, "Disabled\n00:%02d", second));
        } else if(!player2_button.isEnabled()){
            player2_button.setText(String.format(Locale.US, "Disabled\n00:%02d", second));
        }
    }

    protected void unlockAnswerButton(){
        player1_button.setText("Answer");
        player2_button.setText("Answer");
        player1_button.setEnabled(true);
        player2_button.setEnabled(true);
    }

    // Check if the game should be ended
    private boolean endGame(){
        int p1_score = Integer.valueOf(player1_score.getText().toString());
        int p2_score = Integer.valueOf(player2_score.getText().toString());
        if(p1_score == p2_score){
            // Over Time if both players have same score
            return false;
        }

        if(gameType.equals(score)) {
            // Score Mode
            return Math.abs(p1_score) >= gameLimit || Math.abs(p2_score) >= gameLimit;
        }
        // Round Mode
        return RoundCounter >= gameLimit;
    }

    // Get the winning player
    private String getWinner(){
        int p1_score = Integer.valueOf(player1_score.getText().toString());
        int p2_score = Integer.valueOf(player2_score.getText().toString());
        return (p1_score > p2_score)? "Player 1": "Player 2";
    }

    // Build the end game dialog
    private void endGameDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMode.this);
        builder.setTitle("Game Over");
        builder.setMessage("Congratulations! " + getWinner() + " wins the game!!");
        builder.setCancelable(false);
        builder.setPositiveButton("Next Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
                dialog.dismiss();
                Intent intent = new Intent(OfflineMode.this, ModeSelection.class);
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    // Get hint from players for fields, index * 2 and index * 2 + 1
    private void getHintInput(int index){
        if(index > 5){
            return;
        }
        new HintChecker(index).execute();
    }

    // Change the appearance of hint field before and after obtaining hint input from players
    private void changeAppearance(TextView view, boolean before){
        if(before){
            // Change text to "Click", color to RED, and clickable
            view.setText("Click");
            view.setTextColor(0xFFFF0000);
            view.setClickable(true);
        } else {
            // Change color to BLACK, and unclickable
            view.setTextColor(0xFF000000);
            view.setClickable(false);
        }
    }

    // The onClickListener for all hint fields
    public void hintInput(final View view) {
        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMode.this);
        builder.setTitle("Enter your Input for HintChecker:");
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.answer_popup, null);
        final EditText input = (EditText) popupView.findViewById(R.id.answer);
        input.setHint("Integer range from 0 to 9999");
        builder.setView(popupView);

        // Submit event
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Validate the input
                String inputVal = input.getText().toString();
                if(inputVal.length() > 4 || inputVal.equals("") || isEqualQuestion(Integer.valueOf(inputVal))){
                    Toast.makeText(OfflineMode.this, "Invalid Value", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Set hint field to input-value
                ((TextView)view).setText(input.getText().toString());
                hintTable.put((TextView)view, true);
            }
        });

        // Display dialog
        dialog = builder.create();
        dialog.show();
    }

    // Check if the hint input equals either of the question numbers
    private boolean isEqualQuestion(int num){
        return num == input1 || num == input2;
    }

    // Repeatedly call ObtainHintsForRound every 0.45 second
    private void callHintTimer() {
        final Handler handler = new Handler();
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        hintTimer = new ObtainHintsForRound();
                        hintTimer.execute();
                    }
                });
            }
        };
        timer.schedule(task, 0, 450);     // Execute in every 0.45 second
    }

    // AsyncTask #1
    private class HintChecker extends AsyncTask<Void, Void, Void> {
        private int index;
        private TextView left;
        private TextView right;

        private HintChecker(int index){
            this.index = index;
            countDown[1] = new CountDownTimerSeconds(HINT_INPUT_WAIT_TIME, HINT_ID, OfflineMode.this);
            left = hint_inputs[index * 2];
            right = hint_inputs[index * 2 + 1];
        }

        @Override
        protected  void onPreExecute(){
            // Pause timers
            countDown[0].pause();
            countDown[2].pause();
            countDown[3].pause();

            // Create a table to check if hint is entered
            hintTable = new HashMap<>();
            hintTable.put(left, false);
            hintTable.put(right, false);

            // Change the appearance of both hint fields and disable the two answer buttons
            changeAppearance(left, true);
            changeAppearance(right, true);
            player1_button.setClickable(false);
            player2_button.setClickable(false);
            settings_button.setClickable(false);

            // Start HintChecker timer
            countDown[1].start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Loop until the HintChecker timer is done or both hint fields is entered by players
            while(!countDown[1].isFinished() && !(hintTable.get(left) && hintTable.get(right)));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Stop HintChecker timer
            countDown[1].stop();

            // Enable answer buttons and change back the appearance of the hint fields
            player1_button.setClickable(true);
            player2_button.setClickable(true);
            settings_button.setClickable(true);
            changeAppearance(left, false);
            changeAppearance(right, false);

            // Remove the dialog if it is displayed
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }

            // Randomly generate the hint if it is not entered by player
            if(!hintTable.get(left)){
                long temp = -1;
                do {
                    temp = RandomNumberGenerators.randomNumber(HINT_MAX_VALUE);
                } while (isEqualQuestion((int)temp));
                left.setText(String.valueOf(temp));
            }
            if(!hintTable.get(right)){
                long temp = -1;
                do {
                    temp = RandomNumberGenerators.randomNumber(HINT_MAX_VALUE);
                } while (isEqualQuestion((int)temp));
                right.setText(String.valueOf(temp));
            }

            // Calculate the hint answer
            int inp1 = Integer.valueOf(left.getText().toString());
            int inp2 = Integer.valueOf(right.getText().toString());
            hint_answer[index].setText(String.valueOf(unknownFunction.getResult(inp1, inp2)));

            // Resume timers
            countDown[0].resume();
            countDown[2].resume();
            countDown[3].resume();
        }
    }

    // AsyncTask #2
    private class AnswerChecker extends AsyncTask<Void, Void, Void>{
        private View view;
        private AlertDialog dialog;

        private AnswerChecker(View view, AlertDialog dialog){
            this.view = view;
            this.dialog = dialog;
        }

        @Override
        protected  void onPreExecute(){
            // Reset and start Answer Timer
            countDown[2] = new CountDownTimerSeconds(ANSWER_WAIT_TIME, ANSWER_ID, OfflineMode.this);
            countDown[2].start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Loop until the Answer timer is done
            while(!countDown[2].isFinished());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Stop Answer timer
            countDown[2].stop();

            // Remove dialog, deduce score and resume timers if the dialog is displayed
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
                answerActive = 0;
                updateScore(-1, view);
                setPenalty((Button)view);
                countDown[0].resume();
                countDown[1].resume();
                countDown[3].resume();
            }
        }
    }

    // AsyncTask #3
    private class ObtainHintsForRound extends AsyncTask<Void, Boolean, Boolean>{
        private long requireTime;

        public ObtainHintsForRound(){
            // Calculate the timing to get hint inputs from players
            long hintInterval = SECOND_PER_ROUND / NUMBER_OF_HINTS * hintIndex;
            requireTime = (SECOND_PER_ROUND  - hintInterval) * SECOND_TO_MILLISECOND;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Return if timing is valid
            return !(hintIndex > 5 || countDown[0].isPaused() || Math.abs(countDown[0].getTimerTime() - requireTime) > 1000);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Get hint inputs if result is true
            if(result){
                getHintInput(hintIndex++);
            }
        }
    }

}

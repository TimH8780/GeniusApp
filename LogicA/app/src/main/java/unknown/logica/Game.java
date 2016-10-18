package unknown.logica;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Gravity;
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

import unknown.logica.Module.CountDownTimerSeconds;
import unknown.logica.Module.FunctionList;
import unknown.logica.Module.RandomNumberGenerators;
import unknown.logica.Module.UnknownFunctionGenerator;

import static unknown.logica.Module.CountDownTimerSeconds.*;
import static unknown.logica.ModeSelection.*;
import static unknown.logica.Settings.*;
import static unknown.logica.Module.StringContainer.*;

public class Game extends AppCompatActivity{

    public static final int REQUEST_CODE = 1;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int NUMBER_OF_HINTS = 6;
    public static final int ROUND_MAX_VALUE = 50;
    public static final int HINT_MAX_VALUE = 100;
    public static final int SECOND_PER_ROUND = 21;  // 181
    public static final int HINT_INPUT_WAIT_TIME = 5;   // 20
    public static final int ANSWER_WAIT_TIME = 10;
    public static final int PENALTY_TIME = 30;
    public static final int HINT_CHECK_INTERVAL = 400;
    public static final int HINT_CHECK_VALID_RANGE = 1000;
    public static final String ROUND_ID = "Round";
    public static final String HINT_ID = "Hint";
    public static final String ANSWER_ID = "Answer";
    public static final String PENALTY_ID = "Penalty";

    private Button player1_button;
    private Button player2_button;
    private TextView score;
    private TextView input1_view;
    private TextView input2_view;
    private TextView round;
    private TextView gameTime;
    private TextView hintTime;
    private TextView action;
    private TextView[] hint_inputs = new TextView[12];
    private TextView[] hint_answer = new TextView[6];
    private int answerActive = 0;

    private Resources res;
    private UnknownFunctionGenerator unknownFunction;
    private CountDownTimerSeconds[] countDown;
    private HintChecker hintChecker;
    private Timer timer;
    private long final_answer;
    private int input1;
    private int input2;
    private int randomNumber;
    private int hintIndex;
    private int previousAnswerIndex;
    private HashMap<TextView, Boolean> hintTable;
    private AlertDialog hintDialog;
    private AlertDialog settingsDialog;
    private String gameType;
    private int RoundCounter;
    private int gameLimit;
    private MediaPlayer musicPlayer;
    private boolean isInSuddenDeath;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        res = getResources();
        initializeStrings(res);
        createPlayBGM();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            gameType = bundle.getString("Type");
            gameLimit = bundle.getInt("Limit");
        }
        RoundCounter = 1;
        isInSuddenDeath = false;
        previousAnswerIndex = -1;

        // Obtain two random inputs and display them
        input1 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);
        input2 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);
        randomNumber = RandomNumberGenerators.randomNumber(10);

        // Initialize info fields
        score = (TextView) findViewById(R.id.score);
        round = (TextView) findViewById(R.id.round);
        gameTime = (TextView) findViewById(R.id.round_time);
        hintTime = (TextView) findViewById(R.id.hint_time);
        action = (TextView) findViewById(R.id.action);

        View questionContainer = findViewById(R.id.question);
        input1_view = (TextView) questionContainer.findViewById(R.id.input1);
        input2_view = (TextView) questionContainer.findViewById(R.id.input2);
        input1_view.setText(String.valueOf(input1));
        input2_view.setText(String.valueOf(input2));

        // Add Click event and touch effect to the settings button (image)
        ImageView settings_button = (ImageView) findViewById(R.id.settings_button);
        settings_button.setOnClickListener(pause_button);
        settings_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        view.setImageResource(R.drawable.ic_pause_green);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        view.setImageResource(R.drawable.ic_pause_black);
                        break;
                }
                return false;
            }
        });

        // Add Click event to the music button (image)
        final ImageView music_button = (ImageView) findViewById(R.id.mute_button);

        final SharedPreferences sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true)) {
            music_button.setImageResource(R.drawable.ic_speaker);
        } else {
            music_button.setImageResource(R.drawable.ic_mute);
        }

        music_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true)) {
                    music_button.setImageResource(R.drawable.ic_mute);
                    editor.putBoolean(MUSIC_ENABLE_VALUE, false);
                } else {
                    music_button.setImageResource(R.drawable.ic_speaker);
                    editor.putBoolean(MUSIC_ENABLE_VALUE, true);
                }
                editor.apply();
                createPlayBGM();
            }
        });


        // Initialize players' answer buttons and their function
        player1_button = (Button) findViewById(R.id.player1_button);
        player2_button = (Button) findViewById(R.id.player2_button);
        player1_button.setOnClickListener(answer_button);
        player2_button.setOnClickListener(answer_button);

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
        unknownFunction = new UnknownFunctionGenerator(getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE));
        final_answer = unknownFunction.getResult(input1, input2, randomNumber);

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
        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);

        if(musicPlayer != null) try{
            musicPlayer.reset();
        } catch(IllegalStateException e){
            musicPlayer = null;
        }

        if (sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true)) {
            musicPlayer = MediaPlayer.create(Game.this, R.raw.bgm_game);
            musicPlayer.start();
            musicPlayer.setLooping(true);
        }
        else {
            musicPlayer = MediaPlayer.create(Game.this, R.raw.bgm_game);        // Without this the game crashes when quitting the game
        }
    }

    // The onClickListener for the two answer buttons
    private View.OnClickListener answer_button = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Ensure only one button can invoke the listener
            if(answerActive == 0) {
                final Button button = (Button) v;
                answerActive = getPlayerIndex(button);

                // Pause timers
                countDown[0].pause();
                countDown[1].pause();
                countDown[3].pause();

                // Build the answer dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                String name = getPlayerName(button);
                builder.setTitle(String.format(res.getString(R.string.answer_dialog_title), name));
                builder.setCancelable(false);

                LayoutInflater inflater = getLayoutInflater();
                View popupView = inflater.inflate(R.layout.popup_answer, null);
                final EditText answer = (EditText) popupView.findViewById(R.id.answer);
                builder.setView(popupView);

                // Submit Event
                builder.setPositiveButton(submit_string, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check answer
                        int change;
                        if (answer.getText().length() > 0 && Long.valueOf(answer.getText().toString()) == final_answer) {
                            change = 1;
                            Toast.makeText(Game.this, correct_answer_string, Toast.LENGTH_LONG).show();
                        } else {
                            change = -1;
                            setPenalty(button);
                            Toast.makeText(Game.this, incorrect_answer_string, Toast.LENGTH_SHORT).show();
                        }

                        // Update score
                        updateScore(change, button);
                        countDown[2].stop();

                        // Game ends if it is in sudden death mode
                        if(isInSuddenDeath){
                            countDown[0].stop();
                            countDown[1].stop();
                            countDown[3].stop();
                            endGameDialog();
                            return;
                        }

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
                new AnswerChecker(button, alertDialog).execute();
                alertDialog.show();
            }
        }
    };

    // Set the 30 seconds penalty if answer incorrectly
    private void setPenalty(Button button){
        Button otherPlayerButton = (button == player1_button)? player2_button: player1_button;
        if(!otherPlayerButton.isEnabled()){
            countDown[3].stop();
            otherPlayerButton.setEnabled(true);
            otherPlayerButton.setText(answer_string);
        }

        countDown[3] = new CountDownTimerSeconds(PENALTY_TIME, PENALTY_ID, this);
        button.setEnabled(false);
        button.setText(String.format(res.getString(R.string.disabled_time_label), PENALTY_TIME));
        countDown[3].start();
    }

    // Update the score on either player
    private void updateScore(int change, Button button){
        String scoreString = score.getText().toString();
        int player1Score = Integer.valueOf(scoreString.substring(0, scoreString.indexOf(" ")));
        int player2Score = Integer.valueOf(scoreString.substring(scoreString.lastIndexOf(" ") + 1));

        if(button == player1_button) player1Score += change;
        else player2Score += change;

        score.setText(String.format(Locale.US, "%d vs %d", player1Score, player2Score));
    }

    // Obtain the name of either player
    private String getPlayerName(Button button){
        return (button == player1_button)? player1_string: player2_string;
    }

    // Obtain the player #
    private int getPlayerIndex(Button button){
        return (button == player1_button)? PLAYER_1: PLAYER_2;
    }

    // The onClickListener for the settings (pause) buttons
    private View.OnClickListener pause_button = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            // Pause all running timers
            final boolean[] runningTimer = new boolean[countDown.length];
            for(int i = 0; i < countDown.length; i++) {
                if(countDown[i].isRunning()){
                    runningTimer[i] = true;
                    countDown[i].pause();
                }
            }

            // Build the settings dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
            builder.setTitle(game_pause_string);
            builder.setCancelable(false);

            LayoutInflater inflater = getLayoutInflater();
            View popupView = inflater.inflate(R.layout.popup_pause, null);
            builder.setView(popupView);
            settingsDialog = builder.create();

            // Function of resume button
            Button resume = (Button) popupView.findViewById(R.id.resume_button);
            resume.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Resume all paused timers and close dialog
                    for(int i = 0; i < runningTimer.length; i++) {
                        if(runningTimer[i]){
                            countDown[i].resume();
                        }
                    }
                    settingsDialog.dismiss();
                }
            });

            // Function of settings button
            Button settings = (Button) popupView.findViewById(R.id.settings_button);
            settings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Game.this, Settings.class);
                    intent.putExtra("location", "Game");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });

            // Show previous answer
            final Button previous = (Button) popupView.findViewById(R.id.pre_answer);
            if(previousAnswerIndex == -1) previous.setEnabled(false);
            previous.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pair<String, String> item = FunctionList.getInstance(res).getListItem(previousAnswerIndex - 1);
                    AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
                    builder.setTitle(item.first);
                    builder.setMessage(item.second);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Close", null);
                    builder.create().show();
                }
            });

            // Function of quit button
            Button quit = (Button) popupView.findViewById(R.id.quit_button);
            quit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Stop all timers, quit game, and return to main page
                    quitGame();
                }
            });

            // Display dialog
            settingsDialog.show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getBooleanExtra("Result", false)){
                Intent intent = getIntent();
                quitGame();
                startActivity(intent);
            }
        }
    }

    private void quitGame(){
        for(CountDownTimerSeconds timer: countDown) {
            timer.stop();
        }
        if(settingsDialog != null && settingsDialog.isShowing()) {
            settingsDialog.dismiss();
        }
        hintChecker.cancel(true);
        timer.cancel();
        finish();
    }

    // Clear all fields and timers for next round
    public void nextRound(){
        // Display operator index and answer
        Toast toast = Toast.makeText(this, String.format(Locale.US, getString(R.string.time_up_label), unknownFunction.getFunctionIndex(), final_answer), Toast.LENGTH_LONG);
        TextView message = (TextView) toast.getView().findViewById(android.R.id.message);
        if(message != null) message.setGravity(Gravity.CENTER);
        toast.show();
        previousAnswerIndex = unknownFunction.getFunctionIndex();

        // Regenerate two numbers, unknown function and answer
        input1 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);
        input2 = RandomNumberGenerators.randomNumber(ROUND_MAX_VALUE);
        randomNumber = RandomNumberGenerators.randomNumber(10);
        input1_view.setText(String.valueOf(input1));
        input2_view.setText(String.valueOf(input2));
        unknownFunction = new UnknownFunctionGenerator(getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE));
        final_answer = unknownFunction.getResult(input1, input2, randomNumber);

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
        boolean previousSuddenDeathState = isInSuddenDeath;
        if(isInSuddenDeath || !endGame()) {
            // Reset and restart round timer and timer for getting hints
            RoundCounter++;
            countDown[0] = new CountDownTimerSeconds(SECOND_PER_ROUND, ROUND_ID, this);
            hintIndex = 0;
            unlockAnswerButton();
            gameTime.setText(String.valueOf(SECOND_PER_ROUND));
            hintTime.setText(String.valueOf(HINT_INPUT_WAIT_TIME));

            if(isInSuddenDeath){
                round.setText(getString(R.string.suddenDeath));
                if(!previousSuddenDeathState){
                    suddenDeathModeDialog();
                } else {
                    countDown[0].start();
                }
            }
            else{
                round.setText(String.format(res.getString(R.string.round_time_label), RoundCounter));
                countDown[0].start();
            }
        } else {
            endGameDialog();
        }
    }

    public void updateGameTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / SECOND_TO_MILLISECOND);
        gameTime.setText(String.valueOf(second));
    }

    public void updateHintTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / SECOND_TO_MILLISECOND);
        hintTime.setText(String.valueOf(second));
    }

    public void updateAnswerTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / SECOND_TO_MILLISECOND);
        switch (answerActive){
            case PLAYER_1:
                player1_button.setText(String.valueOf(second));
                break;
            case PLAYER_2:
                player2_button.setText(String.valueOf(second));
                break;
        }
    }

    public void updatePenaltyTime(long millisUntilFinished){
        int second = (int)(millisUntilFinished / SECOND_TO_MILLISECOND);
        if(!player1_button.isEnabled()){
            player1_button.setText(String.format(res.getString(R.string.disabled_time_label), second));
        }
        if(!player2_button.isEnabled()){
            player2_button.setText(String.format(res.getString(R.string.disabled_time_label), second));
        }
    }

    public void unlockAnswerButton(){
        player1_button.setText(answer_string);
        player2_button.setText(answer_string);
        player1_button.setEnabled(true);
        player2_button.setEnabled(true);
    }

    // Check if the game should be ended
    private boolean endGame(){
        String scoreString = score.getText().toString();
        int p1_score = Integer.valueOf(scoreString.substring(0, scoreString.indexOf(" ")));
        int p2_score = Integer.valueOf(scoreString.substring(scoreString.lastIndexOf(" ") + 1));

        if(gameType.equals(SCORE)) {
            // Score Mode
            if(Math.abs(p1_score) >= gameLimit || Math.abs(p2_score) >= gameLimit){
                if(p1_score == p2_score){
                    isInSuddenDeath = true;
                    return false;
                }
                return true;
            }
            return false;
        }
        // Round Mode
        if(RoundCounter >= gameLimit){
            if(p1_score == p2_score){
                isInSuddenDeath = true;
                return false;
            }
            return true;
        }
        return false;
    }

    // Get the winning player
    private String getWinner(){
        String scoreString = score.getText().toString();
        int p1_score = Integer.valueOf(scoreString.substring(0, scoreString.indexOf(" ")));
        int p2_score = Integer.valueOf(scoreString.substring(scoreString.lastIndexOf(" ") + 1));

        return (p1_score > p2_score)? player1_string: player2_string;
    }

    // Build the end game dialog
    private void endGameDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setTitle(game_over_string);
        builder.setMessage(String.format(res.getString(R.string.winner_message_label), getWinner()));
        builder.setCancelable(false);

        builder.setPositiveButton(next_game_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
                dialog.dismiss();
                Intent intent = new Intent(Game.this, ModeSelection.class);
                finish();
                startActivity(intent);
            }
        });

        builder.setNegativeButton(quit_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer.cancel();
                dialog.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    // Build the sudden death notification dialog
    private void suddenDeathModeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setTitle(getString(R.string.suddenDeath));
        builder.setMessage(getString(R.string.sudden_death_dialog_message));
        builder.setCancelable(false);

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countDown[0].start();
            }
        });
        builder.create().show();
    }

    // Get hint from players for fields, index * 2 and index * 2 + 1
    private void getHintInput(int index){
        if(index > 5){
            return;
        }
        hintChecker = new HintChecker(index);
        hintChecker.execute();
    }

    // Change the appearance of hint field before and after obtaining hint input from players
    private void changeAppearance(TextView view, boolean before){
        if(before){
            // Change text to "Click", color to RED, and clickable
            view.setText(click_string);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Game.this);
        builder.setTitle(hint_input_title_string);

        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_answer, null);
        final EditText input = (EditText) popupView.findViewById(R.id.answer);
        input.setHint(hint_input_message_string);
        builder.setView(popupView);

        // Submit event
        builder.setPositiveButton(submit_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Validate the input
                String inputVal = input.getText().toString();
                if(inputVal.length() > 4 || inputVal.equals("") || isEqualQuestion(Integer.valueOf(inputVal))){
                    Toast.makeText(Game.this, invalid_value_string, Toast.LENGTH_SHORT).show();
                    return;
                }

                // Set hint field to input-value
                ((TextView)view).setText(input.getText().toString());
                hintTable.put((TextView)view, true);
            }
        });

        // Display dialog
        hintDialog = builder.create();
        hintDialog.show();
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
                        new ObtainHintsForRound().execute();
                    }
                });
            }
        };
        timer.schedule(task, 0, HINT_CHECK_INTERVAL);
    }

    private void setRandomNumber(TextView view){
        int random;
        do {
            random = RandomNumberGenerators.randomNumber(HINT_MAX_VALUE);
        } while (isEqualQuestion(random));

        view.setText(String.valueOf(random));
    }

    // AsyncTask #1
    private class HintChecker extends AsyncTask<Void, Void, Void> {
        private int index;
        private TextView left;
        private TextView right;

        private HintChecker(int index){
            this.index = index;
            countDown[1] = new CountDownTimerSeconds(HINT_INPUT_WAIT_TIME, HINT_ID, Game.this);
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

            // Update the action field
            action.setText(getString(R.string.action_waiting_hint));

            // Start HintChecker timer
            countDown[1].start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Loop until the HintChecker timer is done or both hint fields is entered by players
            while(!countDown[1].isFinished() && !(hintTable.get(left) && hintTable.get(right)) && !isCancelled());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Stop HintChecker timer
            countDown[1].stop();
            hintTime.setText(String.valueOf(HINT_INPUT_WAIT_TIME));
            action.setText(getString(R.string.action_thinking));

            // Enable answer buttons and change back the appearance of the hint fields
            player1_button.setClickable(true);
            player2_button.setClickable(true);
            changeAppearance(left, false);
            changeAppearance(right, false);

            // Remove the dialog if it is displayed
            if(hintDialog != null && hintDialog.isShowing()){
                hintDialog.dismiss();
            }

            // Randomly generate the hint if it is not entered by player
            if(!hintTable.get(left)) setRandomNumber(left);
            if(!hintTable.get(right)) setRandomNumber(right);

            // Calculate the hint answer
            int inp1 = Integer.valueOf(left.getText().toString());
            int inp2 = Integer.valueOf(right.getText().toString());
            hint_answer[index].setText(String.valueOf(unknownFunction.getResult(inp1, inp2, randomNumber)));

            // Resume timers
            countDown[0].resume();
            countDown[2].resume();
            countDown[3].resume();
        }
    }

    // AsyncTask #2
    private class AnswerChecker extends AsyncTask<Void, Void, Void>{
        private Button button;
        private AlertDialog dialog;

        private AnswerChecker(Button view, AlertDialog dialog){
            button = view;
            this.dialog = dialog;
        }

        @Override
        protected  void onPreExecute(){
            // Reset and start Answer Timer
            countDown[2] = new CountDownTimerSeconds(ANSWER_WAIT_TIME, ANSWER_ID, Game.this);
            countDown[2].start();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Loop until the Answer timer is done
            while(!countDown[2].isFinished() && !isCancelled());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Stop Answer timer
            countDown[2].stop();
            button.setText(answer_string);

            // Remove dialog, deduce score and resume timers if the dialog is displayed
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
                answerActive = 0;
                updateScore(-1, button);
                setPenalty(button);

                // Game ends if in sudden death mode
                if(isInSuddenDeath){
                    countDown[0].stop();
                    countDown[1].stop();
                    countDown[3].stop();
                    endGameDialog();
                    return;
                }

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
            return !(hintIndex + 1 > NUMBER_OF_HINTS || countDown[0].isPaused() || Math.abs(countDown[0].getTimerTime() - requireTime) > HINT_CHECK_VALID_RANGE);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // Get hint inputs if result is true
            if(result) getHintInput(hintIndex++);
        }
    }

}

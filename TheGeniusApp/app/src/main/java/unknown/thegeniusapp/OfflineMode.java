package unknown.thegeniusapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 *Created by Unknown on 7/11/2016.
 */
public class OfflineMode extends AppCompatActivity{

    private Button player1_button;
    private Button player2_button;
    private TextView player1_score;
    private TextView player2_score;
    private TextView input1_view;
    private TextView input2_view;
    private TextView[] hint_inputs = new TextView[12];
    private TextView[] hint_answer = new TextView[6];

    private UnknownFunctionGenerator unknownFunction;
    private int final_answer;
    private int input1;
    private int input2;
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

        // Obtain two random inputs and display them
        input1 = (int)Math.round(Math.random() * 1000.0) % 1000;
        input2 = (int)Math.round(Math.random() * 1000.0) % 1000;

        Log.d("Input1", String.valueOf(input1));
        Log.d("Input2", String.valueOf(input2));

        View questionContainer = findViewById(R.id.question);
        input1_view = (TextView) questionContainer.findViewById(R.id.input1);
        input2_view = (TextView) questionContainer.findViewById(R.id.input2);
        input1_view.setText(String.valueOf(input1));
        input2_view.setText(String.valueOf(input2));

        // Add Click event and touch effect to the settings button (image)
        ImageView settings_button = (ImageView) questionContainer.findViewById(R.id.settings_button);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // it is supposed to be a settings button, but for now it will only be acted as quit button
                finish();
            }
        });
        settings_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImageView view = (ImageView) v;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        view.setImageResource(R.drawable.settings_icon_purple);
                        break;

                    case MotionEvent.ACTION_UP:
                        view.setImageResource(R.drawable.settings_icon_black);
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

        // Initialize all hint input buttons
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

        // Initialize all hint answer field
        hint_answer[0] = (TextView) hint1.findViewById(R.id.ans);
        hint_answer[1] = (TextView) hint2.findViewById(R.id.ans);
        hint_answer[2] = (TextView) hint3.findViewById(R.id.ans);
        hint_answer[3] = (TextView) hint4.findViewById(R.id.ans);
        hint_answer[4] = (TextView) hint5.findViewById(R.id.ans);
        hint_answer[5] = (TextView) hint6.findViewById(R.id.ans);

        //Initializes the class and generates the answer, can be used to compare with players' answers
        unknownFunction = new UnknownFunctionGenerator();
        final_answer = unknownFunction.getResult(input1, input2);

        // Testing
        testing();

        //Used to test the random number generator, UnknownFunctionGenerator::randomGenerator()
//        while (tempCounter > 0){
//            testing = test.randomGenerator();
//            Log.d("Number", Long.toString(testing));
//            tempCounter--;
//        }
    }

    @Override
    public void onBackPressed(){
        // Disable back button
    }

    private View.OnClickListener answer_button = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(OfflineMode.this);
            builder.setTitle("Enter your answer:");
            final EditText answer = new EditText(OfflineMode.this);
            answer.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setCancelable(false);
            builder.setView(answer);
            builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Check answer
                    int change;
                    if(answer.getText().length() > 0 && Long.valueOf(answer.getText().toString()) == final_answer){
                        change = 1;
                        Toast.makeText(OfflineMode.this, "Correct", Toast.LENGTH_SHORT).show();
                        nextRound();
                    } else {
                        change = -1;
                        Toast.makeText(OfflineMode.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        testing();
                    }

                    // Update score
                    if(v == player1_button){
                        int currentScore = Integer.valueOf(player1_score.getText().toString());
                        player1_score.setText(String.valueOf(currentScore + change));
                    } else {
                        int currentScore = Integer.valueOf(player2_score.getText().toString());
                        player2_score.setText(String.valueOf(currentScore + change));
                    }
                }
            });
            builder.create().show();
        }
    };

    private void nextRound(){
        input1 = (int)Math.round(Math.random() * 1000.0) % 1000;
        input2 = (int)Math.round(Math.random() * 1000.0) % 1000;
        input1_view.setText(String.valueOf(input1));
        input2_view.setText(String.valueOf(input2));
        unknownFunction = new UnknownFunctionGenerator();
        final_answer = unknownFunction.getResult(input1, input2);
        for(TextView view: hint_inputs){
            view.setText("");
        }
        for(TextView view: hint_answer){
            view.setText("");
        }
        pos = 0;
        testing();
    }

    // Test code
    int pos = 0;
    private void testing(){
        int random_inp1 = (int) Math.floor(Math.random() * 10000.0);
        int random_inp2 = (int) Math.floor(Math.random() * 10000.0);
        hint_inputs[pos * 2].setText(String.valueOf(random_inp1));
        hint_inputs[pos * 2 + 1].setText(String.valueOf(random_inp2));
        hint_answer[pos].setText(String.valueOf(unknownFunction.getResult(random_inp1, random_inp2)));
        pos = (pos + 1) % 6;
    }

}

package unknown.logica;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static unknown.logica.StringContainer.*;

/**
 *Created by Tim on 08/07/16.
 */
public class ModeSelection extends AppCompatActivity {

    public static final String score = "SCORE MODE";
    public static final String round = "ROUND MODE";

    private Button scoreMode;
    private Button roundMode;
    private Context context;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector);
        context = this;

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        scoreMode = (Button) findViewById(R.id.score_mode);
        roundMode = (Button) findViewById(R.id.round_mode);
        ImageButton icon = (ImageButton) findViewById(R.id.joystick);
        RelativeLayout screen = (RelativeLayout) findViewById(R.id.main_screen);

        assert scoreMode != null;
        scoreMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(score);
            }
        });
        scoreMode.setOnTouchListener(selectionEffect);

        assert roundMode != null;
        roundMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(round);
            }
        });
        roundMode.setOnTouchListener(selectionEffect);

        assert icon != null;
        icon.setOnTouchListener(swipeListener);

        assert screen != null;
        screen.setOnTouchListener(swipeListener);
    }

    private void showPicker(final String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModeSelection.this);
        final LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.popup_selector, null);
        final NumberPicker picker = (NumberPicker) view.findViewById(R.id.picker);
        String title = score_mode_string;
        if (type.equals(round)) {
            TextView message = (TextView) view.findViewById(R.id.message);
            message.setText(round_mode_description_string);
            title = round_mode_string;
        }
        picker.setMaxValue(20);
        picker.setMinValue(1);
        picker.setValue(5);
        picker.setWrapSelectorWheel(true);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton(game_start_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ModeSelection.this, Game.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Limit", picker.getValue());
                bundle.putString("Type", type);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);
            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    private View.OnTouchListener selectionEffect = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.lightYellow));
                    break;

                case MotionEvent.ACTION_UP:
                    v.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.deepPink));
                    break;
            }
            return false;
        }
    };

    private OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(context, true){
        @Override public void onSwipeTopHold() { }
        @Override public void onSwipeBottomHold() { }
        @Override public void onSwipeTopUp() { }
        @Override public void onSwipeBottomUp() { }

        @Override
        public void onSwipeRightHold() {
            super.onSwipeRightHold();
            roundMode.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.lightYellow));
            scoreMode.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.deepPink));
        }

        @Override
        public void onSwipeLeftHold() {
            super.onSwipeLeftHold();
            scoreMode.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.lightYellow));
            roundMode.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.deepPink));
        }

        @Override
        public void onSwipeRightUp() {
            showPicker(round);
            roundMode.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.deepPink));
        }

        @Override
        public void onSwipeLeftUp() {
            showPicker(score);
            scoreMode.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.deepPink));
        }
    };
}

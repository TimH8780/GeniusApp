package unknown.logica;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import static unknown.logica.Module.StringContainer.*;

/**
 *Created by Tim on 08/07/16.
 */
public class ModeSelection extends AppCompatActivity {

    public static final String SCORE = "SCORE MODE";
    public static final String ROUND = "ROUND MODE";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        Button scoreMode = (Button) findViewById(R.id.score_mode);
        Button roundMode = (Button) findViewById(R.id.round_mode);
        ImageButton icon = (ImageButton) findViewById(R.id.joystick);

        assert scoreMode != null;
        scoreMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(SCORE);
            }
        });
        scoreMode.setOnTouchListener(selectionEffect);

        assert roundMode != null;
        roundMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(ROUND);
            }
        });
        roundMode.setOnTouchListener(selectionEffect);

        assert icon != null;
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showPicker(final String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = getLayoutInflater().inflate(R.layout.popup_selector, null);
        final NumberPicker picker = (NumberPicker) view.findViewById(R.id.picker);
        String title = score_mode_string;
        if (type.equals(ROUND)) {
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
                    v.setBackgroundColor(ContextCompat.getColor(ModeSelection.this, R.color.lightPink));
                    break;
            }
            return false;
        }
    };

}

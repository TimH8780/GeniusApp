package unknown.thegeniusapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 *Created by Tim on 08/07/16.
 */
public class ModeSelection extends AppCompatActivity {

    public static final String score = "SCORE_MODE";
    public static final String round = "ROUND_MODE";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selector_new);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        Button scoreMode = (Button) findViewById(R.id.score_mode);
        Button roundMode = (Button) findViewById(R.id.round_mode);
        ImageButton joystick = (ImageButton) findViewById(R.id.joystick);

        assert scoreMode != null;
        scoreMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(score);
            }
        });

        assert roundMode != null;
        roundMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicker(round);
            }
        });

        assert joystick !=null;
        joystick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showPicker(final String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(ModeSelection.this);
        builder.setTitle("Limit Setup");
        final LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.selector_popup, null);
        final NumberPicker picker = (NumberPicker) view.findViewById(R.id.picker);
        if (type.equals(round)) {
            TextView message = (TextView) view.findViewById(R.id.message);
            message.setText("The game ends when it reaches the limited round");
        }
        picker.setMaxValue(20);
        picker.setMinValue(1);
        picker.setValue(5);
        picker.setWrapSelectorWheel(true);
        builder.setView(view);
        builder.setPositiveButton("Start Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ModeSelection.this, OfflineMode.class);
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
}

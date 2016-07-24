package unknown.thegeniusapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by Unknown on 7/22/2016.
 */
public class TestClass extends AppCompatActivity {

    CountDownTimerSeconds counter = new CountDownTimerSeconds();
    long time_seconds = 10;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdowntest);

        final Button starting = (Button) findViewById(R.id.start_button);
        final Button stoping = (Button) findViewById(R.id.stop_button);
        final Button resuming = (Button) findViewById(R.id.resume_button);

        starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.start(time_seconds);
            }
        });

        stoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.stop();
            }
        });

        resuming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.resume();
            }
        });
    }

}

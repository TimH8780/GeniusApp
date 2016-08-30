package unknown.logica;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TestClass extends AppCompatActivity {

    CountDownTimerSeconds counter;
    long time_seconds = 10;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdowntest);
        counter = new CountDownTimerSeconds(time_seconds, null, null);

        final Button starting = (Button) findViewById(R.id.start_button);
        final Button stoping = (Button) findViewById(R.id.stop_button);
        final Button resuming = (Button) findViewById(R.id.resume_button);
        final Button pausing = (Button) findViewById(R.id.pause_button);

        starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.start();
            }
        });

        pausing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.pause();
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

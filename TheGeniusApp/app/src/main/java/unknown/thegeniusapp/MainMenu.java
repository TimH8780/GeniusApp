package unknown.thegeniusapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        final Button offline_mode_button = (Button) findViewById(R.id.offline_mode_button);
        final Button settings_button = (Button) findViewById(R.id.settings_button);
        final Button tutorial_button = (Button) findViewById(R.id.tutorial_button);
        final Button quit_button = (Button) findViewById(R.id.quit_button);

        offline_mode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offline_activity = new Intent(MainMenu.this, OfflineMode.class);
                startActivity(offline_activity);
            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent settings_activity = new Intent(MainMenu.this, Settings.class);
//                startActivity(settings_activity);
            }
        });

        tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent tutorial_activity = new Intent(MainMenu.this, Tutorial.class);
//                startActivity(tutorial_activity);
            }
        });

        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

    }
}

package unknown.thegeniusapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        final Button offline_mode_button = (Button) findViewById(R.id.offline_mode_button);
        final Button settings_button = (Button) findViewById(R.id.settings_button);
        final Button tutorial_button = (Button) findViewById(R.id.tutorial_button);
        final Button quit_button = (Button) findViewById(R.id.quit_button);

        offline_mode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offline_mode_activity = new Intent(MainMenu.this, OfflineMode.class);
                offline_mode_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(offline_mode_activity);
            }
        });

        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_activity = new Intent(MainMenu.this, Settings.class);
                settings_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(settings_activity);
            }
        });

        tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorial_activity = new Intent(MainMenu.this, TestClass.class);
                tutorial_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(tutorial_activity);
            }
        });

        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    }

    @Override
    public void onBackPressed(){
        // Disable back button
    }

}

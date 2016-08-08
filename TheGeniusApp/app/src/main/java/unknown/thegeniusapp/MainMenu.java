package unknown.thegeniusapp;

import android.content.Intent;
import android.graphics.Rect;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    private Rect rect;
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
        final ImageView main_menu_buttons = (ImageView) findViewById(R.id.main_menu_buttons);

        assert offline_mode_button != null;
        offline_mode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent offline_mode_activity = new Intent(MainMenu.this, ModeSelection.class);
                offline_mode_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(offline_mode_activity);
            }
        });

        offline_mode_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });

        offline_mode_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom()); //Button bounds
                        main_menu_buttons.setImageResource(R.drawable.offline_mode_pressed);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            // User moved outside bounds
                            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                            break;
                        }

                }
                return false;
            }
        });

        assert settings_button != null;
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings_activity = new Intent(MainMenu.this, Settings.class);
                settings_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(settings_activity);
            }
        });

        settings_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom()); //Button bounds
                        main_menu_buttons.setImageResource(R.drawable.settings_pressed);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            // User moved outside bounds
                            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                            break;
                        }
                }
                return false;
            }
        });

        assert tutorial_button != null;
        tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tutorial_activity = new Intent(MainMenu.this, TestClass.class);
                tutorial_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(tutorial_activity);
            }
        });

        tutorial_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom()); //Button bounds
                        main_menu_buttons.setImageResource(R.drawable.tutorial_mode_pressed);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            // User moved outside bounds
                            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                            break;
                        }
                }
                return false;
            }
        });

        assert quit_button != null;
        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        quit_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom()); //Button bounds
                        main_menu_buttons.setImageResource(R.drawable.quit_pressed);
                        break;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                            // User moved outside bounds
                            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
                            break;
                        }
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed(){
        // Disable back button
    }

}

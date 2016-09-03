package unknown.logica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import static unknown.logica.Settings.*;

public class MainMenu extends AppCompatActivity {

    private Rect rect;
    private ImageView main_menu_buttons;
    private Context context;
    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;

        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);
        if(sharedPreferences.getBoolean(FIRST_TIMER_UESER, true)){
            // First time user
            Toast.makeText(getApplicationContext(), "First Timer User", Toast.LENGTH_LONG).show();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_TIMER_UESER, false);
        editor.apply();

        createPlayBGM();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        Button data_button = (Button) findViewById(R.id.data_button);
        Button settings_button = (Button) findViewById(R.id.settings_button);
        Button tutorial_button = (Button) findViewById(R.id.tutorial_button);
        Button quit_button = (Button) findViewById(R.id.quit_button);
        ImageView main_menu_background = (ImageView) findViewById(R.id.main_menu_background);
        ImageButton gameplay = (ImageButton) findViewById(R.id.gameplay);
        main_menu_buttons = (ImageView) findViewById(R.id.main_menu_buttons);

        //Swipe Listener, Covers only the background, not inside main_menu_button
        assert main_menu_background != null;
        main_menu_background.setOnTouchListener(swipeListener);

        //Does not make a big difference, only areas without buttons can be registered
        assert main_menu_buttons != null;
        main_menu_buttons.setOnTouchListener(swipeListener);

        assert data_button != null;
        data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Data.class);
            }
        });
        data_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.offline_mode_pressed));

        assert settings_button != null;
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Settings.class);
            }
        });
        settings_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.settings_pressed));

        assert tutorial_button != null;
        tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Tutorial.class);
            }
        });
        tutorial_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.tutorial_mode_pressed));

        assert quit_button != null;
        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitGame();
            }
        });
        quit_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.quit_pressed));

        assert gameplay != null;
        gameplay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    startActivity(ModeSelection.class);
                }
                return false;
            }
        });
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

    private void startActivity(Class<?> cls){
        Intent activity = new Intent(context, cls);
        activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(activity);
    }

    private void quitGame(){
        finishAffinity();
        System.exit(0);
    }

    private void createPlayBGM(){
        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);
        boolean music_enable = sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true);
        Log.d("Enable Music", Boolean.toString(music_enable));

        if(musicPlayer != null) try{
            musicPlayer.reset();
        } catch (IllegalStateException e){
            musicPlayer = null;
        }

        if (music_enable) {
            musicPlayer = MediaPlayer.create(MainMenu.this, R.raw.bgm_main);
            musicPlayer.start();
            musicPlayer.setLooping(true);
        }
        else musicPlayer = MediaPlayer.create(MainMenu.this, R.raw.bgm_main); //Without this the game crashes when Settings is pressed
    }

    private OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(context, false){
        @Override
        public void onSwipeTopHold() {
            super.onSwipeTopHold();
            main_menu_buttons.setImageResource(R.drawable.offline_mode_pressed);
        }

        @Override
        public void onSwipeBottomHold() {
            super.onSwipeBottomHold();
            main_menu_buttons.setImageResource(R.drawable.settings_pressed);
        }

        @Override
        public void onSwipeRightHold() {
            super.onSwipeRightHold();
            main_menu_buttons.setImageResource(R.drawable.quit_pressed);
        }

        @Override
        public void onSwipeLeftHold() {
            super.onSwipeLeftHold();
            main_menu_buttons.setImageResource(R.drawable.tutorial_mode_pressed);
        }

        @Override
        public void onSwipeRightUp() {
            quitGame();
        }

        @Override
        public void onSwipeLeftUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
            startActivity(Tutorial.class);
        }

        @Override
        public void onSwipeTopUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
            startActivity(Data.class);
        }

        @Override
        public void onSwipeBottomUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
            startActivity(Settings.class);
        }
    };

    private class CustomOnTouchListener implements View.OnTouchListener {
        private int drawablePressed;
        private CustomOnTouchListener(int drawablePressed){
            this.drawablePressed = drawablePressed;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());   // Button bounds
                    main_menu_buttons.setImageResource(drawablePressed);
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
    }

}

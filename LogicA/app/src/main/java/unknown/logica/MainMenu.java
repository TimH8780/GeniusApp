package unknown.logica;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainMenu extends AppCompatActivity {

    private Rect rect;
    private int min_distance = 100;
    private float downX, downY, upX, upY;
    private ImageView main_menu_buttons;
    private Context context;
    private MediaPlayer musicPlayer;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;
        createPlayBGM();

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        final Button offline_mode_button = (Button) findViewById(R.id.offline_mode_button);
        final Button settings_button = (Button) findViewById(R.id.settings_button);
        final Button tutorial_button = (Button) findViewById(R.id.tutorial_button);
        final Button quit_button = (Button) findViewById(R.id.quit_button);
        main_menu_buttons = (ImageView) findViewById(R.id.main_menu_buttons);
        final ImageView main_menu_background = (ImageView) findViewById(R.id.main_menu_background);
        final ImageButton joystick = (ImageButton) findViewById(R.id.joystick);

        //Swipe Listener, Covers only the background, not inside main_menu_button
        assert main_menu_background != null;
        main_menu_background.setOnTouchListener(swipeListener);

        //Does not make a big difference, only areas without buttons can be registered
        assert main_menu_buttons != null;
        main_menu_buttons.setOnTouchListener(swipeListener);

        assert offline_mode_button != null;
        offline_mode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Intent offline_mode_activity = new Intent(MainMenu.this, ModeSelection.class);
//              offline_mode_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//              startActivity(offline_mode_activity);
            }
        });
        offline_mode_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.offline_mode_pressed));

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
                startActivity(TestClass.class);
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

        // Enter game directly - Dubug
        assert joystick != null;
        joystick.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Intent intent = new Intent(MainMenu.this, OfflineMode.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("Limit", 10);
                        bundle.putString("Type", "SCORE MODE");
                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        break;
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
        Intent settings_activity = new Intent(context, cls);
        settings_activity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(settings_activity);
    }

    private void quitGame(){
        finishAffinity();
        System.exit(0);
    }

    private void createPlayBGM(){
        if(musicPlayer != null) {
            try {
                musicPlayer.reset();
            } catch(IllegalStateException e){
                musicPlayer = null;
            }
        }
        musicPlayer = MediaPlayer.create(MainMenu.this, R.raw.bgm_main);
        musicPlayer.start();
        musicPlayer.setLooping(true);
    }

    private OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(context){
        @Override
        public void onSwipeTopHold() {
            super.onSwipeTopHold();
            main_menu_buttons.setImageResource(R.drawable.offline_mode_pressed);
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
        public void onSwipeBottomHold() {
            super.onSwipeBottomHold();
            main_menu_buttons.setImageResource(R.drawable.settings_pressed);
        }

        @Override
        public void onSwipeRightUp() {
            quitGame();
        }

        @Override
        public void onSwipeLeftUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
            startActivity(TestClass.class);
        }

        @Override
        public void onSwipeTopUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons);
            startActivity(ModeSelection.class);
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
                    rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom()); //Button bounds
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

package unknown.logica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import unknown.logica.Module.BGMManager;
import unknown.logica.Module.OnSwipeTouchListener;
import unknown.logica.Module.StringContainer;

import static unknown.logica.Settings.*;

public class MainMenu extends AppCompatActivity {

    private Rect rect;
    private ImageView main_menu_buttons;
    private Context context;
    private BGMManager bgmManager;
    private int guideNum;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;

        final Button data_button = (Button) findViewById(R.id.data_button);
        final Button settings_button = (Button) findViewById(R.id.settings_button);
        final Button tutorial_button = (Button) findViewById(R.id.tutorial_button);
        final Button quit_button = (Button) findViewById(R.id.quit_button);
        final ImageView main_menu_background = (ImageView) findViewById(R.id.main_menu_background);
        final ImageView gameplay = (ImageView) findViewById(R.id.gameplay);
        main_menu_buttons = (ImageView) findViewById(R.id.main_menu_buttons);
        final ImageView guidelabel1 = (ImageView) findViewById(R.id.guidelabel1);
        final ImageView guidelabel2 = (ImageView) findViewById(R.id.guidelabel2);
        final ImageView guidelabel3 = (ImageView) findViewById(R.id.guidelabel3);
        final ImageView next_arrow = (ImageView) findViewById(R.id.next_arrow);
        final ImageView previous_arrow = (ImageView) findViewById(R.id.previous_arrow);
        final ImageView skip_arrow = (ImageView) findViewById(R.id.skip_arrow);
        final TextView cover = (TextView) findViewById(R.id.cover);
        SharedPreferences sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);

        if(sharedPreferences.getBoolean(FIRST_TIMER_USER, true)){
            // First time user
            guidelabel1.setVisibility(View.VISIBLE);
            next_arrow.setVisibility(View.VISIBLE);
            skip_arrow.setVisibility(View.VISIBLE);
            cover.setVisibility(View.VISIBLE);
            guideNum = 1;

            data_button.setEnabled(false);
            settings_button.setEnabled(false);
            tutorial_button.setEnabled(false);
            quit_button.setEnabled(false);
            gameplay.setEnabled(false);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FIRST_TIMER_USER, false);
            editor.apply();
        }
        else{
            //Swipe Listener, Covers only the background, not inside main_menu_button
            main_menu_background.setOnTouchListener(swipeListener);

            //Does not make a big difference, only areas without buttons can be registered
            main_menu_buttons.setOnTouchListener(swipeListener);
        }

        assert skip_arrow != null;
        skip_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guidelabel1.setVisibility(View.GONE);
                guidelabel2.setVisibility(View.GONE);
                guidelabel3.setVisibility(View.GONE);
                next_arrow.setVisibility(View.GONE);
                previous_arrow.setVisibility(View.GONE);
                skip_arrow.setVisibility(View.GONE);
                cover.setVisibility(View.GONE);

                data_button.setEnabled(true);
                settings_button.setEnabled(true);
                tutorial_button.setEnabled(true);
                quit_button.setEnabled(true);
                gameplay.setEnabled(true);

                main_menu_background.setOnTouchListener(swipeListener);
                main_menu_buttons.setOnTouchListener(swipeListener);
            }
        });

        assert next_arrow != null;
        next_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (guideNum){
                    case 1:
                        guidelabel1.setVisibility(View.GONE);
                        guidelabel2.setVisibility(View.VISIBLE);
                        previous_arrow.setVisibility(View.VISIBLE);
                        guideNum++;
                        break;

                    case 2:
                        guidelabel2.setVisibility(View.GONE);
                        guidelabel3.setVisibility(View.VISIBLE);
                        guideNum++;
                        break;

                    case 3:
                        guidelabel3.setVisibility(View.GONE);
                        next_arrow.setVisibility(View.GONE);
                        previous_arrow.setVisibility(View.GONE);
                        skip_arrow.setVisibility(View.GONE);
                        cover.setVisibility(View.GONE);

                        data_button.setEnabled(true);
                        settings_button.setEnabled(true);
                        tutorial_button.setEnabled(true);
                        quit_button.setEnabled(true);
                        gameplay.setEnabled(true);

                        main_menu_background.setOnTouchListener(swipeListener);
                        main_menu_buttons.setOnTouchListener(swipeListener);
                        break;

                    default:
                        throw new RuntimeException("First Time Tutorial");
                }
            }
        });

        assert previous_arrow != null;
        previous_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (guideNum){
                    case 1:
                        break;

                    case 2:
                        guidelabel1.setVisibility(View.VISIBLE);
                        guidelabel2.setVisibility(View.GONE);
                        previous_arrow.setVisibility(View.GONE);
                        guideNum--;
                        break;

                    case 3:
                        guidelabel2.setVisibility(View.VISIBLE);
                        guidelabel3.setVisibility(View.GONE);
                        guideNum--;
                        break;

                    default:
                        throw new RuntimeException("First Time Tutorial");
                }
            }
        });

        String lang = "";
        switch (sharedPreferences.getInt(LANGUAGE_VALUE, 0)){
            case 0:
                lang = "en";
                break;
            case 1:
                lang = "zh";
                break;
            case 2:
                lang = "ja";
                break;
        }
        Locale newLocale = new Locale(lang);                        // Set Selected Locale
        Locale.setDefault(newLocale);                                // Set new locale as default
        Configuration config = new Configuration();                 // Get Configuration
        config.locale = newLocale;                                  // Set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        StringContainer.initializeStrings(getResources());

        bgmManager = BGMManager.getInstance(this, R.raw.bgm_main);
        bgmManager.startMusic();

        assert data_button != null;
        data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Data.class);
            }
        });
        data_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.data_pressed_new));

        assert settings_button != null;
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, Settings.class);
                intent.putExtra("location", "Main Menu");
                startActivity(intent);
            }
        });
        settings_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.settings_pressed_new));

        assert tutorial_button != null;
        tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Tutorial.class);
            }
        });
        tutorial_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.tutorial_mode_pressed_new));

        assert quit_button != null;
        quit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitGame();
            }
        });
        quit_button.setOnTouchListener(new CustomOnTouchListener(R.drawable.quit_pressed_new));

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
        bgmManager.pauseMusic();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(!bgmManager.isSameMusic(R.raw.bgm_main)){
            bgmManager = BGMManager.getInstance(this, R.raw.bgm_main);
        }
        bgmManager.startMusic();
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

    private OnSwipeTouchListener swipeListener = new OnSwipeTouchListener(context, false){
        @Override
        public void onSwipeTopHold() {
            super.onSwipeTopHold();
            main_menu_buttons.setImageResource(R.drawable.data_pressed_new);
        }

        @Override
        public void onSwipeBottomHold() {
            super.onSwipeBottomHold();
            main_menu_buttons.setImageResource(R.drawable.settings_pressed_new);
        }

        @Override
        public void onSwipeRightHold() {
            super.onSwipeRightHold();
            main_menu_buttons.setImageResource(R.drawable.quit_pressed_new);
        }

        @Override
        public void onSwipeLeftHold() {
            super.onSwipeLeftHold();
            main_menu_buttons.setImageResource(R.drawable.tutorial_mode_pressed_new);
        }

        @Override
        public void onSwipeRightUp() {
            quitGame();
        }

        @Override
        public void onSwipeLeftUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons_new);
            startActivity(Tutorial.class);
        }

        @Override
        public void onSwipeTopUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons_new);
            startActivity(Data.class);
        }

        @Override
        public void onSwipeBottomUp() {
            main_menu_buttons.setImageResource(R.drawable.main_menu_buttons_new);
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
                    main_menu_buttons.setImageResource(R.drawable.main_menu_buttons_new);
                    break;

                case MotionEvent.ACTION_MOVE:
                    if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                        // User moved outside bounds
                        main_menu_buttons.setImageResource(R.drawable.main_menu_buttons_new);
                    }
                    break;
            }
            return false;
        }
    }

}

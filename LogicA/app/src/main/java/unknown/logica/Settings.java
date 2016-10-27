package unknown.logica;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import unknown.logica.Module.BGMManager;
import unknown.logica.Module.StringContainer;

//Reference: http://www.androhub.com/android-building-multi-language-supported-app/

public class Settings extends AppCompatActivity {

    public static final String SAVED_VALUES = "Saved Values";
    public static final String LOCALE_VALUE = "Saved Locale";
    public static final String LANGUAGE_VALUE = "Saved Language";
    public static final String MUSIC_ENABLE_VALUE = "Saved Music Enable";
    public static final String DIFFICULTY_VALUE = "Saved Difficulty";
    public static final String FIRST_TIMER_USER = "First Time";
    public static final int ENGLISH = 0;
    public static final int CHINESE = 1;
    public static final int JAPANESE = 2;
    public static final int EASY = R.id.easy;
    public static final int HARD = R.id.hard;
    public static final int MIXED = R.id.mixed;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox Music_Check_Box;
    private TextView title, language_label, music_label;
    private Button apply_button, contact_button;
    private RadioGroup radioGroup;
    private BGMManager bgmManager;

    private String lang;
    private int lang_pos;
    private boolean isLanguageChanged;
    private boolean music_enable;
    private int difficulty;

    private ImageView select_english;
    private ImageView select_chinese;
    private ImageView select_japanese;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        loadLocale();
        updateTexts();
        if(getIntent().getStringExtra("location").equals("Main Menu")) {
            bgmManager = BGMManager.getInstance(this, R.raw.bgm_main);
        } else {
            bgmManager = BGMManager.getInstance(this, R.raw.bgm_game);
        }
        bgmManager.startMusic(this);
        isLanguageChanged = false;

        Music_Check_Box.setChecked(music_enable);
        Music_Check_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                music_enable = Music_Check_Box.isChecked();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioGroup.check(checkedId);
            }
        });

        assert select_english != null;
        select_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_english.setImageResource(R.drawable.english_selected);
                select_chinese.setImageResource(R.drawable.chinese_not_selected);
                select_japanese.setImageResource(R.drawable.japanese_not_selected);
                lang_pos = ENGLISH;
                isLanguageChanged = !(lang.equals("en"));
            }
        });

        assert select_chinese != null;
        select_chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_chinese.setImageResource(R.drawable.chinese_selected);
                select_english.setImageResource(R.drawable.english_not_selected);
                select_japanese.setImageResource(R.drawable.japanese_not_selected);
                lang_pos = CHINESE;
                isLanguageChanged = !(lang.equals("zh"));
            }
        });

        assert select_japanese != null;
        select_japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_japanese.setImageResource(R.drawable.japanese_selected);
                select_english.setImageResource(R.drawable.english_not_selected);
                select_chinese.setImageResource(R.drawable.chinese_not_selected);
                lang_pos = JAPANESE;
                isLanguageChanged = !(lang.equals("ja"));
            }
        });

        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromLocation = getIntent().getStringExtra("location");
                boolean isDifficultyChanged = !(radioGroup.getCheckedRadioButtonId() == difficulty);
                difficulty = radioGroup.getCheckedRadioButtonId();

                if (fromLocation.equals("Main Menu") || (!isLanguageChanged && !isDifficultyChanged)) {
                    saveAndQuit();
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setCancelable(false);

                    View popupView = getLayoutInflater().inflate(R.layout.popup_restart, null);
                    builder.setView(popupView);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                    Button cancel = (Button) popupView.findViewById(R.id.restart_cancel);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            finish();
                        }
                    });

                    Button confirm = (Button) popupView.findViewById(R.id.restart_confirm);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            saveAndQuit();

                            Intent data = new Intent();
                            data.putExtra("Result", true);
                            setResult(Activity.RESULT_OK, data);
                            finish();
                        }
                    });
                }
            }
        });

        contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ouremail@domain.com"});
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Settings.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        bgmManager.pauseMusic();
    }

    @Override
    protected void onResume(){
        super.onResume();
        bgmManager.startMusic(this);
    }

    public void saveAndQuit(){
        switch (lang_pos){
            case ENGLISH:
                lang = "en";
                break;
            case CHINESE:
                lang = "zh";
                break;
            case JAPANESE:
                lang = "ja";
                break;
        }
        changeLocale();
    }

    private void initViews() {
        sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        select_english = (ImageView) findViewById(R.id.select_english_button);
        select_chinese = (ImageView) findViewById(R.id.select_chinese_button);
        select_japanese = (ImageView) findViewById(R.id.select_japanese_button);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        title = (TextView) findViewById(R.id.settings_title);
        language_label = (TextView) findViewById(R.id.language_label);
        music_label = (TextView) findViewById(R.id.music_label);
        apply_button = (Button) findViewById(R.id.apply_button);
        contact_button = (Button) findViewById(R.id.contact_button);
        Music_Check_Box = (CheckBox) findViewById(R.id.music_check_box);
    }

    // Get locale method in preferences
    public void loadLocale() {
        lang = sharedPreferences.getString(LOCALE_VALUE, "");
        lang_pos = sharedPreferences.getInt(LANGUAGE_VALUE, ENGLISH);
        music_enable = sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true);
        difficulty = sharedPreferences.getInt(DIFFICULTY_VALUE, MIXED);

        changeLocale();
    }

    // Change Locale
    public void changeLocale() {
        if(lang.equalsIgnoreCase("")) return;

        Locale newLocale = new Locale(lang);                        // Set Selected Locale
        saveValues();                                                // Save the selected locale
        Locale.setDefault(newLocale);                                // Set new locale as default
        Configuration config = new Configuration();                 // Get Configuration
        config.locale = newLocale;                                  // Set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());       // Update the config
        updateTexts();                                              // Update texts according to locale
        StringContainer.initializeStrings(getResources());          // Update texts in other activities
    }

    public void saveValues() {
        editor.putString(LOCALE_VALUE, lang);
        editor.putInt(LANGUAGE_VALUE, lang_pos);
        editor.putBoolean(MUSIC_ENABLE_VALUE, music_enable);
        editor.putInt(DIFFICULTY_VALUE, difficulty);
        editor.commit();
    }

    private void updateTexts() {
        title.setText(R.string.settings);
        language_label.setText(R.string.language_label);
        music_label.setText(R.string.music_label);
        apply_button.setText(R.string.apply);
        Music_Check_Box.setChecked(music_enable);

        if (lang_pos == ENGLISH) {
            select_english.setImageResource(R.drawable.english_selected);
            select_chinese.setImageResource(R.drawable.chinese_not_selected);
            select_japanese.setImageResource(R.drawable.japanese_not_selected);
        }
        else if (lang_pos == CHINESE){
            select_chinese.setImageResource(R.drawable.chinese_selected);
            select_english.setImageResource(R.drawable.english_not_selected);
            select_japanese.setImageResource(R.drawable.japanese_not_selected);
        }
        else if (lang_pos == JAPANESE){
            select_japanese.setImageResource(R.drawable.japanese_selected);
            select_english.setImageResource(R.drawable.english_not_selected);
            select_chinese.setImageResource(R.drawable.chinese_not_selected);
        }

        radioGroup.check(difficulty);
    }

}
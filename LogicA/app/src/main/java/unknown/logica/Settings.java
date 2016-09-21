package unknown.logica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

//Reference: http://www.androhub.com/android-building-multi-language-supported-app/

public class Settings extends AppCompatActivity {

    public static final String SAVED_VALUES = "Saved Values";
    public static final String LOCALE_VALUE = "Saved Locale";
    public static final String LANGUAGE_VALUE = "Saved Language";
    public static final String MUSIC_ENABLE_VALUE = "Saved Music Enable";
    public static final String FIRST_TIMER_USER = "First Time";
    public static boolean languageChanged = false;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox Music_Check_Box;
    private TextView title, language_label, music_label;
    private Button apply_button;

    private String lang;
    private int lang_pos;
    public boolean music_enable;
    public Activity context;

    private ImageView select_english;
    private ImageView select_chinese;
    private ImageView select_japanese;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        context = this;
        initViews();
        loadLocale();
        updateTexts();
        languageChanged = false;

        Music_Check_Box.setChecked(music_enable);
        Music_Check_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Music_Check_Box.isChecked()) music_enable = true;
                else music_enable = false;
            }
        });

        assert select_english != null;
        select_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_english.setImageResource(R.drawable.english_selected);
                select_chinese.setImageResource(R.drawable.chinese_not_selected);
                select_japanese.setImageResource(R.drawable.japanese_not_selected);
                lang_pos = 0;
            }
        });

        assert select_chinese != null;
        select_chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_chinese.setImageResource(R.drawable.chinese_selected);
                select_english.setImageResource(R.drawable.english_not_selected);
                select_japanese.setImageResource(R.drawable.japanese_not_selected);
                lang_pos = 1;
            }
        });

        assert select_japanese != null;
        select_japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_japanese.setImageResource(R.drawable.japanese_selected);
                select_english.setImageResource(R.drawable.english_not_selected);
                select_chinese.setImageResource(R.drawable.chinese_not_selected);
                lang_pos = 2;
            }
        });

        apply_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                String fromLocation = intent.getStringExtra("location");
                if (fromLocation.equals("Main Menu"))
                    saveAndQuit();
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setCancelable(false);
                    LayoutInflater inflater = getLayoutInflater();
                    View popupView = inflater.inflate(R.layout.popup_restart, null);
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
                            //data.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                            //data.setData(Uri.parse("true"));
                            if(getParent() == null){
                                context.setResult(Activity.RESULT_OK, data);
                            } else {
                                context.getParent().setResult(Activity.RESULT_OK, data);
                            }
                            finish();
                        }
                    });
                }
                //saveAndQuit();
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void saveAndQuit(){
        switch (lang_pos){
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
        changeLocale();
        finish();
    }

    private void initViews() {
        sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        select_english = (ImageView) findViewById(R.id.select_english_button);
        select_chinese = (ImageView) findViewById(R.id.select_chinese_button);
        select_japanese = (ImageView) findViewById(R.id.select_japanese_button);

        title = (TextView) findViewById(R.id.settings_title);
        language_label = (TextView) findViewById(R.id.language_label);
        music_label = (TextView) findViewById(R.id.music_label);
        apply_button = (Button) findViewById(R.id.apply_button);
        Music_Check_Box = (CheckBox) findViewById(R.id.music_check_box);
    }

    //Get locale method in preferences
    public void loadLocale() {
        lang = sharedPreferences.getString(LOCALE_VALUE, "");
        lang_pos = sharedPreferences.getInt(LANGUAGE_VALUE, 0);
        music_enable = sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true);
        Log.d("loadLocale: ", Boolean.toString(music_enable));
        changeLocale();
    }

    //Change Locale
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
        editor.commit();
    }

    private void updateTexts() {
        title.setText(R.string.settings);
        language_label.setText(R.string.language_label);
        music_label.setText(R.string.music_label);
        apply_button.setText(R.string.apply);
        Music_Check_Box.setChecked(music_enable);
        Log.d("updateTexts : ", Boolean.toString(music_enable));
        if (lang_pos == 0) {
            select_english.setImageResource(R.drawable.english_selected);
            select_chinese.setImageResource(R.drawable.chinese_not_selected);
            select_japanese.setImageResource(R.drawable.japanese_not_selected);
        }
        else if (lang_pos == 1){
            select_chinese.setImageResource(R.drawable.chinese_selected);
            select_english.setImageResource(R.drawable.english_not_selected);
            select_japanese.setImageResource(R.drawable.japanese_not_selected);
        }
        else if (lang_pos == 2){
            select_japanese.setImageResource(R.drawable.japanese_selected);
            select_english.setImageResource(R.drawable.english_not_selected);
            select_chinese.setImageResource(R.drawable.chinese_not_selected);
        }
    }
}
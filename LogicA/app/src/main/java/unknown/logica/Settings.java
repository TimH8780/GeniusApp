package unknown.logica;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

//Reference: http://www.androhub.com/android-building-multi-language-supported-app/

public class Settings extends AppCompatActivity {

    public static final String SAVED_VALUES = "Saved Values";
    public static final String LOCALE_VALUE = "Saved Locale";
    public static final String LANGUAGE_VALUE = "Saved Language";
    public static final String MUSIC_ENABLE_VALUE = "Saved Music Enable";
    public static final String FIRST_TIMER_UESER = "First Time";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Spinner Language_Spinner;
    private CheckBox Music_Check_Box;
    private TextView title, language_label, music_label;
    private Button apply_button;

    private String lang, selected_language;
    private int lang_pos;
    public boolean music_enable;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        initViews();
        loadLocale();
        updateTexts();

        Music_Check_Box.setChecked(music_enable);
        Music_Check_Box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Music_Check_Box.isChecked()) music_enable = true;
                else music_enable = false;
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void saveAndQuit(View view){
        selected_language = Language_Spinner.getSelectedItem().toString();
        lang_pos = Language_Spinner.getSelectedItemPosition();

        switch (selected_language){
            case "English":
                lang = "en";
                break;
            case "中文":
                lang = "zh";
                break;
            case "日本語":
                lang = "ja";
                break;
        }
        changeLocale();
        finish();
    }

    private void initViews() {
        sharedPreferences = getSharedPreferences(SAVED_VALUES, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        title = (TextView) findViewById(R.id.settings_title);
        language_label = (TextView) findViewById(R.id.language_label);
        music_label = (TextView) findViewById(R.id.music_label);
        apply_button = (Button) findViewById(R.id.apply_button);
        Language_Spinner = (Spinner) findViewById(R.id.language_spinner);
        Music_Check_Box = (CheckBox) findViewById(R.id.music_check_box);

        selected_language = Language_Spinner.getSelectedItem().toString();
    }

    //Get locale method in preferences
    public void loadLocale() {
        lang = sharedPreferences.getString(LOCALE_VALUE, "");
        lang_pos = sharedPreferences.getInt(LANGUAGE_VALUE, 0);
        music_enable = sharedPreferences.getBoolean(MUSIC_ENABLE_VALUE, true);
        Log.d("loadLocale: ", Boolean.toString(music_enable));
        Language_Spinner.setSelection(lang_pos);
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
        Log.d("Saved Language", selected_language);
    }

    private void updateTexts() {
        title.setText(R.string.settings);
        language_label.setText(R.string.language_label);
        music_label.setText(R.string.music_label);
        apply_button.setText(R.string.apply);
        Language_Spinner.setSelection(lang_pos);
        Music_Check_Box.setChecked(music_enable);
        Log.d("updateTexts : ", Boolean.toString(music_enable));
    }
}
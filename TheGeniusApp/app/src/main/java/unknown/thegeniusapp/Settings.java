package unknown.thegeniusapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

//Reference: http://www.androhub.com/android-building-multi-language-supported-app/

public class Settings extends AppCompatActivity {

    private static TextView title, language_label, music_label;
    private static Button apply_button;

    private static Locale myLocale;

    private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

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
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    public void saveAndQuit(View view){
        // TODO: Save Part
        Spinner mySpinner = (Spinner) findViewById(R.id.language_spinner);
        String selected_language = mySpinner.getSelectedItem().toString();

        String lang = "en";//Default Language
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
        changeLocale(lang);
        finish();
    }

    private void initViews() {
        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        title = (TextView) findViewById(R.id.settings_title);
        language_label = (TextView) findViewById(R.id.language_label);
        music_label = (TextView) findViewById(R.id.music_label);
        apply_button = (Button) findViewById(R.id.apply_button);
    }

    public void changeLocale(String lang) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);//Set Selected Locale
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
    }

    public void saveLocale(String lang) {
        editor.putString(Locale_KeyValue, lang);
        editor.commit();
    }

    //Get locale method in preferences
    public void loadLocale() {
        String language = sharedPreferences.getString(Locale_KeyValue, "");
        changeLocale(language);
    }

    private void updateTexts() {
        title.setText(R.string.settings);
        language_label.setText(R.string.language_label);
        music_label.setText(R.string.music_label);
        apply_button.setText(R.string.apply);
    }
}

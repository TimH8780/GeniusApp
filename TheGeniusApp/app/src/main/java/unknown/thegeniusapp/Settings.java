package unknown.thegeniusapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

//Reference: http://www.androhub.com/android-building-multi-language-supported-app/

public class Settings extends AppCompatActivity {

    private static TextView title, language_label, music_label;
    private static Button apply_button;

    //private static Locale newLocale;

    private static final String Saved_Values = "Saved Values";
    private static final String Locale_Value = "Saved Locale";
    private static final String Language_Value = "Saved Language";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Spinner Language_Spinner;
    private String lang, selected_language;
    private int lang_pos;

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

        selected_language = Language_Spinner.getSelectedItem().toString();
        lang_pos = Language_Spinner.getSelectedItemPosition();
        Log.d("saveAndQuit: ", Integer.toString(lang_pos));

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
        sharedPreferences = getSharedPreferences(Saved_Values, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        title = (TextView) findViewById(R.id.settings_title);
        language_label = (TextView) findViewById(R.id.language_label);
        music_label = (TextView) findViewById(R.id.music_label);
        apply_button = (Button) findViewById(R.id.apply_button);
        Language_Spinner = (Spinner) findViewById(R.id.language_spinner);

        selected_language = Language_Spinner.getSelectedItem().toString();

    }

    //Get locale method in preferences
    public void loadLocale() {
        lang = sharedPreferences.getString(Locale_Value, "");
        lang_pos = sharedPreferences.getInt(Language_Value, 0);
        Language_Spinner.setSelection(lang_pos);
        changeLocale();
    }

    //Change Locale
    public void changeLocale() {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale newLocale = new Locale(lang);//Set Selected Locale
        saveValues();//Save the selected locale
        Locale.setDefault(newLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = newLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
        updateTexts();//Update texts according to locale
    }

    public void saveValues() {
        editor.putString(Locale_Value, lang);
        editor.putInt(Language_Value, lang_pos);
        editor.commit();
        Log.d("Saved Language", selected_language);
    }

    private void updateTexts() {
        title.setText(R.string.settings);
        language_label.setText(R.string.language_label);
        music_label.setText(R.string.music_label);
        apply_button.setText(R.string.apply);
        Language_Spinner.setSelection(lang_pos);
    }
}

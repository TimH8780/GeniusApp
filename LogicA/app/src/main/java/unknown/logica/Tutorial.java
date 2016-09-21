package unknown.logica;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Tutorial extends AppCompatActivity {

    private Intent tutIntent;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Remove action bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        Button tut_main_menu = (Button) findViewById(R.id.tut_main_menu);
        Button tut_gameplay = (Button) findViewById(R.id.tut_gameplay);
        Button tut_functions = (Button) findViewById(R.id.tut_functions);
        Button tut_faq = (Button) findViewById(R.id.tut_faq);

        tutIntent = new Intent(Tutorial.this, TutorialWindow.class);

        assert tut_main_menu !=null;
        tut_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutIntent.putExtra("mode", "tut_main_menu");
                startActivity(tutIntent);
            }
        });

        assert tut_gameplay != null;
        tut_gameplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutIntent.putExtra("mode", "tut_gameplay");
                startActivity(tutIntent);
            }
        });

        assert tut_functions != null;
        tut_functions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutIntent.putExtra("mode", "tut_functions");
                startActivity(tutIntent);
            }
        });

        assert tut_faq != null;
        tut_faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutIntent.putExtra("mode", "tut_faq");
                startActivity(tutIntent);
            }
        });
    }

}

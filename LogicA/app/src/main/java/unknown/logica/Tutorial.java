package unknown.logica;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Tutorial extends AppCompatActivity {

    //private Intent tutIntent;
    private FragmentManager fragMgr;
    private FragmentTransaction fragTrans;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        // Remove action bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        fragMgr = getSupportFragmentManager();
        displayTutorial();
/*
        Button tut_main_menu = (Button) findViewById(R.id.tut_main_menu);
        Button tut_gameplay = (Button) findViewById(R.id.tut_gameplay);
        Button tut_functions = (Button) findViewById(R.id.tut_functions);
        Button tut_faq = (Button) findViewById(R.id.tut_faq);

        tutIntent = new Intent(Tutorial.this, TutorialWindow.class);

        assert tut_main_menu != null;
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
*/

    }

    private void displayTutorial(){
        fragTrans = fragMgr.beginTransaction();
        fragTrans.replace(R.id.fragment_container, new Fragment_Title(), "Title");
        fragTrans.commit();

    }

    public void switchMainMenu(View view){
        fragTrans = fragMgr.beginTransaction();
        Fragment fragment = fragMgr.findFragmentByTag("Main");

        if(fragment != null && fragment.isVisible()) return;

        fragTrans.replace(R.id.fragment_container, new Fragment_Main(), "Main");
        fragTrans.commit();
    }

    public void switchGameplay(View view){
        fragTrans = fragMgr.beginTransaction();
        Fragment fragment = fragMgr.findFragmentByTag("Gameplay");

        if(fragment != null && fragment.isVisible()) return;

        fragTrans.replace(R.id.fragment_container, new Fragment_Gameplay(), "Gameplay");
        fragTrans.commit();
    }

    public void switchFunction(View view){
        fragTrans = fragMgr.beginTransaction();
        Fragment fragment = fragMgr.findFragmentByTag("Function");

        if(fragment != null && fragment.isVisible()) return;

        fragTrans.replace(R.id.fragment_container, new Fragment_Function(), "Function");
        fragTrans.commit();
    }

    public void switchFAQ(View view){
        fragTrans = fragMgr.beginTransaction();
        Fragment fragment = fragMgr.findFragmentByTag("FAQ");

        if(fragment != null && fragment.isVisible()) return;

        fragTrans.replace(R.id.fragment_container, new Fragment_FAQ(), "FAQ");
        fragTrans.commit();
    }

}

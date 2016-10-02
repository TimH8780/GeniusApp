package unknown.logica;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Tutorial extends AppCompatActivity {

    private FragmentManager fragMgr;
    private FragmentTransaction fragTrans;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        // Remove action bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        fragMgr = getSupportFragmentManager();
        displayTutorial();
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

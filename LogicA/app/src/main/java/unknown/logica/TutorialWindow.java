package unknown.logica;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import static unknown.logica.StringContainer.*;
import java.util.ArrayList;

public class TutorialWindow extends Activity {

    private ArrayList<Pair<String, String>> data;
    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new ArrayList<>();
        generateListData();

        Intent test = getIntent();
        String mode = test.getStringExtra("mode");

        switch (mode){
            case "tut_main_menu":
                setContentView(R.layout.tutorial_main_menu);
                break;
            case "tut_gameplay":
                setContentView(R.layout.tutorial_gameplay);
                break;
            case "tut_functions":
                setContentView(R.layout.tutorial_functions);
                break;
            case "tut_faq":
                setContentView(R.layout.activity_data);
                listView = (ExpandableListView) findViewById(R.id.list);
                TextView text = (TextView) findViewById(R.id.list_textview);
                text.setText(faq_string);

                if(listView != null) {
                    listView.setAdapter(new Adapter());
                    listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            if(listView.isGroupExpanded(groupPosition)) listView.expandGroup(groupPosition, true);
                            else listView.collapseGroup(groupPosition);
                            return false;
                        }
                    });
                }
                break;
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.98));
    }

    private void generateListData() {
        data.add(new Pair<>(getString(R.string.tut_faq_1q), getString(R.string.tut_faq_1a)));
        data.add(new Pair<>(getString(R.string.tut_faq_2q), getString(R.string.tut_faq_2a)));
        data.add(new Pair<>(getString(R.string.tut_faq_3q), getString(R.string.tut_faq_3a)));
        data.add(new Pair<>(getString(R.string.tut_faq_4q), getString(R.string.tut_faq_4a)));
        data.add(new Pair<>(getString(R.string.tut_faq_5q), getString(R.string.tut_faq_5a)));
    }

    private class Adapter extends BaseExpandableListAdapter {
        @Override
        public int getGroupCount() {
            return data.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return data.get(groupPosition).first;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return data.get(groupPosition).second;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
            String childText = (String) getGroup(groupPosition);

            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.item_header, null);
            }

            TextView header = (TextView) convertView.findViewById(R.id.header);
            header.setText(childText);
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.item_sub1, null);
            }

            TextView child = (TextView) convertView.findViewById(R.id.sub);
            child.setText(childText);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }
}
package unknown.logica;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import unknown.logica.Module.BGMManager;
import unknown.logica.Module.FunctionList;
import unknown.logica.Module.RandomNumberGenerators;
import unknown.logica.Module.UnknownFunctionGenerator;

import static unknown.logica.Module.UnknownFunctionGenerator.*;

/**
 *Created by Tim on 09/02/16.
 */
public class Data extends AppCompatActivity {

    private ExpandableListView listView;
    private ArrayList<Pair<String, String>> data;
    private BGMManager bgmManager;
    private Resources res;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        res = getResources();
        data = FunctionList.getInstance(res).getList();
        bgmManager = BGMManager.getInstance(this, R.raw.bgm_main);
        bgmManager.startMusic(this);

        listView = (ExpandableListView) findViewById(R.id.list);
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

    private class ExampleListener implements View.OnClickListener{
        private UnknownFunctionGenerator function;
        private int position;

        public ExampleListener(int pos){
            function = new UnknownFunctionGenerator(pos);
            position = pos;
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Data.this);
            builder.setTitle(res.getString(R.string.example));
            LayoutInflater inflater = getLayoutInflater();
            final View popupView = inflater.inflate(R.layout.popup_example, null);
            final EditText in1 = (EditText) popupView.findViewById(R.id.input1);
            final EditText in2 = (EditText) popupView.findViewById(R.id.input2);
            final TextView randomView = (TextView) popupView.findViewById(R.id.random);
            final TextView resultView = (TextView) popupView.findViewById(R.id.result);

            builder.setView(popupView);
            builder.setNegativeButton(res.getString(R.string.reset_label), null);
            builder.setNeutralButton(res.getString(R.string.close_label), null);
            builder.setPositiveButton(res.getString(R.string.calculate_label), null);
            final AlertDialog alertDialog = builder.create();

            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button positive = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!in1.isEnabled() || in1.getText().toString().equals("") || in2.getText().toString().equals("")) return;

                            int input1 = Integer.valueOf(in1.getText().toString());
                            int input2 = Integer.valueOf(in2.getText().toString());
                            int randomX = RandomNumberGenerators.randomNumber(10);
                            long result = function.getResult(input1, input2, randomX);
                            if(position >= MIN_RANDOM_INDEX && position <= MAX_RANDOM_INDEX) {
                                randomView.setText(String.format(res.getString(R.string.random_text), randomX));
                            }
                            resultView.setText(String.format(res.getString(R.string.result_text), result));

                            in1.setEnabled(false);
                            in2.setEnabled(false);
                        }
                    });

                    Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            in1.setText("");
                            in2.setText("");
                            in1.setEnabled(true);
                            in2.setEnabled(true);
                            randomView.setText(getResources().getString(R.string.random_na_text));
                            resultView.setText(getResources().getString(R.string.result_na_text));
                        }
                    });
                }
            });

            alertDialog.show();
        }
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
                convertView = inflater.inflate(R.layout.item_sub, null);
            }

            TextView child = (TextView) convertView.findViewById(R.id.sub);
            child.setText(childText);

            Button example = (Button) convertView.findViewById(R.id.button);
            example.setOnClickListener(new ExampleListener(groupPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }

}


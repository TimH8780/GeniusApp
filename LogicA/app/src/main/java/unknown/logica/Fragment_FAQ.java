package unknown.logica;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import static unknown.logica.StringContainer.faq_string;

/**
 *Created by Tim on 09/22/16.
 */
public class Fragment_FAQ extends Fragment {

    private ArrayList<Pair<String, String>> data;
    private ExpandableListView listView;
    private LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View returnValue = inflater.inflate(R.layout.tutorial_faq, container, false);
        this.inflater = inflater;

        data = new ArrayList<>();
        generateListData();

        listView = (ExpandableListView) returnValue.findViewById(R.id.list);
        listView.setAdapter(new Adapter());
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(listView.isGroupExpanded(groupPosition)) listView.expandGroup(groupPosition, true);
                else listView.collapseGroup(groupPosition);
                return false;
            }
        });

        return returnValue;
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

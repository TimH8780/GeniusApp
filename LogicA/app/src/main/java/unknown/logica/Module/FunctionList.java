package unknown.logica.Module;

import android.content.res.Resources;
import android.util.Pair;

import java.util.ArrayList;

import unknown.logica.R;

/**
 *Created by Tim on 10/17/16.
 */

public class FunctionList {

    private static FunctionList instance;
    private Resources res;
    private ArrayList<Pair<String, String>> data;

    private FunctionList(Resources res){
        this.res = res;
        data = new ArrayList<>();
        generateListData();
    }

    private void generateListData(){
        data.add(new Pair<>(res.getString(R.string.title_E01), res.getString(R.string.description_E01)));
        data.add(new Pair<>(res.getString(R.string.title_E02), res.getString(R.string.description_E02)));
        data.add(new Pair<>(res.getString(R.string.title_E03), res.getString(R.string.description_E03)));
        data.add(new Pair<>(res.getString(R.string.title_E04), res.getString(R.string.description_E04)));
        data.add(new Pair<>(res.getString(R.string.title_E05), res.getString(R.string.description_E05)));
        data.add(new Pair<>(res.getString(R.string.title_E06), res.getString(R.string.description_E06)));
        data.add(new Pair<>(res.getString(R.string.title_E07), res.getString(R.string.description_E07)));
        data.add(new Pair<>(res.getString(R.string.title_E08), res.getString(R.string.description_E08)));
        data.add(new Pair<>(res.getString(R.string.title_E09), res.getString(R.string.description_E09)));
        data.add(new Pair<>(res.getString(R.string.title_E10), res.getString(R.string.description_E10)));
        data.add(new Pair<>(res.getString(R.string.title_E11), res.getString(R.string.description_E11)));
        data.add(new Pair<>(res.getString(R.string.title_E12), res.getString(R.string.description_E12)));
        data.add(new Pair<>(res.getString(R.string.title_E13), res.getString(R.string.description_E13)));
        data.add(new Pair<>(res.getString(R.string.title_E14), res.getString(R.string.description_E14)));
        data.add(new Pair<>(res.getString(R.string.title_E15), res.getString(R.string.description_E15)));
        data.add(new Pair<>(res.getString(R.string.title_E16), res.getString(R.string.description_E16)));
        data.add(new Pair<>(res.getString(R.string.title_E17), res.getString(R.string.description_E17)));
        data.add(new Pair<>(res.getString(R.string.title_E18), res.getString(R.string.description_E18)));
        data.add(new Pair<>(res.getString(R.string.title_E19), res.getString(R.string.description_E19)));
        data.add(new Pair<>(res.getString(R.string.title_E20), res.getString(R.string.description_E20)));
        data.add(new Pair<>(res.getString(R.string.title_E21), res.getString(R.string.description_E21)));
        data.add(new Pair<>(res.getString(R.string.title_E22), res.getString(R.string.description_E22)));
        data.add(new Pair<>(res.getString(R.string.title_E23), res.getString(R.string.description_E23)));
        data.add(new Pair<>(res.getString(R.string.title_E24), res.getString(R.string.description_E24)));

        data.add(new Pair<>(res.getString(R.string.title_H01), res.getString(R.string.description_H01)));
        data.add(new Pair<>(res.getString(R.string.title_H02), res.getString(R.string.description_H02)));
        data.add(new Pair<>(res.getString(R.string.title_H03), res.getString(R.string.description_H03)));
        data.add(new Pair<>(res.getString(R.string.title_H04), res.getString(R.string.description_H04)));
        data.add(new Pair<>(res.getString(R.string.title_H05), res.getString(R.string.description_H05)));
        data.add(new Pair<>(res.getString(R.string.title_H06), res.getString(R.string.description_H06)));
        data.add(new Pair<>(res.getString(R.string.title_H07), res.getString(R.string.description_H07)));
        data.add(new Pair<>(res.getString(R.string.title_H08), res.getString(R.string.description_H08)));
        data.add(new Pair<>(res.getString(R.string.title_H09), res.getString(R.string.description_H09)));
        data.add(new Pair<>(res.getString(R.string.title_H10), res.getString(R.string.description_H10)));
        data.add(new Pair<>(res.getString(R.string.title_H11), res.getString(R.string.description_H11)));
    }

    public static FunctionList getInstance(Resources res){
        if(instance == null){
            instance = new FunctionList(res);
        }
        return instance;
    }

    public ArrayList<Pair<String, String>> getList(){
        return data;
    }

    public Pair<String, String> getListItem(int index){ return data.get(index); }
}

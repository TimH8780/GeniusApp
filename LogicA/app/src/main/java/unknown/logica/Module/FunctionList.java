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
        data.add(new Pair<>(res.getString(R.string.title_01), res.getString(R.string.description_01)));
        data.add(new Pair<>(res.getString(R.string.title_02), res.getString(R.string.description_02)));
        data.add(new Pair<>(res.getString(R.string.title_03), res.getString(R.string.description_03)));
        data.add(new Pair<>(res.getString(R.string.title_04), res.getString(R.string.description_04)));
        data.add(new Pair<>(res.getString(R.string.title_05), res.getString(R.string.description_05)));
        data.add(new Pair<>(res.getString(R.string.title_06), res.getString(R.string.description_06)));
        data.add(new Pair<>(res.getString(R.string.title_07), res.getString(R.string.description_07)));
        data.add(new Pair<>(res.getString(R.string.title_08), res.getString(R.string.description_08)));
        data.add(new Pair<>(res.getString(R.string.title_09), res.getString(R.string.description_09)));
        data.add(new Pair<>(res.getString(R.string.title_10), res.getString(R.string.description_10)));
        data.add(new Pair<>(res.getString(R.string.title_11), res.getString(R.string.description_11)));
        data.add(new Pair<>(res.getString(R.string.title_12), res.getString(R.string.description_12)));
        data.add(new Pair<>(res.getString(R.string.title_13), res.getString(R.string.description_13)));
        data.add(new Pair<>(res.getString(R.string.title_14), res.getString(R.string.description_14)));
        data.add(new Pair<>(res.getString(R.string.title_15), res.getString(R.string.description_15)));
        data.add(new Pair<>(res.getString(R.string.title_16), res.getString(R.string.description_16)));
        data.add(new Pair<>(res.getString(R.string.title_17), res.getString(R.string.description_17)));
        data.add(new Pair<>(res.getString(R.string.title_18), res.getString(R.string.description_18)));
        data.add(new Pair<>(res.getString(R.string.title_19), res.getString(R.string.description_19)));
        data.add(new Pair<>(res.getString(R.string.title_20), res.getString(R.string.description_20)));
        data.add(new Pair<>(res.getString(R.string.title_21), res.getString(R.string.description_21)));
        data.add(new Pair<>(res.getString(R.string.title_22), res.getString(R.string.description_22)));
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

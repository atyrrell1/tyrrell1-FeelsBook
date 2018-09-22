package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/*This class is to be used by HistoryActivity.class to create a custom ArrayAdapter and ListView.
* The ListView will show the description of the feeling and the timestamp that is associated with that particular
* feeling. */

public class ListItemsAdapter extends ArrayAdapter<Feelings> {

    private ArrayList<Feelings> feelingslist;
    private Context feelingcontext;

    public ListItemsAdapter (@NonNull Context context, ArrayList<Feelings> list){
        super (context,0, list);
        feelingcontext = context;
        feelingslist = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;

        if (listitem == null){
            listitem = LayoutInflater.from(feelingcontext).inflate(R.layout.listfeelings,parent,false);
        }
        Feelings current_feeling = feelingslist.get(position);

        TextView emotion = (TextView) listitem.findViewById(R.id.textView_feeling);
        emotion.setText(current_feeling.getFeeling_type());

        TextView date = (TextView) listitem.findViewById(R.id.textView_date);
        date.setText(current_feeling.getDatetostring());

        return listitem;
    }
}

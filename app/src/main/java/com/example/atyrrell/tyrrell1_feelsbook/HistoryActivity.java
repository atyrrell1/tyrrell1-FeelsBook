package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;


public class HistoryActivity extends AppCompatActivity {

    private ListView allFeelings;
    private static ArrayList<Feelings> feelingshistory;
    private ListItemsAdapter adapter;
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        allFeelings = (ListView) findViewById(R.id.listView);
        feelingshistory = MainActivity.feelingslist.getFeelingslist();

        adapter = new ListItemsAdapter(this, feelingshistory);
        allFeelings.setAdapter(adapter);

        allFeelings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feelings currentfeeling = MainActivity.feelingslist.getFeeling(position);
                editAlertDialog(currentfeeling,position);
            }
        });

    }

    private void editAlertDialog(final Feelings emotion, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final TextView title = new TextView(this);
        title.setText(emotion.getFeeling_type());
        title.setTextSize(18);
        layout.addView(title);

        final TextView editdatetitle = new TextView(this);
        editdatetitle.setText("\n Date:");
        layout.addView(editdatetitle);

        final EditText editdate = new EditText(this);
        editdate.setText(emotion.getDatetostring());
        layout.addView(editdate);

        final TextView editcommenttitle = new TextView(this);
        editcommenttitle.setText(" Optional Comment (max. 100 characters):\n");
        layout.addView(editcommenttitle);

        final EditText editcomment = new EditText(this);
        editcomment.setText(emotion.getOptionalComment());
        layout.addView(editcomment);

        builder.setView(layout);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Date newdate = emotion.getStringtoDate(editdate.getText().toString());
                MainActivity.feelingslist.getFeeling(position).setTimestamp(newdate);

                if (editdate.getText().toString().equals(MainActivity.feelingslist.getFeeling(position).getDatetostring())){
                    emotion.setOptional_comment(editcomment.getText().toString());
                    MainActivity.feelingslist.getFeeling(position).setOptional_comment(editcomment.getText().toString());
                    MainActivity.feelingslist.sort();
                    saveFile();
                    adapter.notifyDataSetChanged();
                }

                else{
                    exceptionalertdialog();
                }
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.feelingslist.decrementcount(emotion);
                MainActivity.feelingslist.deleteFeeling(emotion);
                saveFile();
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void exceptionalertdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Invalid Date");

        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void saveFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            MainActivity.feelingslist.saveFeelingFile(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/*This class displays the emotions that the user has entered using a custom array adapter (located in the ListItemsAdapter class).
It also takes the MainActivity's feelingslist (type Feelings_Storage) and gets the ArrayList<Feelings> object located in
that class and stores it in feelingshistory. feelingshistory is used in the adapter to display the feelings*/

public class HistoryActivity extends AppCompatActivity {

    private ListView allFeelings;
    private static ArrayList<Feelings> feelingshistory;
    private ListItemsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //Initialize and set up the count button.
        Button Count = (Button) findViewById(R.id.Countbutton);
        Count.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         CountActivity(v);
                                     }
                                 }
        );
        //Create the List view
        allFeelings = (ListView) findViewById(R.id.listView);

        feelingshistory = RecordNewFeelingActivity.feelingslist.getFeelingslist();
        feelingshistory = sort(feelingshistory);

        //Initialize and set the custom adapter
        adapter = new ListItemsAdapter(this, feelingshistory);
        allFeelings.setAdapter(adapter);

        /*If the user selects a feeling from History, an alert dialog box will pop up, and allow
        the user to edit the selected recorded feeling*/
        allFeelings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feelings currentfeeling = RecordNewFeelingActivity.feelingslist.getFeeling(position);
                editAlertDialog(currentfeeling,position);
            }
        });

    }

    /*This Alert Dialog appears when a user clicks on the feeling that they wish to edit. They can edit the
    date or the comment. They can also delete the emotion from the History as well.
     */
    private void editAlertDialog(final Feelings emotion, final int position){

        //Initialize the AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditFeelingLayout layout = new EditFeelingLayout(emotion, this);
        layout.setLayout();
        builder.setView(layout);

        //The Save button saves the user's edits to the emotion.
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*Get the new date set by the user, and check if it's valid. Then set the new date
                for the specified emotion. Also the comment will be updated. After the updates, the feelingslist
                is sorted by most recent recorded feelings and the adapter is notified and updated. The file
                is also saved because there were changes made to the recorded emotion.
                 */
                Date newdate = emotion.getStringtoDate(layout.geteditdate().getText().toString());
                RecordNewFeelingActivity.feelingslist.getFeeling(position).setTimestamp(newdate);

                if (layout.geteditdate().getText().toString().equals(emotion.getDatetostring())){
                    emotion.setOptional_comment(layout.geteditcomment().getText().toString());
                    editfeeling(emotion,position);
                }

                else{
                    /*If the date is invalid, the exceptional alert dialog (informing the user that the date is invalid
                    will be shown.*/
                    exceptionalAlert();
                }
            }
        });

        /*The delete button allows the user to delete the emotion from history. If it's deleted, the count is also
        decremented for that emotion type. The file is then saved and the adapter is notified and updated. */
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            deletefeeling(emotion);
            }
        });

        //The cancel button allows the user to go back without editing the emotion.
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        /* Create the alertDialog from the builder and display it*/
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //editfeeling will change the feeling
    public void editfeeling(Feelings newfeeling, int position){
        RecordNewFeelingActivity.feelingslist.deleteFeeling(newfeeling);
        RecordNewFeelingActivity.feelingslist.addFeeling(newfeeling);
        sort(feelingshistory);
        RecordNewFeelingActivity.feelingslist.saveFile(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        adapter.notifyDataSetChanged();
    }

    //deletefeeling will remove the feeling from history
    public void deletefeeling(Feelings emotion){
        RecordNewFeelingActivity.feelingslist.deleteFeeling(emotion);
        RecordNewFeelingActivity.feelingslist.saveFile(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        adapter.notifyDataSetChanged();
    }

    public void exceptionalAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Set the error message
        builder.setTitle("Error");
        builder.setMessage("Invalid Date");

        //Set the Okay button which will return the user back to the History screen.
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        /* Create the alertDialog from the builder and display it*/
        AlertDialog dialog1 = builder.create();
        dialog1.show();
    }

    //Sort an ArrayList by most recent Feelings
    private ArrayList<Feelings> sort (ArrayList <Feelings> emotionlist){
        Collections.sort(emotionlist, new Comparator<Feelings>() {
            @Override
            public int compare(Feelings o1, Feelings o2) {
                return o2.getTimestamp().compareTo(o1.getTimestamp());
            }
        });
        return emotionlist;
    }

    //Change to the Count page
   private void CountActivity(View view) {
        Intent intent = new Intent(this, CountActivity.class);
        startActivity(intent);
    }
}
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

/*This class displays the emotions that the user has entered using a custom array adapter (located in the ListItemsAdapter class).
It also takes the MainActivity's feelingslist (type Feelings_Storage) and gets the ArrayList<Feelings> object located in
that class and stores it in feelingshistory. feelingshistory is used in the adapter to display the feelings*/

public class HistoryActivity extends AppCompatActivity {

    private ListView allFeelings;
    private static ArrayList<Feelings> feelingshistory;
    private ListItemsAdapter adapter;
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //Create the List view
        allFeelings = (ListView) findViewById(R.id.listView);

        feelingshistory = MainActivity.feelingslist.getFeelingslist();

        //Initialize and set the custom adapter
        adapter = new ListItemsAdapter(this, feelingshistory);
        allFeelings.setAdapter(adapter);

        /*If the user selects a feeling from History, an alert dialog box will pop up, and allow
        the user to edit the selected recorded feeling*/
        allFeelings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Feelings currentfeeling = MainActivity.feelingslist.getFeeling(position);
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

        /*I chose to do a custom layout (of type LinearLayout) for the alertdialog. It allowed me to
        show the Emotion, the date and the comment, as well as three buttons. */
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        //To display the recorded emotion type, it must be added to the custom layout.
        final TextView title = new TextView(this);
        title.setText(emotion.getFeeling_type());
        title.setTextSize(18);
        layout.addView(title);

        //To display "Date: ", it must be set and added to the custom layout.
        final TextView editdatetitle = new TextView(this);
        editdatetitle.setText("\n Date:");
        layout.addView(editdatetitle);

        //To display the recorded date, it must be added to the custom layout.
        final EditText editdate = new EditText(this);
        editdate.setText(emotion.getDatetostring());
        layout.addView(editdate);

        //To display "Optional Comment (max. 100 characters):", it must be set and added to the custom layout.
        final TextView editcommenttitle = new TextView(this);
        editcommenttitle.setText(" Optional Comment (max. 100 characters):\n");
        layout.addView(editcommenttitle);

        //To display the optional comment, it must be added to the custom layout.
        final EditText editcomment = new EditText(this);
        editcomment.setText(emotion.getOptionalComment());
        layout.addView(editcomment);

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

                if (editdate.getText().toString().equals(MainActivity.feelingslist.getFeeling(position).getDatetostring())){
                    Date newdate = emotion.getStringtoDate(editdate.getText().toString());
                    MainActivity.feelingslist.getFeeling(position).setTimestamp(newdate);

                    emotion.setOptional_comment(editcomment.getText().toString());
                    MainActivity.feelingslist.getFeeling(position).setOptional_comment(editcomment.getText().toString());

                    MainActivity.feelingslist.sort();
                    saveFile();
                    adapter.notifyDataSetChanged();
                }

                else{
                    /*If the date is invalid, the exceptional alert dialog (informing the user that the date is invalid
                    will be shown.
                     */
                    exceptionalertdialog();
                }
            }
        });

        /*The delete button allows the user to delete the emotion from history. If it's deleted, the count is also
        decremented for that emotion type. The file is then saved and the adapter is notified and updated */
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.feelingslist.decrementcount(emotion);
                MainActivity.feelingslist.deleteFeeling(emotion);
                saveFile();
                adapter.notifyDataSetChanged();
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

    /*This Alert Dialog appears when the date changed by the user is invalid. It informs the user that the date entered is
    invalid.*/
    private void exceptionalertdialog(){
        //Initialize the builder
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
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //Save the feelingsList into a file.
    public void saveFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            MainActivity.feelingslist.saveFeelingFile(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
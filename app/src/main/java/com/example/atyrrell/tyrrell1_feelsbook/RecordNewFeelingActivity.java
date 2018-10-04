package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Date;

import com.google.gson.Gson;

/*This class is to be used as the "homepage". By creating a Feelings_Storage object here, I can access it
easily from other classes (e.g HistoryActivity.java, and CountActivity.java). It made most sense to create the
object here since all three classes are constantly accessing it. Also, I can load the file into it, when the
app returns to this activity. feelingslist needed to be cleared each time before loading the file or there would
be duplicate Feelings objects displayed. I did not use the gson method of saving the file because even though it
was object oriented, it kept throwing a Runtime error. I also attempted to use to split the gson method and have most of
it in the Feeling_Storage class rather than in the GUI classes but I was unsuccessful.

This java class lets the user record a new activity. They can choose from a preset list and add
an optional comment. The optional comment can only be a maximum of 100 characters which is preset in the
activity_record_new_feeling.xml file.
 */
public class RecordNewFeelingActivity extends AppCompatActivity{


    private Button lovebutton;
    private Button joybutton;
    private Button surprisebutton;
    private Button angerbutton;
    private Button sadbutton;
    private Button fearbutton;
    private Button historybutton;
    private Button editbutton;

    public static Feelings_Storage feelingslist = new Feelings_Storage();
    public SharedPreferences sharedPref;


    /*onCreate will be called when the activity is created. It will initialize and set up the feelings buttons and
    load the previously recorded feelings into feelingslist.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_feeling);

        //Initialize data.
        feelingslist.clearFeelingsList();

        //Load the data from the file
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = this.sharedPref.getString("SavedFeelings", "");
        Feelings_Storage newFeelings = gson.fromJson(json, Feelings_Storage.class);

        // Add saved feelings to feelingslist
        if (newFeelings != null){
            for(Feelings feeling : newFeelings.getFeelingslist()){
                this.feelingslist.addFeeling(feeling);
            }
        }

        //Set the button for a user to record Love
        lovebutton = (Button) findViewById(R.id.lovebutton);
        lovebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Love");
            }
        });

        //Set the button for a user to record Joy
        joybutton = (Button) findViewById(R.id.joybutton);
        joybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Joy");
            }
        });

        //Set the button for a user to record Surprise
        surprisebutton = (Button) findViewById(R.id.surprisebutton);
        surprisebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Surprise");
            }
        });

        //Set the button for a user to record Anger
        angerbutton = (Button) findViewById(R.id.angerbutton);
        angerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Anger");
            }
        });

        //Set the button for a user to record Sadness
        sadbutton = (Button) findViewById(R.id.sadnessbutton);
        sadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Sadness");
            }
        });

        //Set the button for a user to record fear
        fearbutton = (Button) findViewById(R.id.fearbutton);
        fearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Fear");
            }
        });

        //Set the button for the user to navigate to the History Activity
        historybutton = (Button) findViewById(R.id.historybutton);
        historybutton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                historyactivity(v);
            }
        });

        //Set the button for the user to add a comment to the last recorded emotion.
        editbutton = (Button) findViewById(R.id.editlast);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editactivity(v);
            }
        });

        //Determine if the editbutton should be visible or not
        if (feelingslist.getFeelingslist().size() > 0){
            editbutton.setVisibility(View.VISIBLE);
        }

    }

    /*onResume will be called when this activity is resumed. It will check if the feelingslist has at least one
    Feelings object. If it does, the Add Comment to the Last Emotion button will be visible. If it doesn't, that button
    will be invisible.
     */
    @Override
    protected void onResume(){
        super.onResume();
        if (feelingslist.getFeelingslist().size() > 0){
            editbutton.setVisibility(View.VISIBLE);
        }
        else{
            editbutton.setVisibility(View.INVISIBLE);
        }
    }

    /*This function will be called when a user clicks a button to record a feeling. Create a Feelings object with the
    specified feeling type that the user chose from picking from the list of options. Add that Feelings object to the
    FeelingsStorage object feelingslist. Also if the feelingslist has at least one Feelings object in it, set the Add
    Comment to the Last Emotion button to visible.
     */
    private void createFeeling (String feelingdesc){
        Feelings emotion = new  Feelings (feelingdesc, new Date(), "");
        feelingslist.addFeeling(emotion);
        feelingslist.saveFile (this.sharedPref);
        if (feelingslist.getFeelingslist().size() > 0){
            editbutton.setVisibility(View.VISIBLE);
        }
    }

    //Changes the Activity to the History Activity
    private void historyactivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    //Changes the Activity to the Edit Activity
    private void editactivity(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }
}

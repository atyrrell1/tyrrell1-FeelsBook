/*
        tyrrell1-FeelsBook: Allows the user to record his/her feelings. Each feeling has a timestamp and
        the option to give a comment.

        Copyright 2018 Alexandra Tyrrell tyrrell1@ualberta.ca

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at
        http://www.apache.org/licenses/LICENSE-2.0
        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
 */
package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

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
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_feeling);

        //Initialize data.
        feelingslist.clearFeelingsList();

        //Load data from the file into feelingslist object of type Feelings_Storage.
        loadFile();

        lovebutton = (Button) findViewById(R.id.lovebutton);
        lovebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Love");
            }
        });

        joybutton = (Button) findViewById(R.id.joybutton);
        joybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Joy");
            }
        });

        surprisebutton = (Button) findViewById(R.id.surprisebutton);
        surprisebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Surprise");
            }
        });

        angerbutton = (Button) findViewById(R.id.angerbutton);
        angerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Anger");
            }
        });

        sadbutton = (Button) findViewById(R.id.sadnessbutton);
        sadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Sadness");
            }
        });

        fearbutton = (Button) findViewById(R.id.fearbutton);
        fearbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFeeling("Fear");
            }
        });

        historybutton = (Button) findViewById(R.id.historybutton);
        historybutton.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                historyactivity(v);
            }
        });

        editbutton = (Button) findViewById(R.id.editlast);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editactivity(v);
            }
        });

        if (feelingslist.getFeelingslist().size() > 0){
            editbutton.setVisibility(View.VISIBLE);
        }
    }

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

    private void createFeeling (String feelingdesc){
        Feelings emotion = new  Feelings (feelingdesc, new Date(), "");
        feelingslist.addFeeling(emotion);
        if (feelingslist.getFeelingslist().size() > 0){
            editbutton.setVisibility(View.VISIBLE);
        }
        saveFile();
    }


    //Update and save the file since another emotion was added to it.
    public void saveFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            this.feelingslist.saveFeelingFile(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Load the data into the feelingslist object of type Feelings_Storage.
    public void loadFile (){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            this.feelingslist.loadFeelingfile(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Changes the Activity to the History Activity
    public void historyactivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    //Changes the Activity to the History Activity
    public void editactivity(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

}

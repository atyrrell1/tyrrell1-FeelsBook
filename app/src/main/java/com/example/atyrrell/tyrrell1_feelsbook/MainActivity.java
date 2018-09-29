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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/* This class is to be used as the "homepage". By creating a Feelings_Storage object here, I can access it
easily from other classes (e.g RecordNewFeelingActivity.java, HistoryActivity.java, and CountActivity.java). It
made most sense to create the object here since all three classes are constantly accessing it. Also, I can load
the file into it, when the app returns to this activity. feelingslist needed to be cleared each time before loading the file
or there would be duplicate Feelings objects displayed. I did not use the gson method of saving the file because even though it
was object oriented, it kept throwing a Runtime error. I also attempted to use to split the gson method and have most of
it in the Feeling_Storage class rather than in the GUI classes but I was unsuccessful.
 */

public class MainActivity extends AppCompatActivity {

    public static Feelings_Storage feelingslist = new Feelings_Storage();
    private static final String FILENAME = "file.sav";

    private Button Record;
    private Button History;
    private Button Count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize and set up the new record button
        Button Record = (Button) findViewById(R.id.button);
        Record.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          RecordNewActivity(v);
                                      }
                                  }
        );

        //Initialize and set up the history button
        Button History = (Button) findViewById(R.id.button2);
        History.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           HistoryActivity(v);
                                       }
                                   }
        );

        //Initialize and set up the count button.
        Button Count = (Button) findViewById(R.id.button3);
        Count.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         CountActivity(v);
                                     }
                                 }
        );

        //Initialize data.
        feelingslist.clearFeelingsList();
        feelingslist.clearCountList();

        //Load data from the file into feelingslist object of type Feelings_Storage.
        loadFile();
        feelingslist.getCount();
    }

    //Change to the Record New Feeling page
    public void RecordNewActivity(View view) {
        Intent intent = new Intent(this, RecordNewFeelingActivity.class);
        startActivity(intent);
    }

    //Change to the History page
    public void HistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    //Change to the Count page
    public void CountActivity(View view) {
        Intent intent = new Intent(this, CountActivity.class);
        startActivity(intent);
    }

    //Load the data into the feelingslist object of type Feelings_Storage.
    public void loadFile (){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            feelingslist.loadFeelingfile(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
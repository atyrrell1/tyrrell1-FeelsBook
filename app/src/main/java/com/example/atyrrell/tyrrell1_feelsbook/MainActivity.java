package com.example.atyrrell.tyrrell1_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import java.io.FileInputStream;
import java.io.FileNotFoundException;



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
        Button Record = (Button) findViewById(R.id.button);
        Record.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          RecordNewActivity(v);
                                      }
                                  }
        );
        Button History = (Button) findViewById(R.id.button2);
        History.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          HistoryActivity(v);
                                      }
                                  }
        );
        Button Count = (Button) findViewById(R.id.button3);
        Count.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           CountActivity(v);
                                       }
                                   }
        );

        feelingslist.clearFeelingsList();
        loadFile();
    }


    public void RecordNewActivity(View view) {
        Intent intent = new Intent(this, RecordNewFeelingActivity.class);
        startActivity(intent);
    }

    public void HistoryActivity(View view) {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }

    public void CountActivity(View view) {
        Intent intent = new Intent(this, CountActivity.class);
        startActivity(intent);
    }

    public void loadFile (){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            feelingslist.loadFeelingfile(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
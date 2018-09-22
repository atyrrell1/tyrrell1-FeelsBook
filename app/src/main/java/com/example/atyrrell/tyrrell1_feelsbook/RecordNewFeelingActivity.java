package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class RecordNewFeelingActivity extends AppCompatActivity{

    private RadioGroup feelingsgroup;
    private RadioButton radioButton;
    private Button savebutton;
    private Integer radiobuttonid;
    private EditText comment;
    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_feeling);

        feelingsgroup = (RadioGroup) findViewById(R.id.feelingsgroup);
        comment = (EditText) findViewById(R.id.editText);

        savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Feelings emotion = new Feelings (radioButton.getText().toString(),new Date(), comment.getText().toString());
                MainActivity.feelingslist.addFeeling(emotion);
                MainActivity.feelingslist.sort();
                MainActivity.feelingslist.incrementcount(emotion);
                saveFile();
                save(v);
            }
        });
    }

    public void rbclick (View view){
        radiobuttonid = feelingsgroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radiobuttonid);
    }

    public void save(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
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

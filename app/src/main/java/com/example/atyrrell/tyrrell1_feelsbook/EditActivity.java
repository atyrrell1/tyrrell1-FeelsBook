package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class EditActivity extends AppCompatActivity {
    private Button savebutton;
    private Feelings Emotion;
    private TextView title;
    private TextView date;
    private EditText comment;

    private static final String FILENAME = "file.sav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Emotion = RecordNewFeelingActivity.feelingslist.getFeeling(RecordNewFeelingActivity.feelingslist.getFeelingslist().size() - 1);

        title = (TextView) findViewById(R.id.Emotion);
        title.setText(Emotion.getFeeling_type().toString());

        date = (TextView) findViewById(R.id.date);
        date.setText(Emotion.getDatetostring());

        comment = (EditText) findViewById(R.id.commentbox);

        savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = RecordNewFeelingActivity.feelingslist.getFeelingslist().size() - 1;
                RecordNewFeelingActivity.feelingslist.getFeeling(size).setOptional_comment(comment.getText().toString());
                saveFile();
                changeactivity(v);
            }
        });
    }

    public void changeactivity(View view) {
        Intent intent = new Intent(this, RecordNewFeelingActivity.class);
        startActivity(intent);
    }

    //Update and save the file since another emotion was added to it.
    public void saveFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            RecordNewFeelingActivity.feelingslist.saveFeelingFile(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}

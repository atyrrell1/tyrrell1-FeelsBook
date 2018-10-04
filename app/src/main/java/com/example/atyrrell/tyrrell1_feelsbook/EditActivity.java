package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    private Button savebutton;
    private Feelings Emotion;
    private TextView title;
    private TextView date;
    private EditText comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Emotion = RecordNewFeelingActivity.feelingslist.getFeeling(RecordNewFeelingActivity.feelingslist.getFeelingslist().size() - 1);

        //Display the type of emotion that is being edited
        title = (TextView) findViewById(R.id.Emotion);
        title.setText(Emotion.getFeeling_type().toString());

        //Display the date that was recorded
        date = (TextView) findViewById(R.id.date);
        date.setText(Emotion.getDatetostring());

        //Comment box so the user can add a comment
        comment = (EditText) findViewById(R.id.commentbox);

        //Save button will save the comment to the emotion, and change activity
        savebutton = (Button) findViewById(R.id.savebutton);
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = RecordNewFeelingActivity.feelingslist.getFeelingslist().size() - 1;
                RecordNewFeelingActivity.feelingslist.getFeeling(size).setOptional_comment(comment.getText().toString());
                RecordNewFeelingActivity.feelingslist.saveFile(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
                changeactivity(v);
            }
        });
    }

    //Change the activity to RecordNewFeelignActivity
    public void changeactivity(View view) {
        Intent intent = new Intent(this, RecordNewFeelingActivity.class);
        startActivity(intent);
    }
}

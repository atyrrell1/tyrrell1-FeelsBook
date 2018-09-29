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

/*This java class lets the user record a new activity. They can choose from a preset list and add
an optional comment. The optional comment can only be a maximum of 100 characters which is preset in the
activity_record_new_feeling.xml file.
 */
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

        /*Saves the emotion in the Main Activity's feelingList and sorts the list. The count arraylist is also
        incremented to include the new emotion*/
        savebutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Gets the comment
                String optionalcomment = comment.getText().toString();
                //Creates a new Feeling object with the information the user recorded
                Feelings emotion = new Feelings (radioButton.getText().toString(), new Date(), optionalcomment);

                MainActivity.feelingslist.addFeeling(emotion);
                MainActivity.feelingslist.sort();
                MainActivity.feelingslist.incrementcount(emotion);
                saveFile();
                save(v);
            }
        });
    }

    //get the ID of the emotion chosen
    public void rbclick (View view){
        radiobuttonid = feelingsgroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(radiobuttonid);
    }

    //Changes the Activity back to the Main Activity
    public void save(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Update and save the file since another emotion was added to it.
    public void saveFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            MainActivity.feelingslist.saveFeelingFile(fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

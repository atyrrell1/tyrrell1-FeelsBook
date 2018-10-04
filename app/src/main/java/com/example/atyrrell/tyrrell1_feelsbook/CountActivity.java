package com.example.atyrrell.tyrrell1_feelsbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/*This activity displays the count of emotion*/

public class CountActivity extends AppCompatActivity {

    private TextView love_count;
    private TextView joy_count;
    private TextView surprise_count;
    private TextView anger_count;
    private TextView sadness_count;
    private TextView fear_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);


        /*Display how many instances of love got recorded*/
        love_count = (TextView) findViewById(R.id.textView_lovecount);
        love_count.setText(RecordNewFeelingActivity.feelingslist.getcount("Love").toString());

        /*Display how many instances of joy got recorded*/
        joy_count = (TextView) findViewById(R.id.textView_joycount);
        joy_count.setText(RecordNewFeelingActivity.feelingslist.getcount("Joy").toString());

        /*Display how many instances of surprise got recorded*/
        surprise_count = (TextView) findViewById(R.id.textView_surprisecount);
        surprise_count.setText(RecordNewFeelingActivity.feelingslist.getcount("Surprise").toString());

        /*Display how many instances of anger got recorded*/
        anger_count = (TextView) findViewById(R.id.textView_angercount);
        anger_count.setText(RecordNewFeelingActivity.feelingslist.getcount("Anger").toString());

        /*Display how many instances of sadness got recorded*/
        sadness_count = (TextView) findViewById(R.id.textView_sadnesscount);
        sadness_count.setText(RecordNewFeelingActivity.feelingslist.getcount("Sadness").toString());

        /*Display how many instances of fear got recorded*/
        fear_count = (TextView) findViewById(R.id.textView_fearcount);
        fear_count.setText(RecordNewFeelingActivity.feelingslist.getcount("Fear").toString());

    }
}
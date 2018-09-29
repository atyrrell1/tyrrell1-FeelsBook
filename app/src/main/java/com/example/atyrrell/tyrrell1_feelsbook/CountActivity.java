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

    private ArrayList<Integer> emotioncount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        emotioncount = RecordNewFeelingActivity.feelingslist.getCountlist();

        /*Display how many instances of love got recorded*/
        love_count = (TextView) findViewById(R.id.textView_lovecount);
        love_count.setText(emotioncount.get(0).toString());

        /*Display how many instances of joy got recorded*/
        joy_count = (TextView) findViewById(R.id.textView_joycount);
        joy_count.setText(emotioncount.get(1).toString());

        /*Display how many instances of surprise got recorded*/
        surprise_count = (TextView) findViewById(R.id.textView_surprisecount);
        surprise_count.setText(emotioncount.get(2).toString());

        /*Display how many instances of anger got recorded*/
        anger_count = (TextView) findViewById(R.id.textView_angercount);
        anger_count.setText(emotioncount.get(3).toString());

        /*Display how many instances of sadness got recorded*/
        sadness_count = (TextView) findViewById(R.id.textView_sadnesscount);
        sadness_count.setText(emotioncount.get(4).toString());

        /*Display how many instances of fear got recorded*/
        fear_count = (TextView) findViewById(R.id.textView_fearcount);
        fear_count.setText(emotioncount.get(5).toString());

    }
}
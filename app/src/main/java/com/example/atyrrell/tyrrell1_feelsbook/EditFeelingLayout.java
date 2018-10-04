package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditFeelingLayout extends LinearLayout {

    private Feelings editEmotion;
    private Context layoutcontext;
    private EditText editdate;
    private EditText editcomment;

    /*Constructor for the EditFeelingLayout class*/
    public EditFeelingLayout(Feelings emotion, Context context){
        super(context);
        editEmotion = emotion;
        layoutcontext = context;
        editdate = new EditText(layoutcontext);
        editcomment = new EditText(layoutcontext);
    }

    //Return the EditText box for the date
    public EditText geteditdate() {
        return editdate;
    }

    //Return the EditText box for the comment
    public EditText geteditcomment(){
        return editcomment;
    }

    public void setLayout(){
        /*I chose to do a custom layout (of type LinearLayout) for the alertdialog. It allowed me to
        show the Emotion, the date and the comment, as well as three buttons. */
        this.setOrientation(LinearLayout.VERTICAL);

        //To display the recorded emotion type, it must be added to the custom layout.
        final TextView title = new TextView(layoutcontext);
        title.setText(editEmotion.getFeeling_type());
        title.setTextSize(18);
        this.addView(title);

        //To display "Date: ", it must be set and added to the custom layout.
        final TextView editdatetitle = new TextView(layoutcontext);
        editdatetitle.setText("\n Date:");
        this.addView(editdatetitle);

        //To display the recorded date, it must be added to the custom layout.
        editdate.setText(editEmotion.getDatetostring());
        this.addView(editdate);

        //To display "Optional Comment (max. 100 characters):", it must be set and added to the custom layout.
        final TextView editcommenttitle = new TextView(layoutcontext);
        editcommenttitle.setText(" Optional Comment (max. 100 characters):\n");
        this.addView(editcommenttitle);

        //To display the optional comment, it must be added to the custom layout.
        editcomment.setText(editEmotion.getOptionalComment());
        this.addView(editcomment);
    }
}

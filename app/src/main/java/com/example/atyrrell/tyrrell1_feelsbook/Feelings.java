package com.example.atyrrell.tyrrell1_feelsbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Feelings {

    private String feeling_type;
    private Date timestamp;
    private String optional_comment;


    Feelings(String feeling, Date time, String comment){
        this.feeling_type = feeling;
        this.timestamp = time;
        this.optional_comment = comment;
    }

    public String getFeeling_type(){
        return this.feeling_type;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getOptionalComment(){
        return this.optional_comment;
    }

    public String getDatetostring(){
        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss"), Locale.CANADA);
        return(sdf.format(this.getTimestamp().getTime()));
    }

    public Date getStringtoDate(String date) {
        try{
            return new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss"), Locale.CANADA).parse(date);
        }
        catch(ParseException e){
            return new Date();
        }
    }

    public void setTimestamp (Date newtime){
        this.timestamp = newtime;
    }

    public void setOptional_comment (String newcomment){
        this.optional_comment = newcomment;
    }

    public String feelingstoString (){
        return this.feeling_type + "|" + this.getDatetostring() + "|" + this.optional_comment + "\n";
    }


}

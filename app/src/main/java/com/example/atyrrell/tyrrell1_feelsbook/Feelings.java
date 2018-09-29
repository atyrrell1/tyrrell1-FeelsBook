package com.example.atyrrell.tyrrell1_feelsbook;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Feelings class consists of getters, setters, and operations that turn a Date object of a Feeling object into a String object.
public class Feelings {

    private String feeling_type; //can be Love, Joy, Surprise, Anger, Sadness, or Fear
    private Date timestamp;
    private String optional_comment;

    //Constructor for the Feelings class
    Feelings(String feeling, Date time, String comment){
        this.feeling_type = feeling;
        this.timestamp = time;
        this.optional_comment = comment;
    }

    // getters and setters for the Feelings class

    public String getFeeling_type(){
        return this.feeling_type;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public String getOptionalComment(){
        return this.optional_comment;
    }

    public void setTimestamp (Date newtime){
        this.timestamp = newtime;
    }

    public void setOptional_comment (String newcomment){
        this.optional_comment = newcomment;
    }

    //convert an object of type Date into a String Object
    public String getDatetostring(){
        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss"), Locale.CANADA);
        return(sdf.format(this.getTimestamp().getTime()));
    }

    //convert a String object into a Date object
    public Date getStringtoDate(String date) {
        try{
            return new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss"), Locale.CANADA).parse(date);
        }
        catch(ParseException e){
            return new Date();
        }
    }

    //convert an object of type Feelings into a String object
    public String feelingstoString (){
        return this.feeling_type + "|" + this.getDatetostring() + "|" + this.optional_comment + "\n";
    }

}

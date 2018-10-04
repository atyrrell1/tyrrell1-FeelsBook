package com.example.atyrrell.tyrrell1_feelsbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/* The feelings that the user records are stored in ArrayList. I chose an ArrayList because I am most familiar with
them than other Collections. I knew I could also specify it to be of type Feelings.
 */

public class Feelings_Storage{

    private ArrayList<Feelings> feelingsList;

    //Constructor for the Feelings_Storage Class
    public Feelings_Storage(){
        this.feelingsList = new ArrayList<Feelings>();
    }

    //getters and setters for Feeling_Storage
    public ArrayList<Feelings> getFeelingslist(){
        return this.feelingsList;
    }

    public Feelings getFeeling(Integer index){
        return this.feelingsList.get(index);
    }

    public void addFeeling(Feelings emotion){
        this.feelingsList.add(emotion);
    }

    public void deleteFeeling (Feelings emotion){
        this.feelingsList.remove(emotion);
    }

    //Clear all the feelings from Feelings_Storage
    public void clearFeelingsList (){
        this.feelingsList.clear();
    }

    //Sort the feelingsList array by most recent Feelings
    public void sort (){
        Collections.sort(this.feelingsList, new Comparator<Feelings>() {
            @Override
            public int compare(Feelings o1, Feelings o2) {
                return o2.getTimestamp().compareTo(o1.getTimestamp());
            }
        });
    }

    public Integer getcount (String feeling_descr){
        Integer count = 0;

        for (Feelings emotion : this.feelingsList){
            if (emotion.getFeeling_type().equals(feeling_descr)){
                count++;
            }
        }
        return count;
    }

    //Save the items of feelingsList to a file
    public void saveFeelingFile(FileOutputStream fos){
        try{
            for (Feelings emotion : this.feelingsList){
                fos.write(emotion.feelingstoString().getBytes());
            }
            fos.close();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Load the file. Take the lines of the file (which are String Objects) and turn them into Feelings. Fill the ArrayList
    public void loadFeelingfile (FileInputStream fis){
        Feelings feelingfromfile;
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null){
                feelingfromfile = StringtoFeelings(line);
                this.addFeeling(feelingfromfile);
                line = in.readLine();
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //convert a String object into a Feelings Object
    private Feelings StringtoFeelings (String stringfromFile){
        Feelings emotion;
        String feelingcomment;

        String [] newstring = stringfromFile.split("\\|");
        String feelingdesc = newstring[0];
        String feelingdatestring = newstring[1];
        if (newstring.length == (2)){
            feelingcomment = "";
        }
        else{
            feelingcomment = newstring[2];
        }
        Date feelingdate = new Date();
        try {
            feelingdate = new SimpleDateFormat(("yyyy-MM-dd'T'HH:mm:ss"), Locale.CANADA).parse(feelingdatestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        emotion = new Feelings (feelingdesc,feelingdate,feelingcomment);
        return emotion;
    }
}

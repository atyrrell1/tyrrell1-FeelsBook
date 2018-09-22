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

public class Feelings_Storage{

    private ArrayList<Feelings> feelingsList;
    private ArrayList<Integer> count;

    public Feelings_Storage(){
        this.feelingsList = new ArrayList<Feelings>();
        this.count = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0));

    }

    public ArrayList<Feelings> getFeelingslist(){
        return this.feelingsList;
    }

    public ArrayList<Integer> getCountlist(){
        return this.count;
    }

    public Feelings getFeeling(Integer index){
        return feelingsList.get(index);
    }

    public void addFeeling(Feelings emotion){
       feelingsList.add(emotion);
    }

    public void deleteFeeling (Feelings emotion){
        this.feelingsList.remove(emotion);
    }

    public void sort (){
        Collections.sort(this.feelingsList, new Comparator<Feelings>() {
            @Override
            public int compare(Feelings o1, Feelings o2) {
                return o2.getTimestamp().compareTo(o1.getTimestamp());
            }
        });
    }

    public void clearFeelingsList (){
        this.feelingsList.clear();
    }

    public void incrementcount(Feelings emotion){
        if (emotion.getFeeling_type().equals("Love")){
            this.count.set(0,this.count.get(0)+1);
        }
        else if (emotion.getFeeling_type().equals("Joy")){
            this.count.set(1,this.count.get(1)+1);
        }
        else if (emotion.getFeeling_type().equals("Surprise")){
            this.count.set(2,this.count.get(2)+1);
        }
        else if (emotion.getFeeling_type().equals("Anger")){
            this.count.set(3, this.count.get(3)+1);
        }
        else if (emotion.getFeeling_type().equals("Sadness")){
            this.count.set(4,this.count.get(4)+1);
        }
        else{
            this.count.set(5, this.count.get(5)+1);
        }
    }

    public void decrementcount(Feelings emotion){
        if (emotion.getFeeling_type().equals("Love")){
            this.count.set(0,this.count.get(0)-1);
        }
        else if (emotion.getFeeling_type().equals("Joy")){
            this.count.set(1,this.count.get(1)-1);
        }
        else if (emotion.getFeeling_type().equals("Surprise")){
            this.count.set(2,this.count.get(2)-1);
        }
        else if (emotion.getFeeling_type().equals("Anger")){
            this.count.set(3, this.count.get(3)-1);
        }
        else if (emotion.getFeeling_type().equals("Sadness")){
            this.count.set(4,this.count.get(4)-1);
        }
        else{
            this.count.set(5, this.count.get(5)-1);
        }
    }

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

    private Feelings StringtoFeelings (String stringfromFile){
        Feelings emotion;
        String [] newstring = stringfromFile.split("\\|+");
        String feelingdesc = newstring[0];
        String feelingdatestring = newstring[1];
        String feelingcomment = newstring[2];
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

package com.example.atyrrell.tyrrell1_feelsbook;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

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

    //Count how many emotions are the "feeling_descr"
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
    public void saveFile(SharedPreferences sharedPref){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(this);
        editor.putString("SavedFeelings", json);
        editor.commit();
    }

}

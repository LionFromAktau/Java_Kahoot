package com.example.alish;

import java.util.ArrayList;

public class Test extends Question {
    private String[] options = new String[4];
    private int numOfOptions = 4;
    private ArrayList<Character> labels = new ArrayList<>();

    public void setLabels(ArrayList<Character> labels){
        for(int j=0 ; j<numOfOptions; j++){
            labels.add((char)('A'+j));
        }
    }

    public Test(){
        options = shuffle(options);
    }
    public void setOptions(String[] options){
        for(int i=0;i<numOfOptions;i++){
            this.options[i]= options[i];
        }
    }
    public String getOptions(int id){
        return options[id];
    }

    public String toString(){
        options = shuffle(options);
        String res = getDescription()+"\n";
        setLabels(labels);
        for(int i=0 ; i < numOfOptions; i++){
            res += labels.get(i)+ ") "+getOptions(i) + "\n";
        }
        return res;
    }

    public static String[] shuffle(String[] mass){
        String temp="";
        for(int i=0;i<mass.length;i++){
            int s = (int)(Math.random()*mass.length);
            temp=mass[i];
            mass[i]=mass[s];
            mass[s]=temp;
        }
        return mass;
    }
}


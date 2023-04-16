package com.example.alish;

public class FillIn extends Question {
    public FillIn(){
    }
    public String toString(){
        return  getDescription().replace("{blank}","______");
    }

    @Override
    public String getOptions(int id) {
        return null;
    }

    @Override
    public void setOptions(String[] options) {

    }
}

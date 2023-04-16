package com.example.alish;

public abstract class Question {
        private String description;
        private String answer;

        public void setDescription(String description) {
            this.description=description;
        }
        public void setAnswer(String answer){
            this.answer = answer;
        }
        public String getDescription(){
            return description;
        }
        public String getAnswer(){
            return  answer;
        }
        public abstract String getOptions(int id);
        public abstract void setOptions(String[] options);
    }


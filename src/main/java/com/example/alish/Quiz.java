package com.example.alish;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Quiz  {
    private String name;
    private static ArrayList<Question> questions = new ArrayList<>();

    public ArrayList<Question> zzz(){
        return questions;
    }

    public Quiz() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addQuestion(Question s) {
        questions.add(s);
    }

    public static ArrayList<Question> loadFromFile(String nameFile) throws FileNotFoundException {
        Quiz quiz = new Quiz();
        File file = new File(nameFile);

        Scanner input = new Scanner(file);
        while(input.hasNext()) {
            String description = input.nextLine();
            if (description.contains("{blank}")) {
                FillIn fillIn = new FillIn();
                fillIn.setDescription(description);
                fillIn.setAnswer(input.nextLine());
                quiz.addQuestion(fillIn);
            }else if(description.contains(".")){
                TrueFalse trueFalse = new TrueFalse();
                trueFalse.setDescription(description);
                trueFalse.setAnswer(input.nextLine());
                quiz.addQuestion(trueFalse);
            } else {
                Test test = new Test();
                test.setDescription(description);
                String[] mass = new String[4];
                for (int i = 0; i < 4; i++) {
                    mass[i] = input.nextLine();
                }
                test.setAnswer(mass[0]);
                test.setOptions(mass);


                quiz.addQuestion(test);
            }
        }
        return questions;
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
    public String toString() {
        return "";
    }

    public void start() {
        int correct = 0;
        int vopros = 0;
        Scanner scan = new Scanner(System.in);
        Collections.shuffle(questions);
        System.out.println("==================================================================\n" +
                "\n" +
                "WELCOME TO \"JavaQuiz\" QUIZ!\n" +
                "__________________________________________________________________\n");
        for (int i = 0; i < questions.size(); i++) {
            vopros++;
            System.out.println(vopros +". "+ questions.get(i));
            System.out.println("------------------------------------------------------------------");
            String usersAns1 = questions.get(i).getAnswer();//durysi

                while (true) {
                    System.out.print("Enter your answer:");
                    String anw = scan.nextLine();
                    if (questions.get(i).getDescription().contains("{blank}")) {
                        if (anw.equals(usersAns1)) {
                            System.out.println("Correct!");
                            correct++;
                            break;
                        } else {
                            System.out.println("Incorrect!");
                            break;
                        }
                    } else {
                    if ((anw.charAt(0) == 'A' || anw.charAt(0) == 'B' || anw.charAt(0) == 'D' || anw.charAt(0) == 'C') && anw.length()==1) {
                        int ind = (anw.charAt(0) - 'A');
                        if (questions.get(i).getOptions(ind).equals(usersAns1)) {
                            System.out.println("Correct!");
                            correct++;
                            break;
                        } else {
                            System.out.println("Incorrect!");
                            break;
                        }
                    } else {
                        System.out.print("Invalid choice! Try again (Ex: A, B, ...): ");
                    }
                }
            }
            System.out.println("__________________________________________________________________");
            System.out.println();
        }
                double procent = (double) correct / (double) vopros;
                System.out.println("Correct Answers: " + correct + "/" + vopros + " (" + procent * 100 + "%)");

            }
        }
package com.example.taskappv3;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.Random;

/**Util is a final class providing functionality shared between classes*/
public final class Util {
    private Util(){}; //Private constructor to prevent instantiation of instances

    /**Generate and returns random number from 0 to 100000000, used for both group and task IDs*/
    static int generateID(){
        Random rand = new Random();
        return rand.nextInt(100000000); //Returns random number between 0 and one hundred million
    }
}
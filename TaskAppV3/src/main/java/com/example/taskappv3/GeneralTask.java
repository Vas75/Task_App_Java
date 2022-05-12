package com.example.taskappv3;

/**Class creates general non-specialized task objects, inherits from Task class, parent class of SchoolTask class*/
public class GeneralTask extends Task {
    public static final String TASK_COLOR = "blue"; //Class constant variable that may be used in styling elements, maybe this should go in class for views

    //Constructors
    public GeneralTask(){}

    public GeneralTask(String taskName, String taskBody, String dueDate){
        super(taskName, taskBody, dueDate);
    }
}
package com.example.taskappv3;

import java.io.Serializable;

/**Abstract class intended to be inherited from by the GeneralTask class*/
public abstract class Task implements Serializable {
    //Instance variables used in subclasses
    private int taskID;
    private String taskName;
    private String dueDate;
    private String taskBody;

    public Task(){ //no constructor will call other constructor with int value for the present date
        this("undefined", "undefined", "undefined");
    }

    public Task(String taskName, String taskBody, String dueDate) {
        taskID = Util.generateID();
        this.dueDate = dueDate;
        this.taskName = taskName;
        this.taskBody = taskBody;
    }

    public int getTaskID(){
        return taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTaskBody() {
        return taskBody;
    }

    //Override of toString of object class
    @Override
    public String toString() {
        return "task name: " +
                getTaskName() +
                "\ntask body: " +
                getTaskBody() +
                "\ndue date: " +
                getDueDate();
    }
}

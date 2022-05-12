package com.example.taskappv3;

/**Creates specialized school related tasks, inherits from the GeneralTask class*/
public class SchoolTask extends GeneralTask {
    private String className;
    private String instructorName;
    private String instructorTel;


    //Constructor methods
    public SchoolTask(){
        this.className = "undefined";
        this.instructorName = "undefined";
        this.instructorTel = "undefined";
    }

    public SchoolTask(String taskName, String taskBody, String dueDate, String className, String instructorName, String instructorTel){
        super(taskName, taskBody, dueDate);
        this.className = className;
        this.instructorName = instructorName;
        this.instructorTel = instructorTel;
    }

    //Getter for instructor name, class name, instructor telephone
    public String getInstructorName() {
        return instructorName;
    }

    public String getClassName() {
        return className;
    }

    public String getInstructorTel() {
        return instructorTel;
    }


    //Override of the toString method from the Task class
    @Override
    public String toString() {
        return "task name: " +
                getTaskName() +
                "\ntask body: " +
                getTaskBody() +
                "\ndue date: " +
                getDueDate() +
                "\ninstructor: " +
                getInstructorName() +
                "\nclass name: " +
                getClassName() +
                "\ninstructor telephone: " +
                getInstructorTel();
    }
}

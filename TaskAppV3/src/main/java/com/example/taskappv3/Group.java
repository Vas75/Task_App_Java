package com.example.taskappv3;

import java.util.ArrayList;


/**Group class creates objects representing groups of tasks, each instance has own ArrayList groupName and groupId fields*/
public class Group extends ArrayList<Task> {
    //Instance variables
    private String groupName;
    private int groupID;

    //Constructors
    public Group(){this("unnamed group");}
    public Group(String groupName){
        groupID = Util.generateID();
        this.groupName = groupName;
    }

    //Getter methods for group name and ID
    public String getGroupName(){
        return groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    /**Find task by id(int) and return the task object*/
    public Task getTaskByID(int targetID) {
        int foundIdx = -1; //Forced to initialize with out-of-bounds index to deal with error

        for (int idx = 0; idx < this.size(); idx++) {
            if (this.get(idx).getTaskID() == targetID) {
                foundIdx = idx;
            }
        }
        return this.get(foundIdx);  //use index of found task to get and return it
    }


    /**Find task by id/int and delete task, return boolean true if deleted, else false*/
    public boolean deleteTaskByID(int targetID) {
        for(int idx = 0; idx < this.size(); idx++) {
            //loop through, find task and delete it
            if (this.get(idx).getTaskID() == targetID) {
                this.remove(idx); //Find task by id on list, delete it, return true
                return true;
            }
        }
        return false; //If task not found by id, return false
    }
}

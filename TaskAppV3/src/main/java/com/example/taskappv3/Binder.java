package com.example.taskappv3;

import java.util.ArrayList;

/**Class will create single binder/ArrayList to hold all groups, includes single method findGroupByIdAndDelete.*/
public class Binder extends ArrayList<Group> {
    public Binder(){};

    /**Required to locate groups by id and delete*/
    public boolean deleteGroupByID(int groupID) {
        for (int i = 0; i < this.size(); i++) {
            if(this.get(i).getGroupID() == groupID){
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    /**Method searches Binder arraylist for group by id, and returns reference to the group*/
    public Group getGroupByID(int groupID) {
        int foundIdx = -1;
        for (int i = 0; i < this.size(); i++) {
            if(this.get(i).getGroupID() == groupID) {
                foundIdx = i;
            }
        }
        return this.get(foundIdx);
    }
}

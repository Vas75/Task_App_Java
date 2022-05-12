package com.example.taskappv3;

import java.io.*;

/**Class will be responsible for saving Groups/Tasks to file, and retrieving from the file on program start*/
public final class Save {
    /**Private constructor to prevent instantiation of save objects*/
    private Save(){}

    /**Get Binder from fileName (SaveFile.dat), return Binder ArrayList*/
    static Binder getBinder(String fileName) {
        try {
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))){
                //Get Binder arraylist and return it
                return (Binder) input.readObject();
            }

            //DataOutputStream output = new DataOutputStream(new FileOutputStream("out.data"));
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**Save Binder arraylist to fileName param (SaveFile.dat)*/
    static void saveBinder(String fileName, Binder listOfGroups) {
        try {
            try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))){
                //Write Binder arraylist to save file
                output.writeObject(listOfGroups);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

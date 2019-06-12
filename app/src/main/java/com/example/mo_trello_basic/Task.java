package com.example.mo_trello_basic;

public class Task {

    int id;
    String name;
    public static String actualTaskList = null;

    public Task(String name) {
        this.name = name;
    }

    Task(String nameFromTaskList, int idFromTaskList){
        id=idFromTaskList;
        name=nameFromTaskList;
    }

}

package com.example.mo_trello_basic;

import java.util.List;

public class TaskList  {
    int id;
    String name;
    List<Task> taskList;
    public static String actualTaskTable = null;

    public TaskList() {}

    public TaskList(String name) {
        this.name = name;
    }

    public TaskList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    void addTask(Task task){

    }

    void removeTask(Task task){

    }
}

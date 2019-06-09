package com.example.mo_trello_basic;

import java.util.List;

public class TaskList {
    int id;
    String name;
    List<Task> taskList;
    public static TaskTable actualTable;

    public TaskList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    void addTask(Task task){

    }

    void removeTask(Task task){

    }
}

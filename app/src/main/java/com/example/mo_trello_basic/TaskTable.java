package com.example.mo_trello_basic;

import java.util.ArrayList;
import java.util.List;


public class TaskTable {
    int id;
    private int lastIdTaskList=-1;
    String name;
    List<TaskList> taskLists;

    TaskTable(){
        id=0;
        lastIdTaskList=-1;
        name="";
    }

    TaskTable(String nameFromUser, int idFromUser){
        id=idFromUser;
        name=nameFromUser;
        taskLists=new ArrayList<TaskList>();
    }

    public TaskTable(String name) {
        this.name = name;
    }

    public int getLastIdTaskList() {
        return lastIdTaskList;
    }

    void addTaskList(TaskList taskList){
        lastIdTaskList++;
        taskLists.add(taskList);
    }

    void removeTaskList(TaskList taskList){

    }
}

package com.example.mo_trello_basic;

import java.util.ArrayList;
import java.util.List;

public class TaskTable {
    String name;
    List<TaskList> taskLists;

    TaskTable(String nameFromUser){
        name=nameFromUser;
        taskLists=new ArrayList<TaskList>();
    }
    void addTaskList(TaskList taskList){
        /*Task newTask=new Task(name,id);
        tables.add(newTaskTable);
*/
    }

    void removeTaskList(TaskList taskList){

    }
}

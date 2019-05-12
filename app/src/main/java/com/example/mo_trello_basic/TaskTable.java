package com.example.mo_trello_basic;

import java.util.ArrayList;
import java.util.List;

public class TaskTable {
    int id=0;
    String name;
    List<TaskList> taskLists;

    TaskTable(String nameFromUser,int idFromUser){
        id=idFromUser;
        name=nameFromUser;
        taskLists=new ArrayList<TaskList>();
    }
    void addTaskList(TaskList taskList){
        /*int id=taskLists.size();
        Task newTask=new Task(name,id);
        tables.add(newTaskTable);
*/
    }

    void removeTaskList(TaskList taskList){

    }
}

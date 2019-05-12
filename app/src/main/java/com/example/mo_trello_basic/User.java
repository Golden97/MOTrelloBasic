package com.example.mo_trello_basic;

import java.util.ArrayList;
import java.util.List;

public class User {
    List<TaskTable> tables;

    User(){
        tables=new ArrayList<TaskTable>();
    }

    void addTable(String name){
        int id=tables.size();
        TaskTable tmp=new TaskTable(name,id);
    }
    void removeTable(){

    }
}

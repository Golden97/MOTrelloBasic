package com.example.mo_trello_basic;

import java.util.ArrayList;
import java.util.List;

public class User {
    List<TaskTable> tables;

    User(){
        tables=new ArrayList<TaskTable>();
    }

    public TaskTable get(int index){
        return tables.get(index);
    }

    void addTable(String name){
        int id=tables.size();
        TaskTable newTaskTable=new TaskTable(name,id);
        tables.add(newTaskTable);
    }
    public int findTaskTableByName(String nameA){
        for(int i=0;i<tables.size();i++){
            if(tables.get(i).name.equals(nameA))
                return i;
        }
        return -1;
    }
    void removeTable(String name){//modify also in main activity
        int index=findTaskTableByName(name);
        if(index!=-1) {
            tables.remove(index);
        }
    }
}

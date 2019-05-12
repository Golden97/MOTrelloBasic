package com.example.mo_trello_basic;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int lastId=0;
    List<TaskTable> tables;

    public boolean remove(TaskTable e) {
            boolean allOk=true;
            String name=e.name;
            for(int i=0;i<tables.size();i++){
                if(name.equals(tables.get(i).name)) {
                    tables.remove(i);
                    break;
                }
            }
            return allOk;
        }


    User(){
        tables=new ArrayList<TaskTable>();
    }

    public int getLastId(){
        return lastId;
    }

    public TaskTable get(int index){
        return tables.get(index);
    }

    void addTable(TaskTable table){
        tables.add(table);
        lastId++;
    }
    public int findTaskTableByName(String nameA){
        for(int i=0;i<tables.size();i++){
            if(tables.get(i).name.equals(nameA))
                return i;
        }
        return -1;
    }
    void removeTable(TaskTable table){//modify also in main activity
            remove(table);
    }
}

package com.example.mo_trello_basic;

import java.util.ArrayList;
import java.util.List;

public class User {
    List<TaskTable> tables=new ArrayList<TaskTable>(){
        @Override
        public boolean add(TaskTable e) {
            boolean allOk = super.add(e);
            return allOk;
        }
        /*@Override
        public boolean remove(Object e) {
            boolean allOk=super.remove(e);
            String name=e.name;
            for(int i=0;i<tables.size();i++){
                if(name.equals(tables.get(i).name)) {
                    tables.remove(i);
                    break;
                }
            }
            return allOk;
        }*/
    };


    /*User(){

    }*/

    public TaskTable get(int index){
        return tables.get(index);
    }

    void addTable(TaskTable table){
        tables.add(table);
    }
    public int findTaskTableByName(String nameA){
        for(int i=0;i<tables.size();i++){
            if(tables.get(i).name.equals(nameA))
                return i;
        }
        return -1;
    }
    void removeTable(TaskTable table){//modify also in main activity
            tables.remove(table);
    }
}

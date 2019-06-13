package com.example.mo_trello_basic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "TaskTables",primaryKeys = "name")
public class TaskTable {


    //@ColumnInfo(name = "id")
    int id;


    @NonNull
    String name;

    public TaskTable(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

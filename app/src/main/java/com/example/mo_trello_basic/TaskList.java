package com.example.mo_trello_basic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "TaskLists",
        foreignKeys = @ForeignKey(entity = TaskTable.class,
                parentColumns = "id",
                childColumns = "taskTableID",
                onDelete = CASCADE))
public class TaskList  {

    @PrimaryKey
    int id;

    @ColumnInfo(name = "name")
    String name = "";

    @ColumnInfo(name = "taskTableID")
    int taskTableID;

    @Ignore
    public static String actualTaskTable;

    public TaskList(int id, @NonNull String name, int taskTableID) {
        this.id = id;
        this.name = name;
        this.taskTableID = taskTableID;
    }
}

package com.example.mo_trello_basic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "Tasks",
        foreignKeys = @ForeignKey(entity = TaskList.class,
                parentColumns = "name",
                childColumns = "taskListID",
                onDelete = CASCADE))
public class Task {

    @PrimaryKey
    int id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "taskListID")
    String taskListID;

    @Ignore
    public static String actualTaskList = null;

    public Task(int id, @NonNull String name, String taskListID) {
        this.id = id;
        this.name = name;
        this.taskListID = taskListID;
    }
}

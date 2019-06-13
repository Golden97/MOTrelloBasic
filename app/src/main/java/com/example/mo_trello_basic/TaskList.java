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
                parentColumns = "name",
                childColumns = "taskTableID",
                onDelete = CASCADE),primaryKeys = "name")
public class TaskList  {

    int id;

    @NonNull
    String name = "";

    @ColumnInfo(name = "taskTableID")
    String taskTableID;

    @Ignore
    public static String actualTaskTable;

    public TaskList(int id, @NonNull String name, String taskTableID) {
        this.id = id;
        this.name = name;
        this.taskTableID = taskTableID;
    }
}

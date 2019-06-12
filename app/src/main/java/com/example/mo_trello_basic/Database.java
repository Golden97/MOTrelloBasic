package com.example.mo_trello_basic;

import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {TaskTable.class, TaskList.class, Task.class}, version = 1)

public abstract class Database extends RoomDatabase {
    public abstract TaskTableInterface taskTableDAO();
    public abstract TaskListInterface taskListDAO();
    public abstract TaskInterface taskDAO();
}

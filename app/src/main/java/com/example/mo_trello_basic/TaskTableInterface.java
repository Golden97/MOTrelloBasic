package com.example.mo_trello_basic;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskTableInterface {
    @Query("SELECT * FROM TaskTables")
    List<TaskTable> getAllTaskTables();

    @Insert
    void addTaskTable(TaskTable taskTable);

    @Query("DELETE FROM TaskTables WHERE name = :name")
    void delete(String name);
}

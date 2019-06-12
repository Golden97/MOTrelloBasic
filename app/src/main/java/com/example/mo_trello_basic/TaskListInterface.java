package com.example.mo_trello_basic;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TaskListInterface {
    @Query("SELECT * FROM TaskLists WHERE taskTableID=:id")
    List<TaskList> getAllTaskLists(int id);

    @Insert
    void addTaskList(TaskList taskList);

    @Query("DELETE FROM TaskLists WHERE name = :name")
    void delete(String name);
}

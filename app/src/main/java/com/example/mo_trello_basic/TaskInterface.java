package com.example.mo_trello_basic;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.widget.ArrayAdapter;

import java.util.List;

@Dao
public interface TaskInterface {
    @Query("SELECT * FROM Tasks WHERE taskListID=:id")
    List<Task> getAllTasks(int id);

    @Insert
    void addTask(Task task);

    @Query("DELETE FROM Tasks WHERE name = :name")
    void delete(String name);
}
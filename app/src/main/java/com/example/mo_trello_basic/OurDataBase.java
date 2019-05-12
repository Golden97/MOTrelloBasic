package com.example.mo_trello_basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OurDataBase extends SQLiteOpenHelper {

    public OurDataBase(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS TaskTable(" +
                        "ID_TaskTable INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Name TEXT NOT NULL);" +
                        "");

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS TaskLists(" +
                        "ID_TaskList INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ID_TaskTable INTEGER NOT NULL," +
                        "Name TEXT NOT NULL," +
                        "CONSTRAINT fk_TaskTable FOREIGN KEY (ID_TaskTable) REFERENCES TaskTable(ID_TaskTable) ON DELETE CASCADE);" +
                        "");

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS Tasks(" +
                        "ID_Task INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ID_TaskList INTEGER NOT NULL," +
                        "Name TEXT NOT NULL," +
                        "CONSTRAINT fk_TaskLists FOREIGN KEY (ID_TaskList) REFERENCES TaskLists(ID_TaskList) ON DELETE CASCADE);" +
                        "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addTaskTableToDB(TaskTable table) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("Name", table.name);
        db.insertOrThrow("TaskTable", null, values);
    }

    public void addTaskListToDB(TaskList taskList, TaskTable table) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("ID_TaskTable", table.id);
        values.put("Name", taskList.name);
        db.insertOrThrow("TaskLists", null, values);
    }

    public void addTaskToDB(Task task, TaskList taskList) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("ID_TaskList", taskList.id);
        values.put("Name", task.name);
        db.insertOrThrow("Tasks", null, values);
    }

    public void removeTaskTableFromDB(TaskTable table) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {"" + table.id};
        db.delete("TaskTable", "ID_TaskTable=?", args);
    }

    public void removeTaskListFromDB(TaskList taskList) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {"" + taskList.id};
        db.delete("TaskLists", "ID_TaskList=?", args);
    }

    public void removeTaskFromDB(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        String[] args = {"" + task.id};
        db.delete("Tasks", "ID_Task=?", args);
    }
}

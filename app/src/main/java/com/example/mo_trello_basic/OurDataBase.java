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
                "CREATE TABLE IF NOT EXISTS Categories(" +
                        "ID_Category INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Name TEXT NOT NULL);" +
                        "");
        )

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS TaskLists(" +
                        "ID_TaskList INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ID_Category INTEGER NOT NULL," +
                        "Name TEXT NOT NULL," +
                        "FOREIGN KEY (ID_Category) REFERENCES Categories(ID_Category));" +
                        "");
        )

        sqLiteDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS Tasks(" +
                        "ID_Task INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "ID_TaskList INTEGER NOT NULL," +
                        "Name TEXT NOT NULL," +
                        "FOREIGN KEY (ID_TaskList) REFERENCES TaskLists(ID_TaskList));" +
                        "");
        )
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addCategory(Table table) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("Name", table.name);
        db.insertOrThrow("Categories", null, values);
    }

    public void addTaskList(TaskList taskList, Table table) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("ID_Category", )
        values.put("Name", taskList.name);
        db.insertOrThrow("TaskLists", null, values);
    }

    public void addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put("Name", task.name);
        db.insertOrThrow("Tasks", null, values);
    }
}

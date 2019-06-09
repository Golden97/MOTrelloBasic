package com.example.mo_trello_basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OurDataBase extends SQLiteOpenHelper {

        private static ContentValues values;
        private static final String TaskList = "TaskList";

        public OurDataBase(Context context) {
            super(context, "trello.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTaskTables = "CREATE TABLE " + DataBaseManager.DataEntry.TASKTABLES + " ( " +
                    DataBaseManager.DataEntry.ID_TASKTABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DataBaseManager.DataEntry.NAME + " TEXT NOT NULL);";

            db.execSQL(createTaskTables);


            String createTaskLists = "CREATE TABLE " + DataBaseManager.DataEntry.TASKLISTS + " ( " +
                    DataBaseManager.DataEntry.ID_TASKLIST + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DataBaseManager.DataEntry.ID_TASKTABLE + " INTEGER, " +
                    DataBaseManager.DataEntry.NAME + " TEXT NOT NULL, " +
                    "CONSTRAINT fk_TaskTable FOREIGN KEY (ID_TaskTable) REFERENCES TaskTable(ID_TaskTable) ON DELETE CASCADE);";

            db.execSQL(createTaskLists);


            db.execSQL(
                    "CREATE TABLE TaskLists(" +
                            "ID_TaskList INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "ID_TaskTable INTEGER DEFAULT NULL," +
                            "Name TEXT NOT NULL," +
                            "CONSTRAINT fk_TaskTable FOREIGN KEY (ID_TaskTable) REFERENCES TaskTable(ID_TaskTable) ON DELETE CASCADE);" +
                            "");

            db.execSQL(
                    "CREATE TABLE Tasks(" +
                            "ID_Task INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "ID_TaskList INTEGER DEFAULT NULL," +
                            "Name TEXT NOT NULL," +
                            "CONSTRAINT fk_TaskLists FOREIGN KEY (ID_TaskList) REFERENCES TaskLists(ID_TaskList) ON DELETE CASCADE);" +
                            "");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }

        public void addTaskTableToDB(TaskTable table) {
            SQLiteDatabase db = this.getWritableDatabase();
            values  = new ContentValues();
            values.put("Name", table.name);
            db.insertOrThrow("TaskTable", null, values);
        }

        public void addTaskListToDB(TaskList taskList, TaskTable table) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO TaskLists (ID_TaskTable, Name) "
                    + "VALUES( (SELECT ID_TaskTable FROM TaskTable"
                    + " WHERE Name=" + table.name + ")," + taskList.name + ")");
        }

        public void addTaskToDB(Task task, TaskList taskList) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("INSERT INTO Tasks (ID_TaskList, Name) "
                    + "VALUES( (SELECT ID_TaskList FROM TaskList"
                    + " WHERE Name=" + taskList.name + ")," + task.name + ")");
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

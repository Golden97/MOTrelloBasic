package com.example.mo_trello_basic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OurDataBase extends SQLiteOpenHelper {

        private static ContentValues values;

        public OurDataBase(Context context) {
            super(context, "trello.db", null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //String drop = "DROP TABLE IF EXISTS " + DataBaseManager.DataEntry.TASKTABLES + ';';

            String createTaskTables = "CREATE TABLE " + DataBaseManager.DataEntry.TASKTABLES + " ( " +
                    DataBaseManager.DataEntry.ID_TASKTABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DataBaseManager.DataEntry.NAME + " TEXT NOT NULL);";

            db.beginTransaction();
            try{
                //db.execSQL(drop);
               db.execSQL(createTaskTables);
            }catch(SQLException e){
               int  a=1;
            }
            db.setTransactionSuccessful();



            String createTaskLists = "CREATE TABLE " + DataBaseManager.DataEntry.TASKLISTS + " ( " +
                    DataBaseManager.DataEntry.ID_TASKLIST + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DataBaseManager.DataEntry.ID_TASKTABLE + " INTEGER, " +
                    DataBaseManager.DataEntry.NAME + " TEXT NOT NULL, " +
                    "CONSTRAINT fk_TaskTable FOREIGN KEY (ID_TaskTable) REFERENCES TaskTable(ID_TaskTable) ON DELETE CASCADE);";

            db.execSQL(createTaskLists);

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

        public Cursor getTTfromDB() {
            String[] col = {DataBaseManager.DataEntry.ID_TASKTABLE, DataBaseManager.DataEntry.NAME};

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.query("TaskTables", col, null, null, null, null, null );
            return cur;
        }

        public void addTaskTableToDB(TaskTable table) {
            SQLiteDatabase db = this.getWritableDatabase();
            values  = new ContentValues();
            values.put("Name", table.name);
            db.insertOrThrow(DataBaseManager.DataEntry.TASKTABLES, null, values);
        }

        public void addTaskListToDB(TaskList taskList) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("INSERT INTO " + DataBaseManager.DataEntry.TASKLISTS + " (" + DataBaseManager.DataEntry.ID_TASKLIST + ", Name) "
                    + "VALUES( (SELECT " + DataBaseManager.DataEntry.ID_TASKTABLE + " FROM " + DataBaseManager.DataEntry.TASKTABLES
                    + " WHERE Name=" + taskList.actualTaskTable + ")," + taskList.name + ")");
        }

        public void addTaskToDB(Task task) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("INSERT INTO " + DataBaseManager.DataEntry.TASKS + " (" + DataBaseManager.DataEntry.ID_TASKLIST + ", Name) "
                    + "VALUES( (SELECT " + DataBaseManager.DataEntry.ID_TASKLIST + " FROM " + DataBaseManager.DataEntry.TASKLISTS
                    + " WHERE Name=" + task.actualTaskList + ")," + task.name + ")");
        }

        public void removeTaskTableFromDB(String name) {
            SQLiteDatabase db = getWritableDatabase();
            String[] args = {"" + name};
            db.delete(DataBaseManager.DataEntry.TASKTABLES, "Name=?", args);
        }

        public void removeTaskListFromDB(String name) {
            SQLiteDatabase db = getWritableDatabase();
            String[] args = {"" + name};
            db.delete(DataBaseManager.DataEntry.TASKLISTS, "Name=?", args);
        }

        public void removeTaskFromDB(String name) {
            SQLiteDatabase db = getWritableDatabase();
            String[] args = {"" + name};
            db.delete(DataBaseManager.DataEntry.TASKS, "Name=?", args);
        }
    }

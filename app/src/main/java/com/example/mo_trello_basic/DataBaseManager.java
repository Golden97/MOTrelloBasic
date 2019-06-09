package com.example.mo_trello_basic;

public class DataBaseManager {

    public static final String DB_CREATE_TASK_TABLE =   "CREATE TABLE IF NOT EXISTS TaskTable(" +
                                                        "ID_TaskTable INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        "Name TEXT  DEFAULT NULL);";

    public static final String DB_CREATE_TASK_LIST =    "CREATE TABLE IF NOT EXISTS TaskLists(" +
                                                        "ID_TaskList INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        "ID_TaskTable INTEGER DEFAULT NULL," +
                                                        "Name TEXT NOT NULL," +
                                                        "CONSTRAINT fk_TaskTable FOREIGN KEY (ID_TaskTable) REFERENCES TaskTable(ID_TaskTable) ON DELETE CASCADE);";

    public static final String DB_CREATE_TASKS =    "CREATE TABLE IF NOT EXISTS Tasks(" +
                                                        "ID_Task INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                        "ID_TaskList INTEGER DEFAULT NULL," +
                                                        "Name TEXT NOT NULL," +
                                                        "CONSTRAINT fk_TaskLists FOREIGN KEY (ID_TaskList) REFERENCES TaskLists(ID_TaskList) ON DELETE CASCADE);";

}

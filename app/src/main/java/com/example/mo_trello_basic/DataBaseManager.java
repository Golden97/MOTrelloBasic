package com.example.mo_trello_basic;

import android.provider.BaseColumns;

public class DataBaseManager {
    public static final String DB_NAME = "trello.db";
    public static final int DB_VERSION = 1;

    public class DataEntry implements BaseColumns {
        public static final String TASKTABLES = "TaskTables";
        public static final String TASKLISTS = "TaskLists";
        public static final String TASKS = "Tasks";

        public static final String ID_TASKTABLE = "ID_TaskTable";
        public static final String ID_TASKLIST = "ID_TaskList";
        public static final String ID_TASK = "ID_Task";
        public static final String NAME = "Name";
    }

}

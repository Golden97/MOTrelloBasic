package com.example.mo_trello_basic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mo_trello_basic.MainActivity.db;
import static com.example.mo_trello_basic.MainActivity.taskTableMap;

public class TableActivity extends AppCompatActivity {
    public static int counter = 0;

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    public static HashMap<String, Integer> taskListMap = new HashMap<>();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_table);
        lvItems = findViewById(R.id.lvTaskLists);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        List<TaskList> list = db.taskListDAO().getAllTaskLists(taskTableMap.get(TaskList.actualTaskTable));
        for(TaskList taskList : list) {
            itemsAdapter.add(taskList.name);
        }

        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task.actualTaskList = itemsAdapter.getItem(position).toString();
                openTaskListActivity();
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTaskList(itemsAdapter, position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });


    }

    public void openTaskListActivity() {
        Intent intent = new Intent(this, TaskListActivity.class);
        startActivity(intent);
    }

    public void onAddTaskList(View v){
        EditText etNewItem = findViewById(R.id.etNewTaskList);
        String itemText = etNewItem.getText().toString();
        if(!itemText.equals("")) {
            itemsAdapter.add(itemText);
        }
        etNewItem.setText("");

        int id = 0;
        try {
            id = taskTableMap.get(TaskList.actualTaskTable);
        }catch (Exception e) {
            e.printStackTrace();
        }
            TaskList tl = new TaskList(++counter, itemText, id);
            db.taskListDAO().addTaskList(tl);
            taskListMap.put(tl.name, tl.id);

    }

    public void deleteTaskList(ArrayAdapter<String> adapter, int pos) {
        db.taskListDAO().delete(adapter.getItem(pos));
    }
}

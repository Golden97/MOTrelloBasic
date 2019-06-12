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

import static com.example.mo_trello_basic.MainActivity.db;

public class TaskListActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        lvItems = findViewById(R.id.lvTasks);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTask(view.toString());
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void openTaskListActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    public void onAddTask(View v) {
        EditText etNewItem = findViewById(R.id.etNewTask);
        String itemText = etNewItem.getText().toString();
        if (!itemText.equals("")) {
            itemsAdapter.add(itemText);
        }
        etNewItem.setText("");

        Task task = new Task(itemText);

        try {
            db.addTaskToDB(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String name) {
        try {
            db.removeTaskFromDB(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

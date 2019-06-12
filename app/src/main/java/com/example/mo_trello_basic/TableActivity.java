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

public class TableActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_table);
        lvItems = findViewById(R.id.lvTaskLists);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task.actualTaskList = view.toString();
                openTaskListActivity();
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTaskList(view.toString());
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

        TaskList tl = new TaskList(itemText);
        try {
            db.addTaskListToDB(tl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskList(String name) {
        try {
            db.removeTaskListFromDB(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

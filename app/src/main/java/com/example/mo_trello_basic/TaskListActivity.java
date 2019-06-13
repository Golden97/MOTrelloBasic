package com.example.mo_trello_basic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.mo_trello_basic.MainActivity.db;
import static com.example.mo_trello_basic.MainActivity.taskTableMap;
import static com.example.mo_trello_basic.TableActivity.taskListMap;

public class TaskListActivity extends AppCompatActivity {
    public static int counter = 0;

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

        List<Task> list = db.taskDAO().getAllTasks(Task.actualTaskList);
        for(Task task : list) {
            itemsAdapter.add(task.name);
        }

        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTask(itemsAdapter, position);
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTask(itemsAdapter, position);
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
        try {

            Task tl = new Task(++counter, itemText, Task.actualTaskList);
            db.taskDAO().addTask(tl);
        } catch (Exception e) {
            Toast.makeText(this, "This task already exists", Toast.LENGTH_LONG).show();;
        }
    }

    public void deleteTask(ArrayAdapter<String> adapter, int pos) {
        db.taskDAO().delete(adapter.getItem(pos));
    }
}

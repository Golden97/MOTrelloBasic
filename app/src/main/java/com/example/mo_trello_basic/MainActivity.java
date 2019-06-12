package com.example.mo_trello_basic;

import android.arch.persistence.room.Room;
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

import static com.example.mo_trello_basic.TableActivity.taskListMap;

public class MainActivity extends AppCompatActivity {

    public static int counter = 0;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    public static HashMap<String, Integer> taskTableMap = new HashMap<>();
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = findViewById(R.id.lvTaskTables);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);

        List<TaskTable> list = db.taskTableDAO().getAllTaskTables();
        for(TaskTable task : list) {
            itemsAdapter.add(task.name);
        }

        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskList.actualTaskTable = itemsAdapter.getItem(position).toString();
                openTableActivity();
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTaskTable(itemsAdapter, position);
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        db = Room.databaseBuilder(getApplicationContext(), Database.class, "trello.db")
                .allowMainThreadQueries().build();
    }

    public void openTableActivity() {
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);

    }

    public void onAddTaskTable(View v) {
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if (!itemText.equals("")) {
            itemsAdapter.add(itemText);
        }
        etNewItem.setText("");

        TaskTable tb = new TaskTable(++counter, itemText);
        try {
            db.taskTableDAO().addTaskTable(tb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            taskTableMap.put(tb.name, tb.id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskTable(ArrayAdapter<String> adapter, int pos) {
        db.taskTableDAO().delete(adapter.getItem(pos));
    }
}

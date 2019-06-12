package com.example.mo_trello_basic;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static int counter = 0;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    public static OurDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = findViewById(R.id.lvTaskTables);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskList.actualTaskTable = view.toString();
                openTableActivity();
            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteTaskTable(view.toString());
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        db = new OurDataBase(getApplicationContext());
        Cursor a=db.getTTfromDB();
        while(a.moveToNext()){
            itemsAdapter.add(a.getString(0));
        }
    }

    public void openTableActivity() {
        Intent intent = new Intent(this, TableActivity.class);
        startActivity(intent);

    }

    public void onAddTaskTable(View v) {
        EditText etNewItem = findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if(!itemText.equals("")) {
            itemsAdapter.add(itemText);
        }
        etNewItem.setText("");

        TaskTable tb = new TaskTable(itemText);
        try {
            db.addTaskTableToDB(tb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskTable(String name) {
        try {
            db.removeTaskTableFromDB(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

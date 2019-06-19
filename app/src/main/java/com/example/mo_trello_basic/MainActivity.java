package com.example.mo_trello_basic;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.mo_trello_basic.TableActivity.taskListMap;



public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient fusedLocationClient;
    public static int counter = 0;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    public static String readedFromXML;
    public static HashMap<String, Integer> taskTableMap = new HashMap<>();
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Integer xmlResFile = R.xml.tasktables;
        backgroundAsyncTask background=new backgroundAsyncTask(getApplicationContext()) ;
        background.execute(xmlResFile);

        lvItems = findViewById(R.id.lvTaskTables);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);

        db = Room.databaseBuilder(getApplicationContext(), Database.class, "trello.db")
                .allowMainThreadQueries().build();

        List<TaskTable> listt = db.taskTableDAO().getAllTaskTables();
        for(TaskTable task : listt) {
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


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.MENU_1:

                String[] tasktablesFromXML=readedFromXML.split(" ");
                for(String names : tasktablesFromXML) {
                    itemsAdapter.add(names);
                    addToDatabase(names);
                }
                break;
            case R.id.MENU_2:
                SharedPreferences sharedPref = this.getPreferences(getApplicationContext().MODE_PRIVATE);
                String txt = sharedPref.getString("dane", "");
                txt=txt+"FromSharedPreferences";
                itemsAdapter.add(txt);
                addToDatabase(txt);
                break;
            case R.id.MENU_3:
                Intent intent= new Intent(getApplicationContext(),GridActivity.class);
                startActivity(intent);
                break;
            case R.id.MENU_4:
                Intent intent2= new Intent(getApplicationContext(),SDActivity.class);
                startActivity(intent2);
                break;
            case R.id.MENU_5:

                /*Notification notification  = new Notification.Builder(this)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentTitle("Position")
                        .setContentText("your position is")
                        .setSmallIcon(R.drawable.cut)
                        .setAutoCancel(true)
                        .setVisibility(1)
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(notification., notification );*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
                    }
                }
                try {
                    fusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    String text = "";
                                    if (location == null) {
                                        text = "Unable to get location";
                                    } else text = location.toString();
                                    NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                        NotificationChannel notificationChannel = new NotificationChannel("channel01", "channel01", importance);
                                        notificationChannel.enableLights(true);
                                        notificationChannel.setLightColor(Color.RED);
                                        notificationChannel.enableVibration(true);
                                        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                        notificationManager.createNotificationChannel(notificationChannel);
                                    }

                                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(), "channel01");

                                    mBuilder.setSmallIcon(R.drawable.cut);
                                    mBuilder.setContentTitle("Position");
                                    mBuilder.setContentText(text);
                                    mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                                            .bigText(text));
                                    notificationManager.notify((int) (System.currentTimeMillis() / 1000), mBuilder.build());
                                }
                            });
                }catch(SecurityException e){
                    e.printStackTrace();
            }

                break;
            case R.id.MENU_6:
                finish();
                System.exit(0);
                break;
        }
        return false;
    }


public void addToDatabase(String string){
    TaskTable tb = new TaskTable(++counter, string);
    try {
        db.taskTableDAO().addTaskTable(tb);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @Override
    protected void onPause() {
        super.onPause();
        if(!items.isEmpty()) {
            SharedPreferences sharedPref = this.getPreferences(getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            editor.putString("dane", items.get(0));
            editor.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
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
    }

    public void deleteTaskTable(ArrayAdapter<String> adapter, int pos) {
        db.taskTableDAO().delete(adapter.getItem(pos));
    }
}

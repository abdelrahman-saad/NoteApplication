package com.example.noteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static SQLiteDatabase DB;
    private ArrayList<Note> list,high,low,mid;
    private ArrayList<String> titlelist;
    private ArrayAdapter listAdapter;
    private ListView listView;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        // initialize list
        list = high = low = mid = new ArrayList<>();
        titlelist = new ArrayList<>();

        // initialize Offline DB
        DB = this.openOrCreateDatabase("Note",MODE_PRIVATE,null);
        DB.execSQL("create table if not exists Note(Title varchar, Description varchar, Priority varchar)");

        // retrieve data
        Cursor c = DB.rawQuery("select * from Note",null);
        c.moveToFirst();

        int title = c.getColumnIndex("Title");
        int description = c.getColumnIndex("Description");
        int priority = c.getColumnIndex("Priority");
        String noteTitle = "";
        String noteDescription = "";
        String notePriority = "";
        Note note = null;
        while(c.moveToNext()){
            noteTitle = c.getString(title);
            noteDescription = c.getString(description);
            notePriority = c.getString(priority);
            note = new Note(noteTitle,noteDescription,notePriority);
            switch (notePriority) {
                case "HIGH":
                    high.add(note);
                    break;
                case "MID":
                    mid.add(note);
                    break;
                case "LOW":
                    low.add(note);
                    break;
                    default:
            }
//            list.add(note);
            titlelist.add(noteTitle);
            c.moveToNext();
        }

        list.addAll(high);
        list.addAll(mid);
        list.addAll(low);
        // initialize list Adapter

        listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView = findViewById(R.id.listview);
        listView.setAdapter(listAdapter);

        Log.d(TAG, "init: Success");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addNote){
            //goto create menu activity
        }

        return super.onOptionsItemSelected(item);
    }

    public static SQLiteDatabase getDB() {
        return DB;
    }

    public ArrayList getList() {
        return list;
    }

    public ArrayList getHigh() {
        return high;
    }

    public ArrayList getLow() {
        return low;
    }

    public ArrayList getMid() {
        return mid;
    }
}

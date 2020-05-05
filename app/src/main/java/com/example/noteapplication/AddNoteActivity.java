    package com.example.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

    public class AddNoteActivity extends AppCompatActivity {
    private String[] priorityList = {"HIGH","MID","LOW"};
    private TextView title;
    private TextView description;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        init();

    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, priorityList));
        spinner.setSelection(0);
        title = findViewById(R.id.noteTitleView);
        description = findViewById(R.id.noteDescriptionView);
    }

    public void addNote(View view){
        if(!title.getText().toString().trim().isEmpty() &&!description.getText().toString().trim().isEmpty()){
            String titletext = title.getText().toString();
            String descriptionText = description.getText().toString();
            String priorityText = spinner.getSelectedItem().toString();
            MainActivity.getDB().execSQL("insert into Note(Title, Description, priority) values(?,?,?)",new String[]{titletext,descriptionText,priorityText});
            Toast.makeText(this, "Note was successfully Added", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this,MainActivity.class));

        }else {
            Toast.makeText(this, "Please check entered values", Toast.LENGTH_SHORT).show();

        }
    }

}

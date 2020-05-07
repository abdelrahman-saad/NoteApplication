package com.example.noteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Show_Update_Note extends AppCompatActivity {
    private Intent intent;
    private int position;
    private TextView title;
    private TextView description;
    private Spinner spinner;
    private Note note;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__update__note);

        inti();

    }

    private void inti() {
        intent = getIntent();
        position = intent.getIntExtra("position",0);

        textView = findViewById(R.id.noteDescText);
        title = findViewById(R.id.noteTitleViewUpdate);
        description = findViewById(R.id.noteDescriptionViewUpdate);
        spinner = findViewById(R.id.spinnerUpdate);
        spinner.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,AddNoteActivity.getPriorityList()));

        note = MainActivity.getList().get(position);
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        spinner.setSelection(getSelection(note.getPriority()));
        title.setEnabled(false);
        description.setEnabled(false);
        spinner.setEnabled(false);
//        spinner.setClickable(false);

    }

    private int getSelection(String priority){
        String[] selection =AddNoteActivity.getPriorityList();
        if(priority.equals(selection[0])){
            return 0;
        }else if(priority.equals(selection[1]))
            return 1;
        else
            return 0;
    }



    public void editSaveButton(View view){
        Button btn = (Button)view;

        if(btn.getText().toString().equals("EDIT")){
            title.setEnabled(true);
            description.setEnabled(true);
            spinner.setEnabled(true);

            btn.setText("Save");
            textView.setText("Update Note");
        }else{
            if(!title.getText().toString().trim().isEmpty() &&!description.getText().toString().trim().isEmpty()){
                String titletext = title.getText().toString();
                String descriptionText = description.getText().toString();
                String priorityText = spinner.getSelectedItem().toString();
                MainActivity.getDB().execSQL("Update Note set Title =?, Description=?, priority=? where Title=? and Description=? and Priority=?",new String[]{titletext,descriptionText,priorityText,note.getTitle(),note.getDescription(),note.getPriority()});
                Toast.makeText(this, "Note was successfully Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
            }else {
                Toast.makeText(this, "Please check entered values", Toast.LENGTH_SHORT).show();
            }
        }

    }

}

package com.example.noteapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {
    private static String[] priorityList = {"HIGH", "MID", "LOW"};
    private TextView title;
    private TextView description;
    private TextView DateText;
    private TextView TimeText;
    private Spinner spinner;
    public int Ayear;
    public int Amonth;
    public int Adate;
    public int Ahour;
    public int Aminute;
View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        back = findViewById(R.id.beckground);
        CreateNotification();
        init();

       // hideKeyboardFrom(this,back);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void hideKeyboardFrom( View view) {
        Context context = this;
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void SetDate(View view) {
        //  startActivity(new Intent(this, timer_date.class));
        final Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        final int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            int  oneBasedMOnth = Integer.valueOf(month);
            oneBasedMOnth++;
                Ayear = year;
                Amonth = month;
                Adate = dayOfMonth;
                String ChosenDate = year + " " + oneBasedMOnth + " " + dayOfMonth;
                DateText.setText(ChosenDate);
            }
        }, YEAR, MONTH, DATE);
        datePickerDialog.show();
    }

    public void SetTime(View view) {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Ahour = hourOfDay;
                Aminute = minute;
                String S = "Hour" + hourOfDay + " " + "Minute" + minute;
                TimeText.setText(S);
            }
        }, HOUR, MINUTE, is24HourFormat);
        timePickerDialog.show();
    }
    public void CreateNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE){
            CharSequence name = "MY NOTE APP";
            String description = "this is our reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify note",name , importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    public void SetAlarm(View view) {

        Date dat = new Date();
        Calendar cal_alarm = Calendar.getInstance();
        Calendar cal_now = Calendar.getInstance();
        cal_now.setTime(dat);
        //  cal_alarm.setTime(dat);

        cal_alarm.set(Calendar.YEAR, Ayear);
        cal_alarm.set(Calendar.MONTH, Amonth);
        cal_alarm.set(Calendar.DATE, Adate);

        cal_alarm.set(Calendar.HOUR_OF_DAY, Ahour);
        cal_alarm.set(Calendar.MINUTE, Aminute);
        cal_alarm.set(Calendar.SECOND, 0);
        if (cal_alarm.before(cal_now)) {
            cal_alarm.add(Calendar.DATE, 1);
        }

        Intent myIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        assert manager != null;
        manager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "ALARM Created", Toast.LENGTH_LONG).show();


    }

    private void init() {
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, priorityList));
        spinner.setSelection(0);
        title = findViewById(R.id.noteTitleView);
        description = findViewById(R.id.noteDescriptionView);
        DateText = findViewById(R.id.Date);
        TimeText = findViewById(R.id.Time);
    }

    public void addNote(View view) {
        if (!title.getText().toString().trim().isEmpty() && !description.getText().toString().trim().isEmpty()) {
            String titletext = title.getText().toString();
            String descriptionText = description.getText().toString();
            String priorityText = spinner.getSelectedItem().toString();
            MainActivity.getDB().execSQL("insert into Note(Title, Description, priority) values(?,?,?)", new String[]{titletext, descriptionText, priorityText});
            Toast.makeText(this, "Note was successfully Added", Toast.LENGTH_SHORT).show();
         //**********************************************************************



//*****************************************************************************************
            startActivity(new Intent(this, MainActivity.class));

        } else {
            Toast.makeText(this, "Please check entered values", Toast.LENGTH_SHORT).show();

        }
    }

    public static String[] getPriorityList() {
        return priorityList;
    }
}

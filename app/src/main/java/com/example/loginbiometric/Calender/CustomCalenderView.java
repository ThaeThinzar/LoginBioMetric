package com.example.loginbiometric.Calender;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.loginbiometric.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnItemClick;

public class CustomCalenderView extends LinearLayout  {

    ImageButton mNextButton,mPreviousButton;
    TextView mCurrentDate;

    DBOpenHelper dpOpenHelper;
    GridView gridLayoutDay;

    private static final int MAX_CALENDARS_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;

    List<Date> dates = new ArrayList<>();
    List<Events> events = new ArrayList<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

    AlertDialog alertDialog;
    GridViewAdapter gridViewAdapter;

    public CustomCalenderView(Context context) {
        super(context);
    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        InitializeLayout();
        setUpCalender();
        mPreviousButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,-1);
                setUpCalender();
            }
        });
        mNextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,1);
                setUpCalender();
            }
        });
        gridLayoutDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
             if(parent.getVisibility() == View.GONE){
                 Toast.makeText(getContext(), "Gone..",Toast.LENGTH_LONG).show();
             } else {
                 AlertDialog.Builder builder = new AlertDialog.Builder(context);
                 builder.setCancelable(true);
                 View addView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_new_event_layout,null);
                 EditText typeEvent = addView.findViewById(R.id.ed_type_event);
                 ImageButton btnSetTime = addView.findViewById(R.id.btn_set_time);
                 TextView eventTime = addView.findViewById(R.id.tv_event_time);
                 Button buttonAddEvent = addView.findViewById(R.id.btn_add_event);
                 btnSetTime.setOnClickListener(new OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Calendar calendar = Calendar.getInstance();
                         int hours = calendar.get(Calendar.HOUR_OF_DAY);
                         int minutes = calendar.get(Calendar.MINUTE);
                         TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), R.style.Theme_AppCompat_Dialog,
                                 new TimePickerDialog.OnTimeSetListener() {
                                     @Override
                                     public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                         Calendar c = Calendar.getInstance();
                                         c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                         c.set(Calendar.MINUTE,minute);
                                         c.setTimeZone(TimeZone.getDefault());
                                         SimpleDateFormat hrFormat = new SimpleDateFormat("K:mm a",Locale.ENGLISH);
                                         String event_Time = hrFormat.format(c.getTime());
                                         typeEvent.setText(event_Time);
                                     }
                                 },hours,minutes, false);
                         timePickerDialog.show();
                     }
                 });
                 String date = dateFormat.format(dates.get(i));
                 String month = monthFormat.format(dates.get(i));
                 String year = yearFormat.format(dates.get(i));

                 buttonAddEvent.setOnClickListener(new OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         saveEvent(typeEvent.getText().toString(),eventTime.toString(),date,month,year);
                         setUpCalender();
                         alertDialog.dismiss();
                     }
                 });

                 builder.setView(addView);
                 alertDialog = builder.create();
                 alertDialog.show();
             }
            }
        });
    }
    private void InitializeLayout(){
       LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_calendar_layout,this);
        mNextButton =  view.findViewById(R.id.btn_next);
        mPreviousButton = view.findViewById(R.id.btn_prev);
        mCurrentDate = view.findViewById(R.id.tv_current_date);
        gridLayoutDay = view.findViewById(R.id.gv_days);
    }

    private void saveEvent(String event, String time, String date, String month, String year) {
        dpOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase  database = dpOpenHelper.getWritableDatabase();
        dpOpenHelper.SaveEvent(event,time,date,month,year,database);
        dpOpenHelper.close();
        Toast.makeText(getContext(),"Event Saved", Toast.LENGTH_LONG).show();

    }
    private void setUpCalender(){
        String currentDate = dateFormat.format(calendar.getTime());
        Log.d("CAlendaar:", currentDate);
        mCurrentDate.setText(currentDate);
        dates.clear();
        Calendar monthCalender = (Calendar) calendar.clone();
        monthCalender.set(Calendar.DAY_OF_MONTH,1);
        int firstDayofMonth = monthCalender.get(Calendar.DAY_OF_WEEK) - 1;
        monthCalender.add(Calendar.DAY_OF_MONTH, - firstDayofMonth);
        while (dates.size() < MAX_CALENDARS_DAYS){
            dates.add(monthCalender.getTime());
            monthCalender.add(Calendar.DAY_OF_MONTH, 1);

        }

        gridViewAdapter = new GridViewAdapter(context,dates, calendar, events);
        gridLayoutDay.setAdapter(gridViewAdapter);
    }
   /* @Override
    public void onClick(View view) {
        switch (view.getId()){
          case   R.id.btn_prev:
            calendar.add(Calendar.MONTH,-1);
            setUpCalender();
            break;
            case R.id.btn_next:
                calendar.add(Calendar.MONTH,1);
                setUpCalender();
        }
    }*/
}

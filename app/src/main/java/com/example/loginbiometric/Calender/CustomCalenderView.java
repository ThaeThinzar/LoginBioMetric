package com.example.loginbiometric.Calender;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.loginbiometric.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class CustomCalenderView extends LinearLayout implements View.OnClickListener {

    ImageButton mNextButton,mPreviousButton;
    TextView mCurrentDate;
    GridLayout gridLayoutDay;

    private static final int MAX_CALENDARS_DAYS = 42;
    Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
    Context context;

    List<Date> dates = new ArrayList<>();
    List<Events> events = new ArrayList<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.ENGLISH);
    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);

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
        mPreviousButton.setOnClickListener(this);
        mNextButton.setOnClickListener(this);
    }
    private void InitializeLayout(){
       LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_calendar_layout,this);
        mNextButton =  view.findViewById(R.id.btn_next);
        mPreviousButton = view.findViewById(R.id.btn_prev);
        mCurrentDate = view.findViewById(R.id.tv_current_date);
        gridLayoutDay = view.findViewById(R.id.gv_days);
    }

    private void setUpCalender(){
        String currentDate = dateFormat.format(calendar.getTime());
        Log.d("CAlendaar:", currentDate);
        mCurrentDate.setText(currentDate);

    }
    @Override
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
    }
}

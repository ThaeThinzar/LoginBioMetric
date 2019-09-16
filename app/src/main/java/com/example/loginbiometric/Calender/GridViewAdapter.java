package com.example.loginbiometric.Calender;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginbiometric.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GridViewAdapter extends ArrayAdapter {
    List<Date> dates;
    List<Events> events;
    Calendar currentDate;
    LayoutInflater inflater;
    TextView dayNum;
    TextView eventNum;
    public GridViewAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public GridViewAdapter(@NonNull Context context, List<Date> dates, Calendar currentDate, List<Events> events){
        super(context, R.layout.date_single_cell_layout);
        this.dates = dates;
        this.events = events;
        this.currentDate = currentDate;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Date monthDate = dates.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthDate);
        int DayNo = calendar.get(Calendar.DAY_OF_MONTH);
        int displayMonth = calendar.get(Calendar.MONTH) +1;
        int displayYear = calendar.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH)+1;
        int currentYear = currentDate.get(Calendar.YEAR);

        View view = convertView;
        if(view == null) {
            view = inflater.inflate(R.layout.date_single_cell_layout, parent,false);
        }
        dayNum = view.findViewById(R.id.tv_calender_day);
        eventNum = view.findViewById(R.id.tv_event_day);
        if(displayMonth == currentMonth && displayYear == currentYear) {
           // view.setBackgroundColor(getContext().getResources().getColor(R.color.colorGreen));


        }
        else {
            dayNum.setVisibility(View.GONE);
            dayNum.setTextColor(getContext().getResources().getColor(R.color.grey));
           // view.setBackgroundColor(getContext().getResources().getColor(R.color.grey));
        }

        dayNum.setText(String.valueOf(DayNo));
        Calendar eventCalender = Calendar.getInstance();
        ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0; i< events.size(); i++){
            eventCalender.setTime(convertStringToDate(events.get(i).getDATE()));
            if(DayNo == eventCalender.get(Calendar.DAY_OF_MONTH) && displayMonth == eventCalender.get(Calendar.MONTH)+1
            && displayYear == eventCalender.get(Calendar.YEAR)){
                arrayList.add(events.get(i).getEVENT());
                eventNum.setText(arrayList.size()+ "EVENTS");
            }
        }
        return view;
    }

    private  Date convertStringToDate(String eventDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(eventDate);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public int getPosition(@Nullable Object item) {
        return dates.indexOf(item);
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }
}

package com.example.loginbiometric;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.loginbiometric.Calender.CustomCalenderView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.customCalender)
    CustomCalenderView customCalenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this,this);
    }
}

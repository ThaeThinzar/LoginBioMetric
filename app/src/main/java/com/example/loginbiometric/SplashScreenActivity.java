package com.example.loginbiometric;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 1000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.P){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Under Android Pie",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), FingerPrintActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}

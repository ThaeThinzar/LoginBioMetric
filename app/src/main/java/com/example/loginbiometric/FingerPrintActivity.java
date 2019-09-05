package com.example.loginbiometric;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.multidots.fingerprintauth.AuthErrorCodes;
import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;

public class FingerPrintActivity extends AppCompatActivity implements FingerPrintAuthCallback {
    FingerPrintAuthHelper mFingerPrintAuthHelper;
    AlertDialog.Builder builder;
    Dialog dialog;

    public void newIntent(Context context,Class cls){
        Intent intent = new Intent(context,cls);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_login);
        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              newIntent(getApplicationContext(),HomeActivity.class);
            }
        });
        mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        showDialog(this);
    }

    private  void showDialog(Context context){
      dialog = new Dialog(context);
      dialog.setCancelable(false);
      dialog.setContentView(R.layout.dialog_biometric);
      Button btnCancel = (Button)dialog.findViewById(R.id.cancelBtn);
      btnCancel.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(getApplicationContext(),"Click cancel button",Toast.LENGTH_LONG).show();
              if(dialog.isShowing()){
                dialog.dismiss();
              }

          }
      });
      dialog.show();

    }
    @Override
    protected void onResume() {
        super.onResume();
        mFingerPrintAuthHelper.startAuth();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFingerPrintAuthHelper.stopAuth();
    }

    @Override
    public void onNoFingerPrintHardwareFound() {
        Toast.makeText(getApplicationContext(),"No finger print sensor Found.",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNoFingerPrintRegistered() {

    }

    @Override
    public void onBelowMarshmallow() {
        Toast.makeText(getApplicationContext(),"your device don't support finger print.",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        Toast.makeText(getApplicationContext(),"Authentication success",Toast.LENGTH_LONG).show();
        dialog.dismiss();
        newIntent(getApplicationContext(),HomeActivity.class);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
        switch (errorCode) {    //Parse the error code for recoverable/non recoverable error.
            case AuthErrorCodes.CANNOT_RECOGNIZE_ERROR:
                //Cannot recognize the fingerprint scanned.
                Toast.makeText(getApplicationContext(),"Cannot recognize the fingerprint scanned.",Toast.LENGTH_LONG).show();
                break;
            case AuthErrorCodes.NON_RECOVERABLE_ERROR:
                //This is not recoverable error. Try other options for user authentication. like pin, password.
              //  Toast.makeText(getApplicationContext(),"his is not recoverable error. Try other options for user authentication. like pin, password.",Toast.LENGTH_LONG).show();
                break;
            case AuthErrorCodes.RECOVERABLE_ERROR:
                //Any recoverable error. Display message to the user.
                Toast.makeText(getApplicationContext(),"Any recoverable error. Display message to the user",Toast.LENGTH_LONG).show();
                break;
        }
    }
}

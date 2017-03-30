package com.gopettingcodingchallenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.gopettingcodingchallenge.R;
import com.gopettingcodingchallenge.util.Constants;
import com.gopettingcodingchallenge.util.Utils;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Utils.getSharedPreference(SplashScreen.this).getBoolean(Constants.PREFERENCE_IS_USER_LOGGED_IN, false))
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                else
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();

            }
        }, 3000);
    }
}

package br.com.briefer.briefer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import br.com.briefer.briefer.util.PreferencesUtility;

public class SplashActivity extends AppCompatActivity {


    private static final int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(this::handleUserLogged, SPLASH_TIME_OUT);

    }

    private void handleUserLogged() {
        //Do all the startup verifications before start the activity
        if(PreferencesUtility.getLoggedStatus(getApplicationContext())){
            //TODO add connectivity check and local database sync

            //start menu activity
            Intent menuIntent = new Intent(this, MenuActivity.class);
            startActivity(menuIntent);
            finish();
        }else{
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }

    }
}

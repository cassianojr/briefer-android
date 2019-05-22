package br.com.briefer.briefer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                //fragments toggle
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        HomeFragment homeFragment = new HomeFragment();
                        transaction.replace(R.id.content_frame, homeFragment);
                        transaction.commit();

                        return true;
                    case R.id.navigation_new_briefing:
                        NewBriefingFragment newBriefingFragment = new NewBriefingFragment();
                        transaction.replace(R.id.content_frame, newBriefingFragment);
                        transaction.commit();

                        return true;
                    case R.id.navigation_profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        transaction.replace(R.id.content_frame, profileFragment);
                        transaction.commit();

                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();
        transaction.replace(R.id.content_frame, homeFragment);
        transaction.commit();
    }

}

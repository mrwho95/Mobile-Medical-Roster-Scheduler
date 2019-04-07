package com.example.dennis.medical;

import android.app.Notification;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Medical ES");

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);

        final DashboardFragment dashboardFragment = new DashboardFragment();
        final DutyAlertFragment dutyAlertFragment = new DutyAlertFragment();
        final ProfileFragment profileFragment = new ProfileFragment();
        final RosterFragment rosterFragment = new RosterFragment();
        final ChatFragment chatFragment = new ChatFragment();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.roster) {
                    setFragment(rosterFragment);
                    return true;
                } else if (id == R.id.dashboard) {
                    setFragment(dashboardFragment);
                    return true;
                }
//                else if (id == R.id.notification) {
//                    setFragment(dutyAlertFragment);
//                    return true;
//                }
                else if (id == R.id.staffprofile) {
                    setFragment(profileFragment);
                    return true;
                } else if (id == R.id.message) {
                    setFragment(chatFragment);
                    return true;
                }

                return false;
            }
        });

        navigationView.setSelectedItemId(R.id.roster);



    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast = Toast.makeText(this, "Press again to exit", Toast.LENGTH_LONG);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.dashboard:
                Toast.makeText(this,"Dashboard Selected", Toast.LENGTH_SHORT).show();
                Intent int1 = new Intent(Dashboard.this, Dashboard.class);
                startActivity(int1);
                return true;
            case R.id.roster:
                Toast.makeText(this,"Roster Schedule Selected", Toast.LENGTH_SHORT).show();
                Intent int2 = new Intent(Dashboard.this, Roster.class);
                startActivity(int2);
                return true;
           /* case R.id.staff:
                Toast.makeText(this,"Staff Preference Selected", Toast.LENGTH_SHORT).show();
                Intent int3 = new Intent(Dashboard.this, staff.class);
                startActivity(int3);
                return true;*/
          /*  case R.id.staffprofile:
                Toast.makeText(this,"Staff Profile Selected", Toast.LENGTH_SHORT).show();
                Intent int4 = new Intent(Dashboard.this, StaffProfile.class);
                startActivity(int4);
                return true;
            case R.id.notification:
                Toast.makeText(this,"Notification Selected", Toast.LENGTH_SHORT).show();
                Intent int5 = new Intent(Dashboard.this, DutyAlert.class);
                startActivity(int5);
                return true;
           /* case R.id.logout:
                Toast.makeText(this,"Sign out", Toast.LENGTH_SHORT).show();
                Intent int6 = new Intent(Dashboard.this, MainActivity.class);
                startActivity(int6);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

}

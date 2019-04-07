package com.example.dennis.medical;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

public class Roster extends AppCompatActivity {
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roster);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Roster Schedule");

    }
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
                Intent int1 = new Intent(Roster.this, Dashboard.class);
                startActivity(int1);
                return true;
            case R.id.roster:
                Toast.makeText(this,"Roster Schedule Selected", Toast.LENGTH_SHORT).show();
                Intent int2 = new Intent(Roster.this, Roster.class);
                startActivity(int2);
                return true;
         /*   case R.id.staff:
                Toast.makeText(this,"Staff Preference Selected", Toast.LENGTH_SHORT).show();
                Intent int3 = new Intent(Roster.this, staff.class);
                startActivity(int3);
                return true; */
            case R.id.staffprofile:
                Toast.makeText(this,"Staff Profile Selected", Toast.LENGTH_SHORT).show();
                Intent int4 = new Intent(Roster.this, StaffProfile.class);
                startActivity(int4);
                return true;
//            case R.id.notification:
//                Toast.makeText(this,"Notification Selected", Toast.LENGTH_SHORT).show();
//                Intent int5 = new Intent(Roster.this, DutyAlert.class);
//                startActivity(int5);
//                return true;
         /*   case R.id.logout:
                Toast.makeText(this,"Sign out", Toast.LENGTH_SHORT).show();
                Intent int6 = new Intent(Roster.this, MainActivity.class);
                startActivity(int6);
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

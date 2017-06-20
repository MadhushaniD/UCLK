package com.uclk.shani.youseelk.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.uclk.shani.youseelk.R;

import java.util.Calendar;
import java.util.List;

public class MainTourActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private static final String FIRST_TIME = "first_time";
    Toolbar toolbar;
    Button open;
    Button view;
    private NavigationView nview;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private int selectedId;
    private boolean mUserSawDrawer = false;

    Button next2;
    Button prev1;
    ArrayAdapter<String> adapter;
    List<String> list;
    private String[] startingLocations;
    private String[] endingLocations;

    Button to, from, at;
    int yearX,monthX,dayX, yearY, monthY, dayY, hourX,minuteX;
    static final int DIALOG_ID_FROM = 0;
    static final int DIALOG_ID_TO = 1;
    static final int DIALOG_ID_AT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tour);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Navigation Drawer
        //initializes the navigation view
        nview = (NavigationView) findViewById(R.id.navView);
        nview.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.layoutMainTour);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout ,toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        if (!didUserSeeDrawer()) {
            showDrawer();
            markDrawerSeen();
        } else {
            hideDrawer();
        }
        selectedId = savedInstanceState == null ? R.id.layoutMainTour : savedInstanceState.getInt(SELECTED_ITEM_ID);
        navigate(selectedId);


        //Next and Previous Buttons

        next2 = (Button) findViewById(R.id.btnNext2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(),SelectLocationsActivity.class);
                startActivity(intent1);
            }
        });


        //Spinners
        final Spinner s = (Spinner) findViewById(R.id.spinStartLoc);
        this.startingLocations = new String[] {"Kandy", "Colombo", "Kalutara", "Matale", "Kegalle","Kaduwela"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, startingLocations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        final CheckBox ret = (CheckBox)findViewById(R.id.cbReturn);
        final Spinner e = (Spinner)findViewById(R.id.spinEndLoc);

        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ret.isChecked()){
                    //Toast.makeText(MainTourActivity.this,"Checked!",Toast.LENGTH_SHORT).show();
                    int selected = s.getSelectedItemPosition();
                    e.setSelection(selected);
                    e.setEnabled(false);
                }
                else {
                    e.setEnabled(true);
                }
            }
        });

        this.endingLocations = new String[]{"Kandy", "Colombo","Kalutara","Matale","Kegalle","Kaduwela"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,endingLocations);
        e.setAdapter(adapter1);

        //DatePicker
        to = (Button)findViewById(R.id.btnTo);
        from = (Button) findViewById(R.id.btnFrom);

        setCurrentDates();
        showDialogOnButtonClick();

        //TimePicker
        at = (Button)findViewById(R.id.btnAtTime);

        at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_AT);
            }
        });

    }






    //Correct?????
    public void showDialogOnButtonClick(){

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_TO);
            }
        });

        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID_FROM);
            }
        });
    }


       @Override
    protected Dialog onCreateDialog(int id){

        switch (id){

            case DIALOG_ID_FROM:
                return new DatePickerDialog(this, fromListener, yearY, monthY, dayY);

            case DIALOG_ID_TO:
                return new DatePickerDialog(this, toListener, yearX, monthX, dayX);

            case DIALOG_ID_AT:
                return new TimePickerDialog(this,atListener, hourX,minuteX,false);
        }

        return null;
    }

    private TimePickerDialog.OnTimeSetListener atListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourX = hourOfDay;
            minuteX = minute;

            //Toast.makeText(MainTourActivity.this,hourX + ":" + minuteX, Toast.LENGTH_LONG).show();
            TextView timeAt = (TextView)findViewById(R.id.tvDisplayAt);
            timeAt.setText(hourX + ":" + minuteX);
        }
    };




    private DatePickerDialog.OnDateSetListener toListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            yearX = year;
            monthX = monthOfYear + 1;
            dayX = dayOfMonth;
            //Toast.makeText(MainTourActivity.this,yearX + "/" + monthX + "/" + dayX, Toast.LENGTH_LONG).show();

            TextView dto = (TextView)findViewById(R.id.tvDisplayTo);
            dto.setText(dayX + " / " + monthX + " / " + yearX);
        }
    };

    private DatePickerDialog.OnDateSetListener fromListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            yearY = year;
            monthY = monthOfYear + 1;
            dayY = dayOfMonth;
            //Toast.makeText(MainTourActivity.this,yearY + "/" + monthY + "/" + dayY, Toast.LENGTH_LONG).show();

            TextView dfrom = (TextView)findViewById(R.id.tvDisplayFrom);
            dfrom.setText(dayY + " / " + monthY + " / " + yearY);
        }
    };


    private void setCurrentDates(){

        final Calendar cal = Calendar.getInstance();
        yearX = cal.get(Calendar.YEAR);
        monthX = cal.get(Calendar.MONTH);
        dayX = cal.get(Calendar.DAY_OF_MONTH);

        yearY = cal.get(Calendar.YEAR);
        monthY = cal.get(Calendar.MONTH);
        dayY = cal.get(Calendar.DAY_OF_MONTH);

    }

    private boolean didUserSeeDrawer() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = sharedPreferences.getBoolean(FIRST_TIME, false);
        return mUserSawDrawer;
    }

    private void markDrawerSeen() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mUserSawDrawer = true;
        sharedPreferences.edit().putBoolean(FIRST_TIME, mUserSawDrawer).apply();
    }

    private void showDrawer(){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private void hideDrawer(){
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void navigate(int selectedId)
    {

        Intent intent = null;
        if(selectedId == R.id.layoutMainTour){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        if(selectedId == R.id.nav_myplans){
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this,MyPlansActivity.class);
            startActivity(intent);
        }

        if(selectedId == R.id.nav_upload){
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this,UploadActivity.class);
            startActivity(intent);
        }

        if(selectedId == R.id.nav_about){
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }

        if(selectedId == R.id.nav_settings){
            drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        if(selectedId == R.id.nav_logout){
            drawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(), "Logging out...",Toast.LENGTH_SHORT).show();
            intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuitem)
    {

        menuitem.setChecked(true);
        selectedId = menuitem.getItemId();
        navigate(selectedId);

        return true;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID,selectedId);
    }

    @Override
    public void onBackPressed()
    {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


}

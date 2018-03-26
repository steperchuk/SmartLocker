package com.sergeyteperchuk.lockscreen;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.sergeyteperchuk.lockscreen.utils.LockScreen;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHelper databaseHelper;

    private boolean enableService;

    private String[] klasses = new String[] { "1 класс", "2 класс", "3 класс", "4 класс", "5 класс", "6 класс", "7 класс" };

    private String[] intervals = new String[] { "1 мин", "3 мин", "15 мин", "30 мин", "45 мин", "60 мин", "90 мин"};

    Switch enableServiceSwitch;
    Spinner intervalSpinner;
    Spinner klassSpinner;
    ImageButton goToSubjectsButton;
    ImageButton goToStatisticsButton;
    ImageButton changePassButton;
    ImageButton startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.create_db();

        enableServiceSwitch = (Switch) findViewById(R.id.switch_enable_service);
        intervalSpinner = (Spinner) findViewById(R.id.spinner_interval);
        klassSpinner = (Spinner) findViewById(R.id.spinner_klass);
        goToSubjectsButton = (ImageButton) findViewById(R.id.button_subjects_list);
        goToStatisticsButton = (ImageButton) findViewById(R.id.button_statistics);
        changePassButton = (ImageButton) findViewById(R.id.button_change_password);
        startButton = (ImageButton) findViewById(R.id.startButton);

        setButtonAnimation(startButton);

        setSwitchTextEmptyForOldAndroidVersions();

        goToSubjectsButton.setOnClickListener(SettingsActivity.this);
        goToStatisticsButton.setOnClickListener(SettingsActivity.this);
        changePassButton.setOnClickListener(SettingsActivity.this);
        startButton.setOnClickListener(SettingsActivity.this);


        ArrayAdapter<String> klassesAddapter = new ArrayAdapter<String>(this, R.layout.styled_spinner, klasses);
        klassesAddapter.setDropDownViewResource(R.layout.checkedlistview);
        klassSpinner.setAdapter(klassesAddapter);

        ArrayAdapter<String> intervalsAddapter = new ArrayAdapter<String>(this, R.layout.styled_spinner, intervals);
        intervalsAddapter.setDropDownViewResource(R.layout.checkedlistview);
        intervalSpinner.setAdapter(intervalsAddapter);

        enableServiceSwitch.setChecked(getServiceState());
        intervalSpinner.setSelection((int)getInterval());
        klassSpinner.setSelection((int)getLevel());


        LockScreen.getInstance().init(this,true);
        if(LockScreen.getInstance().isActive()){
            enableServiceSwitch.setChecked(true);
            enableServiceSwitch.setText("On");
            setSwitchTextEmptyForOldAndroidVersions();
            enableService = true;
        }else{
            enableServiceSwitch.setChecked(false);
            enableServiceSwitch.setText("Off");
            setSwitchTextEmptyForOldAndroidVersions();
            enableService = false;
        }


        enableServiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (checked) {
                        enableService = true;
                        enableServiceSwitch.setText("On");
                        setSwitchTextEmptyForOldAndroidVersions();

                    } else {
                        enableService = false;
                        enableServiceSwitch.setText("Off");
                        setSwitchTextEmptyForOldAndroidVersions();
                    }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*
        Queries queries = new Queries(getApplicationContext());
        if (queries.isSubjectsSelected()) {
            enableServiceSwitch.setChecked(false);
            enableService = false;
            enableServiceSwitch.setText("Off");
            Toast.makeText(getApplicationContext(), "Предметы не выбраны", Toast.LENGTH_SHORT).show();
        }
        */
    }


    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.2F);

    @Override
    public void onClick(View view) {

        ImageButton button = (ImageButton)view;
        button.startAnimation(buttonClick);

        switch (button.getId()){

            case R.id.button_subjects_list:
                saveKlass();
                showSubjects();
                break;
            case R.id.button_statistics:
                saveKlass();
                Queries queries = new Queries(getApplicationContext());
                int selectedSubjectsCount = queries.getSelectedSubjectsNames(queries.getKlass()).size();
                if (selectedSubjectsCount > 0){
                    showStatistics();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Предметы не выбраны", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.button_change_password:
                dropPassword();
                showAuthentication();
                break;
            case R.id.startButton:
                EnableService(enableService);
                saveSettings();
                showDesktop();
                break;
            }

        }

    @Override
    public void onBackPressed() {
        showDesktop();
    }

    private void EnableService(boolean serviceState){
            if(serviceState){
                LockScreen.getInstance().active();
            }
            else {
                LockScreen.getInstance().deactivate();
            }
        }

    private void saveSettings(){
        SharedPreferences sharedPref = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("serviceState", enableServiceSwitch.isChecked());
        editor.putLong("klass", klassSpinner.getSelectedItemId());
        editor.putString("klass_value", klassSpinner.getSelectedItem().toString());
        editor.putLong("interval", intervalSpinner.getSelectedItemId());
        editor.putString("intervalValue", intervalSpinner.getSelectedItem().toString());
        editor.putString("tempInterval", intervalSpinner.getSelectedItem().toString());
        editor.commit();

    }

    private void saveKlass(){
        SharedPreferences sharedPref = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("klass_value", klassSpinner.getSelectedItem().toString());
        editor.commit();
    }

    private boolean getServiceState(){
        SharedPreferences sharedPref = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        return sharedPref.getBoolean("serviceState", false);
    }

    private long getLevel(){
        SharedPreferences sharedPref = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        return sharedPref.getLong("klass", 0);
    }

    private long getInterval(){
        SharedPreferences sharedPref = getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        return sharedPref.getLong("interval", 0);
    }

    private void dropPassword(){
        SharedPreferences sharedPref = getSharedPreferences("PasswordPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("password").commit();
        editor.putString("password", null);
        editor.commit();
    }

    private void showAuthentication(){
        Intent authentication = new Intent(getApplicationContext(), AuthenticationActivity.class);
        authentication.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(authentication);
    }

    private void showDesktop(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showSubjects(){
        Intent subjects = new Intent(getApplicationContext(), SubjectsActivity.class);
        subjects.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(subjects);
    }

    private void showStatistics(){
        Intent statistics = new Intent(getApplicationContext(), StatisticsActivity.class);
        statistics.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(statistics);
    }

    private void setButtonAnimation(ImageButton button){
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                button,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(310);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();
    }

    private void setSwitchTextEmptyForOldAndroidVersions(){
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            enableServiceSwitch.setText("");
        }
    }

}

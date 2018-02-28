package me.andika.lockscreen;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.github.orangegangsters.lollipin.lib.PinActivity;

import me.andika.lockscreen.utils.LockScreen;

public class SettingsActivity extends PinActivity implements View.OnClickListener {

    private boolean enableService;

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

        enableServiceSwitch = (Switch) findViewById(R.id.switch_enable_service);
        intervalSpinner = (Spinner) findViewById(R.id.spinner_interval);
        klassSpinner = (Spinner) findViewById(R.id.spinner_klass);
        goToSubjectsButton = (ImageButton) findViewById(R.id.button_subjects_list);
        goToStatisticsButton = (ImageButton) findViewById(R.id.button_statistics);
        changePassButton = (ImageButton) findViewById(R.id.button_change_password);
        startButton = (ImageButton) findViewById(R.id.startButton);


        goToSubjectsButton.setOnClickListener(SettingsActivity.this);
        goToStatisticsButton.setOnClickListener(SettingsActivity.this);
        changePassButton.setOnClickListener(SettingsActivity.this);
        startButton.setOnClickListener(SettingsActivity.this);

        LockScreen.getInstance().init(this,true);
        if(LockScreen.getInstance().isActive()){
            enableServiceSwitch.setChecked(true);
        }else{
            enableServiceSwitch.setChecked(false);

        }




        enableServiceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    enableService = true;
                }else{
                    enableService = false;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {

        ImageButton button = (ImageButton)view;

        switch (button.getId()){
            case R.id.button_subjects_list:

                break;
            case R.id.button_statistics:


                break;
            case R.id.button_change_password:


                break;
            case R.id.startButton:
                EnableService(enableService);

                break;
            }

        }

        private void EnableService(boolean enableService){
            if(enableService){
                LockScreen.getInstance().active();
            }
            else {
                LockScreen.getInstance().deactivate();
            }
        }

}

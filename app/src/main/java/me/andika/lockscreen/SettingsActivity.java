package me.andika.lockscreen;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.github.orangegangsters.lollipin.lib.PinActivity;

import me.andika.lockscreen.utils.LockScreen;

public class SettingsActivity extends PinActivity {


    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);


        LockScreen.getInstance().init(this,true);
        if(LockScreen.getInstance().isActive()){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);

        }

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){

                    LockScreen.getInstance().active();
                }else{
                    LockScreen.getInstance().deactivate();
                }
            }
        });

    }




}

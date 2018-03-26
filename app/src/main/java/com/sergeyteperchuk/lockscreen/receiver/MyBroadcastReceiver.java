package com.sergeyteperchuk.lockscreen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.sergeyteperchuk.lockscreen.utils.LockscreenService;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent startServiceIntent = new Intent(context, LockscreenService.class);

        if(!getServiceState(context)){
            return;
        }

        context.startService(startServiceIntent);

    }


    private boolean getServiceState(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("Preferences",Context.MODE_PRIVATE);
        return sharedPref.getBoolean("serviceState", false);
    }
}

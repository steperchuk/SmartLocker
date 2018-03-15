package me.sergeyteperchuk.lockscreen.utils;

/**
 * Created by andika on 2/15/17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import me.sergeyteperchuk.lockscreen.LockApplication;
import me.sergeyteperchuk.lockscreen.LockScreenActivity;
import me.sergeyteperchuk.lockscreen.Queries;
import me.sergeyteperchuk.lockscreen.SettingsActivity;
import me.sergeyteperchuk.lockscreen.R;


public class LockscreenService extends Service {
    private final String TAG = "LockscreenService";
    private int mServiceStartId = 0;
    private Context mContext = null;
    public int counter = 0;   // need to use global or preferences

    private NotificationManager mNM;



    private BroadcastReceiver mLockscreenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null != context) {
                if (//intent.getAction().equals(Intent.ACTION_SCREEN_OFF) || //
                        intent.getAction().equals(Intent.ACTION_TIME_TICK)) {

                    counter++;
                    startLockscreenActivity();
                }
            }
        }
    };

    private void stateRecever(boolean isStartRecever) {
        if (isStartRecever) {
            IntentFilter filter = new IntentFilter();
            //filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_TIME_TICK);
            registerReceiver(mLockscreenReceiver, filter);
        } else {
            if (null != mLockscreenReceiver) {
                unregisterReceiver(mLockscreenReceiver);
            }
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mServiceStartId = startId;
        stateRecever(true);
        Intent bundleIntet = intent;
        if (null != bundleIntet) {
            Log.d(TAG, TAG + " onStartCommand intent  existed");
        } else {
            Log.d(TAG, TAG + " onStartCommand intent NOT existed");
        }
        return LockscreenService.START_STICKY;
    }





    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        stateRecever(false);
        mNM.cancel(((LockApplication) getApplication()).notificationId);
    }

    private void startLockscreenActivity() {
        if(shouldShowLockScreen()){
            showLockScreen();
        }
    }

    /**
     * Show a notification while this service is running.
     */
    private void showNotification() {
        CharSequence text = "Работает";
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, SettingsActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_phone_lock)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentTitle(getText(R.string.app_name))
                .setContentText(text)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .build();

        mNM.notify(((LockApplication) getApplication()).notificationId, notification);
    }

    private boolean shouldShowLockScreen(){
        if(counter == Queries.getTempIntervalValue(getApplicationContext())){
            counter = 0;
            return true;
        }
        return false;
    }

    private void showLockScreen(){
        Queries queries = new Queries(getApplicationContext());
        int selectedSubjectsCount = queries.getSubjectsTables(queries.getKlass()).size();
        if(selectedSubjectsCount == 0){
            return;
        }

        Intent startLockscreenActIntent = new Intent(mContext, LockScreenActivity.class);
        startLockscreenActIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startLockscreenActIntent);
    }


}
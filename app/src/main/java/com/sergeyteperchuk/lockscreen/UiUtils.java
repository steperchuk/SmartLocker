package com.sergeyteperchuk.lockscreen;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by sergeyteperchuk on 3/27/18.
 */

public class UiUtils extends Activity{

    public double getScreenSize(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);

        screenInches = (double)Math.round(screenInches * 10) / 10;

        return screenInches;
    }

}

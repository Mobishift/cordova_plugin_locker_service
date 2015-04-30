package com.mobishift.cordova.plugins.lockerservice;

import android.os.Build;
import android.util.Log;

/**
 * Created by Alpha on 11/27/14.
 */
public class RootUtils {
    public static void closeBar(){
        try{
            //需要root 权限
            Build.VERSION_CODES vc = new Build.VERSION_CODES();
            Build.VERSION vr = new Build.VERSION();
            String ProcID = "79";

            if(vr.SDK_INT >= vc.ICE_CREAM_SANDWICH){
                ProcID = "42"; //ICS AND NEWER
            }

            //需要root 权限
            Process proc = Runtime.getRuntime().exec(new String[]{"su","-c","service call activity "+ ProcID +" s16 com.android.systemui"}); //WAS 79
            proc.waitFor();

        }catch(Exception ex){
            Log.d("closeBar", String.valueOf(ex));
            ex.printStackTrace();
        }
    }

    public static void showBar(){
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"su","-c","am startservice -n com.android.systemui/.SystemUIService"});
            proc.waitFor();
        } catch (Exception e) {
            Log.d("showBar", String.valueOf(e));
            e.printStackTrace();
        }
    }
}

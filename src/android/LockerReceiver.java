package com.mobishift.cordova.plugins.lockerservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LockerReceiver extends BroadcastReceiver{
    String myPkgName = "com.mobishift.cordova.plugins";//#包名
    String myActName = "com.mobishift.cordova.plugins.lockerservice";//#类名

    @Override
    public void onReceive(Context context, Intent intent) {
        //启动监听服务
        Intent myIntent = new Intent();
        myIntent.setAction(myPkgName + "." + myActName);
        context.startService(myIntent);
    }
}
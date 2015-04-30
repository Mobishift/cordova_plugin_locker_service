package com.mobishift.cordova.plugins.lockerservice;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

@SuppressWarnings("deprecation")
public class LockerService extends Service {
    private String TAG = "ScreenReceiver Log";
    private KeyguardManager keyguardManager = null;
    private KeyguardManager.KeyguardLock keyguardLock = null;
    Intent toMainIntent;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        //设置intent
        toMainIntent = new Intent("lockerService");//#设置Main.class为要跳转到的界面，既当解锁时要打开的界面
        toMainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//必须得有，不知为何

        //注册广播
        IntentFilter intentFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(screenReceiver, intentFilter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;//粘性进程
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(screenReceiver);
        //重启此服务
        startActivity(new Intent(LockerService.this,LockerService.class));
    }

    private BroadcastReceiver screenReceiver = new BroadcastReceiver() {

        @SuppressWarnings("static-access")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "intent.action = " + action);

            if (action.equals("android.intent.action.SCREEN_ON") || action.equals("android.intent.action.SCREEN_OFF")) {
                Log.d(TAG, "onReceive: action");
                //关闭锁屏
                keyguardManager = (KeyguardManager) context.getSystemService(context.KEYGUARD_SERVICE);
                keyguardLock = keyguardManager.newKeyguardLock("");
                keyguardLock.disableKeyguard();
                Log.e("", "closed the keyGuard");

                //打开主界面
                startActivity(toMainIntent);
            }

        }
    };

}
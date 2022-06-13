package com.example.sportnetsheled;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.BatteryManager;

import com.google.firebase.auth.FirebaseAuth;

public class MyReceiver extends BroadcastReceiver {

    private final static String BATTERY_LEVEL = "level";
    BatteryManager myBatteryManager;
    private AlertDialog ad;

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BATTERY_LEVEL, 0);
        myBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);

       boolean isBatteryCharging =  myBatteryManager.isCharging();

        if(level < 15 && !isBatteryCharging){
            AlertDialog.Builder builder = new AlertDialog.Builder(context)
                    .setTitle("Attention")
                    .setMessage("your battery is below 15% please charge it! the application can not run at low battery!")
                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setNeutralButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert);

            if(ad == null || !ad.isShowing()){
                ad = builder.create();
                ad.show();
            }
        }
    }
}
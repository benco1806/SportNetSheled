package com.example.sportnetsheled;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.BatteryManager;

public class MyReceiver extends BroadcastReceiver {

    private final static String BATTERY_LEVEL = "level";
    BatteryManager myBatteryManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BATTERY_LEVEL, 0);
        myBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);

        if(level < 15 && !myBatteryManager.isCharging()){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("battery is lower then 15%, please charge!, low battery effects application's running");
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });


            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
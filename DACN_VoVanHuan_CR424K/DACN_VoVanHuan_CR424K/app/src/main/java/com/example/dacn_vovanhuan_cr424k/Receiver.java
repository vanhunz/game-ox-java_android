package com.example.dacn_vovanhuan_cr424k;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && Manager.ACTION_NOTIFY.equals(intent.getAction())) {
            String message = intent.getStringExtra(Manager.EXTRA_MESSAGE);
            if (message != null) {
                // Hiển thị thông báo
                Toast.makeText(context, "Thông báo: " + message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
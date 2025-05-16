package com.example.dacn_vovanhuan_cr424k;

import android.content.Context;
import android.content.Intent;

public class Manager {
    public static final String ACTION_NOTIFY = "com.example.doan_xo.NOTIFY"; // Action broadcast
    public static final String EXTRA_MESSAGE = "message";

    // Phương thức gửi thông báo
    public static void sendNotification(Context context, String message) {
        Intent intent = new Intent(ACTION_NOTIFY);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}


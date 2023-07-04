package com.strapes.android.addams.fake.call.wednesday.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.strapes.android.addams.fake.call.wednesday.message.activities.FaceBookVideoCallScreen;
import com.strapes.android.addams.fake.call.wednesday.message.activities.FaceBookVoiceCallScreen;
import com.strapes.android.addams.fake.call.wednesday.message.activities.SelectCallingOptions;
import com.strapes.android.addams.fake.call.wednesday.message.activities.SystemCallScreen;
import com.strapes.android.addams.fake.call.wednesday.message.activities.WhatsAppVideoCallScreen;
import com.strapes.android.addams.fake.call.wednesday.message.activities.WhatsAppVoiceCallScreen;

public class CallingReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SelectCallingOptions.rd_form == 1) {
            if (SelectCallingOptions.rd_vid == 2) {
                Intent intent2 = new Intent(context, WhatsAppVoiceCallScreen.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent2);
            } else if (SelectCallingOptions.rd_vid == 1) {
                Intent intent3 = new Intent(context, WhatsAppVideoCallScreen.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent3);
            } else {
                Intent intent4 = new Intent(context, WhatsAppVideoCallScreen.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent4);
            }
        } else if (SelectCallingOptions.rd_form == 2) {
            if (SelectCallingOptions.rd_vid == 2) {
                Intent intent5 = new Intent(context, FaceBookVoiceCallScreen.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent5);
            } else if (SelectCallingOptions.rd_vid == 1) {
                Intent intent6 = new Intent(context, FaceBookVideoCallScreen.class);
                intent6.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent6);
            } else {
                Intent intent7 = new Intent(context, FaceBookVideoCallScreen.class);
                intent7.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent7);
            }
        } else if (SelectCallingOptions.rd_form != 3) {
        } else {
            if (SelectCallingOptions.rd_vid == 2) {
                Intent intent8 = new Intent(context, SystemCallScreen.class);
                intent8.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent8);
            } else if (SelectCallingOptions.rd_vid == 1) {
               /* Intent intent9 = new Intent(context, TeleVideoCallActivity.class);
                intent9.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent9);*/
            } else {
              /*  Intent intent10 = new Intent(context, TeleVideoCallActivity.class);
                intent10.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent10);*/
            }
        }
    }
}

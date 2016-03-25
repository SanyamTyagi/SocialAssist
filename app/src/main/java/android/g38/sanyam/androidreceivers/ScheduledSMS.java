package android.g38.sanyam.androidreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;

/**
 * Created by SANYAM TYAGI on 3/24/2016.
 */
public class ScheduledSMS extends BroadcastReceiver{
    Bundle extras;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        extras = intent.getExtras();
        String ext=extras.getString("extras");
        String[] arr=ext.split("!@#");
        String number=arr[0];
        String message=arr[1];
        SmsManager manager=SmsManager.getDefault();
        manager.sendTextMessage(number, null, message, null, null);

    }

}

package android.g38.sanyam.androidreceivers;

/**
 * Created by SANYAM TYAGI on 3/21/2016.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.Tasks;
import android.g38.socialassist.R;
import android.os.BatteryManager;

import java.util.GregorianCalendar;

/**
 * Created by SANYAM TYAGI on 3/20/2016.
 */
public class BatteryReceiver  extends BroadcastReceiver {
    static Cursor cursor;
    String pluggedInFlag="";
    String pluggedOutFlag="";
    String below15Flag="";
    Intent intentPost;

    @Override
    public void onReceive(Context context, Intent intent) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        cursor=context.getContentResolver().query(Tasks.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        pluggedInFlag=cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
        cursor.move(Integer.parseInt(String.valueOf(R.string.pluged_out_index)));
        pluggedOutFlag=cursor.getString(cursor.getColumnIndex("state")).trim();
        cursor.move(Integer.parseInt(String.valueOf(R.string.below_15_index)));
        below15Flag=cursor.getString(cursor.getColumnIndex("state")).trim();

        if(isCharging){
            if(pluggedInFlag.equalsIgnoreCase("true")){
                String className = cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim();
                try {
            intentPost=new Intent(context, Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
            intentPost.putExtra("extra",""+cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim());
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Long time = new GregorianCalendar().getTimeInMillis()+1000;
            alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(context, 1,
                    intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
        }
        }else{
            if(pluggedOutFlag.equalsIgnoreCase("true")){

            }
        }
        if(level<15){
            if(below15Flag.equalsIgnoreCase("true")){

            }
        }
    }

}
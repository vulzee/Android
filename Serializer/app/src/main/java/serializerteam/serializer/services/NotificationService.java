package serializerteam.serializer.services;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Random;

import serializerteam.serializer.R;

/**
 * Created by xxx on 2017-05-16.
 */

public class NotificationService extends BroadcastReceiver {

    private int mId= new Random().nextInt();
  //  private static String title="ShowTitle";
   // private static String episodeTitle="EpisodeTitle";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.logo_serializer)
                    .setContentTitle(intent.getStringExtra("showTitle"))
                    .setContentText(intent.getStringExtra("episodeTitle"));

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(mId, mBuilder.build());

        wl.release();
    }

    public void setAlarm(Context context, String title, String episodeTitle, int hour, int minute)
    {
      //  this.title=title;
    //    this.episodeTitle=episodeTitle;
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra("showTitle",title);
        i.putExtra("episodeTitle",episodeTitle);
        PendingIntent pi = PendingIntent.getBroadcast(context, new Random().nextInt(), i, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if(hour<calendar.get(Calendar.HOUR_OF_DAY))
            return;
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
//        am.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                calendar.getTimeInMillis(), pi);
       //calendar.add(Calendar.MILLISECOND,5000);
        am.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pi);
//        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                calendar.getTimeInMillis(), pi);
    }

    public void setAlarm(Context context,String title, String episodeTitle, long timeInMillis)
    {
     //  this.title=title;
   //     this.episodeTitle=episodeTitle;
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, NotificationService.class);
        i.putExtra("showTitle",title);
        i.putExtra("episodeTitle",episodeTitle);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY,hour);

        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                timeInMillis, pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, NotificationService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

package serializerteam.serializer.services;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import serializerteam.serializer.NextShowWidget;
import serializerteam.serializer.R;
import serializerteam.serializer.api.ApiSettings;
import serializerteam.serializer.database.ShowsDbAdapter;
import serializerteam.serializer.dto.EpisodeDto;
import serializerteam.serializer.dto.ShowDto;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.Math.abs;

public class CheckShowsService extends BroadcastReceiver
{
    public static String date="";
    //o tyle godzin i minut ma byc wczesniej przypomniane
    public static int notifyMinute=60;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        final ConnectivityManager connMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String actualDate = format1.format(calendar.getTime());

        if ((!wifi.isAvailable() && !mobile.isAvailable())||date.equals(actualDate)) {
            return;
        }

        ////////////////

        ComponentName name = new ComponentName(context, NextShowWidget.class);
        int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
        NextShowWidget.updateAppWidget(context, AppWidgetManager.getInstance(context), ids[0]);

        /////////////////


        final Context contextTmp=context;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        ApiSettings.usersApi.getUserShows(context.getSharedPreferences("serializer", MODE_PRIVATE).getString("userId", null)).enqueue(new Callback<int[]>() {
            @Override
            public void onResponse(Call<int[]> call, Response<int[]> response) {
                int[] myFavouriteShows = new int[response.body().length];
                System.arraycopy(response.body(), 0, myFavouriteShows, 0, response.body().length);
                for (int i = 0; i < myFavouriteShows.length; i++) {
                   getNextEpisode(contextTmp,myFavouriteShows[i]);
                }
            }

            @Override
            public void onFailure(Call<int[]> call, Throwable t) {
                Log.e("ERR", t.getMessage());
            }
        });
        date = actualDate;
        wl.release();
    }
//need to be somewhere else
    private void getNextEpisode( final Context context,final int showId) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(calendar.getTime());
        ApiSettings.episodeApi.getEpisodeByDate(showId,formatted).enqueue(new Callback<EpisodeDto[]>() {
            @Override
            public void onResponse(Call<EpisodeDto[]> call, Response<EpisodeDto[]> response) {
                if (response.body() != null) {
                    EpisodeDto[] e=response.body();
                    for(int m=0;m<e.length;m++) {
                        int hour = Integer.parseInt(e[m].getAirtime().substring(0, 2));// - notifyHour;
                        //hour = notifyMinute > 0 ? hour - 1 : hour;
                        int minute = Integer.parseInt(e[m].getAirtime().substring(e[m].getAirtime().length() - 2, e[m].getAirtime().length()));// - notifyMinute;
                        //minute = minute < 0 ? minute + 60 : minute;
                        NotificationService ns = new NotificationService();

                        ShowsDbAdapter dbAdapter = new ShowsDbAdapter(context);
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hour);
                        c.set(Calendar.MINUTE,minute);
                        c.add(Calendar.MINUTE,-abs(notifyMinute));
                        ns.setAlarm(context, dbAdapter.getShowName(showId), e[m].getName(), c.getTimeInMillis());
                        dbAdapter.closeDbContext();
                    }
                }
            }

            @Override
            public void onFailure(Call<EpisodeDto[]> call, Throwable t) {
                Log.e("ERR", t.toString());
            }
        });
    }
    //useless
    public void setAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, CheckShowsService.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
       // am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, pi); // Millisec * Second * Minute

        // Set the alarm to start at approximately 2:00 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,2);


        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, CheckShowsService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

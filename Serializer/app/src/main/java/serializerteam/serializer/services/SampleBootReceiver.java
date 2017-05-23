//package serializerteam.serializer.services;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//
///**
// * Created by xxx on 2017-05-16.
// */
//public class SampleBootReceiver extends BroadcastReceiver {
//
//    public static CheckShowsService alarm = new CheckShowsService();
//    @Override
//    public void onReceive(Context context, Intent intent)
//    {
//        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
//        {
//            alarm.setAlarm(context);
//        }
//    }
//}
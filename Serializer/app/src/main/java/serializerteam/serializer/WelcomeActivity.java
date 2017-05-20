package serializerteam.serializer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import serializerteam.serializer.services.NotificationService;
import serializerteam.serializer.services.SampleBootReceiver;

public class WelcomeActivity extends AppCompatActivity {
    private float x1,x2;
    static final int MIN_DISTANCE = 150;
    RadarView mRadarView = null;
   // Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mRadarView = (RadarView) findViewById(R.id.radarView);
//        new ShedulerJobService().scheduleJob(this);
//        //intent = new Intent(this, NotificationService.class);
//        startService(intent);

//        try {
//            NotificationService ns = new NotificationService();
//            //TODO
//            ns.setAlarm(getApplicationContext(), "Episode is coming!", "odcinek", 17, 19);
//           // Intent intent = new Intent(this,NotificationService.class);
//           // sendBroadcast(intent);
//        }catch(Exception e){
//            Log.d("ERR",e.toString());
//        }
       enableReceiver();
    }

    private void enableReceiver(){
        ComponentName receiver = new ComponentName(this, SampleBootReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        SampleBootReceiver.alarm.setAlarm(getApplicationContext());
        //disable receiver
//        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE){
                    startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                    WelcomeActivity.this.finish();
                }

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}

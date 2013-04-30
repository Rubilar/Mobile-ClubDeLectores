package club.lectores;

import static club.lectores.CommonUtilities.SENDER_ID;
import static club.lectores.CommonUtilities.displayMessage;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.preference.PreferenceManager;
import club.lectores.R;


import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;


public class GCMIntentService extends GCMBaseIntentService {


    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        displayMessage(context, getString(R.string.gcm_registered));
        ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        displayMessage(context, getString(R.string.gcm_unregistered));
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            Log.i(TAG, "Ignoring unregister callback");
        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        SharedPreferences pref =
    			PreferenceManager.getDefaultSharedPreferences(
    					GCMIntentService.this);	
        String message = intent.getStringExtra("message");
        String url = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");           
		if(pref.getBoolean(type, false)){    	
	        Log.i(TAG, "Received message");
	        generateNotification(context, message, url); 
		}      
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        String url = "http://club.mersap.com/mobileN/";
        generateNotification(context, message, url);
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        Log.i(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }

    private void generateNotification(Context context, String message, String url) {
        int icon = R.drawable.ic_stat_gcm;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        Notification notification = new Notification(icon, message, when);
        String title = context.getString(R.string.app_name);
        
        Intent notificationIntent = new Intent(context, Browser.class);
        notificationIntent.removeExtra("Url");    
        notificationIntent.putExtra("Url",url);

        int requestID = (int) System.currentTimeMillis();
    
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intent = PendingIntent.getActivity(context, requestID ,notificationIntent, 0 );
        
        notification.setLatestEventInfo(context, title, message, intent);
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.flags 	  |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
        
        
        
        
        
    }

}

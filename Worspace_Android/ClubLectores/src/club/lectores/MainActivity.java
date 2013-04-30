package club.lectores;

import static club.lectores.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static club.lectores.CommonUtilities.SENDER_ID;
import static club.lectores.CommonUtilities.SERVER_URL;

import org.apache.cordova.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends DroidGap {
	
	AsyncTask<Void, Void, Void> mRegisterTask;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNotNull(SERVER_URL, "SERVER_URL");
        checkNotNull(SENDER_ID, "SENDER_ID");
        
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);  

	    registerReceiver(mHandleMessageReceiver,
	    new IntentFilter(DISPLAY_MESSAGE_ACTION));
	    final String regId = GCMRegistrar.getRegistrationId(this);	
        if (regId.equals("")) {	
            GCMRegistrar.register(this, SENDER_ID);
        }
	    super.loadUrl("file:///android_asset/www/index.html");	          	
	    if (GCMRegistrar.isRegisteredOnServer(this)) {
	    	
	    } else {
	        final Context context = this;
	        mRegisterTask = new AsyncTask<Void, Void, Void>() {
	            @Override
	            protected Void doInBackground(Void... params) {
	                boolean registered =
	                        ServerUtilities.register(context, regId);
	                if (!registered) {
	                    GCMRegistrar.unregister(context);
	                }
	                return null;
	            }
	            @Override
	            protected void onPostExecute(Void result) {
	                mRegisterTask = null;
	            }
	        };
	        mRegisterTask.execute(null, null, null);
	    }
	}
        
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.options_opt1:{
				startActivity(new Intent(MainActivity.this, 
		                 DisplayOptions.class));
            return true;
            } 
            case R.id.options_exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        unregisterReceiver(mHandleMessageReceiver);
        GCMRegistrar.onDestroy(this);
        super.onDestroy();
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }

    private final BroadcastReceiver mHandleMessageReceiver =
        new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };
}
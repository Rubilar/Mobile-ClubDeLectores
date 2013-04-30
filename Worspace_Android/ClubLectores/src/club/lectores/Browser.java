package club.lectores;

import android.os.Bundle;
import org.apache.cordova.DroidGap;


public class Browser extends DroidGap {
	
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle InstanceState) {
        super.onCreate(InstanceState);   
        // setContentView(R.layout.browser);  
    	//Bundle ong = getIntent().getExtras();   
        //String url = ong.getString("file:///android_asset/www/index.html");        
        super.loadUrl("file:///android_asset/www/index.html");
 
        
    }
    
    public void onDestroy() {
        super.onDestroy();
    }

}
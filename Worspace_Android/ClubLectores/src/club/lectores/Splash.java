package club.lectores;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
 
public class Splash extends Activity {
 
	private final int SPLASH_DISPLAY_LENGTH = 2000;
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        setContentView(R.layout.splash); /*Tenemos un splash.xml*/
 
        new Handler().postDelayed(new Runnable(){
        	public void run(){
				/*Pasados los dos segundos inicia la activity "activityApp"*/
        		Intent intent = new Intent(Splash.this, MainActivity.class);
        		startActivity(intent);
				/*Destruye esta*/
        		finish();
        	};
 
        }, SPLASH_DISPLAY_LENGTH);
 
    }
}
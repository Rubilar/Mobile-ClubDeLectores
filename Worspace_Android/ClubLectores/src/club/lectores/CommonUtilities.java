package club.lectores;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    static final String SERVER_URL = "http://club.mersap.com:8080/gcm_server_inversiones/";
    static final String SENDER_ID = "804740345831";
    static final String TAG = "Inversiones";
    static final String DISPLAY_MESSAGE_ACTION ="com.google.android.gcm.demo.app.DISPLAY_MESSAGE";
    static final String EXTRA_MESSAGE = "message";

    
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
    }
}

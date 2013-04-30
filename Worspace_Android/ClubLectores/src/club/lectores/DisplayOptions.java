package club.lectores;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class DisplayOptions extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.opciones);
    }
}

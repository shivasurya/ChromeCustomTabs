package customtabs.ivb.com;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    CustomTabsServiceConnection  customTabsServiceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String mpackage = "com.android.chrome";
        final EditText url = (EditText)findViewById(R.id.editText);
        final Button btn = (Button)findViewById(R.id.button);
        customTabsServiceConnection = new CustomTabsServiceConnection() {
                    @Override
                    public void onCustomTabsServiceConnected(ComponentName componentName,CustomTabsClient customTabsClient) {
                        customTabsClient.warmup(0L);
                        final CustomTabsSession customTabsSession = customTabsClient.newSession(null);
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String mUrl = url.getText().toString();
                                Uri uri = Uri.parse(mUrl);
                                boolean maylaunch = customTabsSession.mayLaunchUrl(uri, null, null);
                                CustomTabsIntent obj = new CustomTabsIntent.Builder(customTabsSession).build();
                                obj.launchUrl(MainActivity.this, uri);

                            }
                        });
                    }



                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
        };


        CustomTabsClient.bindCustomTabsService(getApplicationContext(), mpackage, customTabsServiceConnection);

    }
}

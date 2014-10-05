package com.Neutrolysis.WiFiNotifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "  is the your choice....please wait",
				Toast.LENGTH_LONG).show();
		//context.startService(new Intent(context, MyService.class));

	}

}

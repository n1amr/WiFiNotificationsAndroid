package com.Neutrolysis.WiFiNotifications;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;
import java.util.LinkedList;

import android.R;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class ListenService extends Service {
	static LinkedList<String> queue = new LinkedList<>(Arrays.asList("one",
			"two", "three"));
	int n = 0;

	public void show(String message) {
		queue.addLast(message);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	NotificationManager notificationManager;
	PendingIntent pendingIntent;

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "Service started", Toast.LENGTH_SHORT).show();

		notificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// build notification
		// the addAction re-use the same intent to keep the example short
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				MainActivity.class), 0);
		final Service me = this;
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				Thread t = new Thread(new ListenTask(queue));
				t.start();
				while (true) {
					if (queue.size() > 0) {
						String nextMsg = queue.pollFirst();
						NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
								me).setSmallIcon(R.drawable.dark_header)
								.setContentTitle("My notification")
								.setContentText(nextMsg);
						mBuilder.setContentIntent(pendingIntent);
						mBuilder.setDefaults(Notification.DEFAULT_SOUND);
						mBuilder.setAutoCancel(true);
						notificationManager.notify(nextMsg.hashCode(),
								mBuilder.build());
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		thread.start();
	}
}

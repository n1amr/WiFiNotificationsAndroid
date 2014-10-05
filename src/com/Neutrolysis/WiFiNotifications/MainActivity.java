package com.Neutrolysis.WiFiNotifications;

import com.Neutrolysis.WiFiNotifications.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView tvLatitude;
	TextView tvLongitude;
	Button btnOk;
	boolean isPressed = false;
	String provider;
	Location location;

	// int lili = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent i=new Intent(this,ListenService.class);
		
		startService(i);
		
		tvLatitude = (TextView) findViewById(R.id.tvLatitude);
		tvLongitude = (TextView) findViewById(R.id.tvLongitude);
		btnOk = (Button) findViewById(R.id.btn);
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isPressed = !isPressed;
				stopService(new Intent(MainActivity.this, ListenService.class));
			}
		});

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						tvLatitude.setText(String.valueOf(location
								.getLatitude()));
						tvLongitude.setText(String.valueOf(location
								.getLongitude()));
					}

					@Override
					public void onProviderDisabled(String provider) {
						tvLatitude.setText("No provider");
						tvLongitude.setText("No provider");
					}

					@Override
					public void onProviderEnabled(String provider) {

					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {

					}

				});
	}

}
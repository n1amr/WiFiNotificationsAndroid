package com.Neutrolysis.WiFiNotifications;

import java.net.*;
import android.util.*;

public class SendMessageTask implements Runnable {
	MulticastSocket multicastSocket;
	DatagramPacket datagramPacket;
	String msg;

	public SendMessageTask(String text) {
		msg = text;
		try {
			multicastSocket = new MulticastSocket(WifiConstants.PORT_NO);
			multicastSocket.joinGroup(InetAddress.getByName(WifiConstants.GROUP_ADDRESS));
		} catch (Exception e) {
			Log.v("Socket Error: ", e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			datagramPacket = new DatagramPacket(msg.getBytes(),
					msg.getBytes().length,
					InetAddress.getByName(WifiConstants.GROUP_ADDRESS),
					WifiConstants.PORT_NO);
			multicastSocket.setTimeToLive(WifiConstants.TIME_TO_LIVE);
			multicastSocket.send(datagramPacket);
		} catch (Exception e) {
			Log.v("Packet Sending Error: ", e.getMessage());
		}
	}

}

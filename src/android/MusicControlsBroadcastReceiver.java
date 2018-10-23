package com.homerours.musiccontrols;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.view.KeyEvent;

public class MusicControlsBroadcastReceiver extends BroadcastReceiver {
	private CallbackContext cb;
	private MusicControls musicControls;


	public MusicControlsBroadcastReceiver(MusicControls musicControls){
		this.musicControls=musicControls;
	}

	public void setCallback(CallbackContext cb){
		this.cb = cb;
	}

	public void stopListening(){
		if (this.cb != null){
			this.cb.success("{\"message\": \"music-controls-stop-listening\" }");
			this.cb = null;
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (this.cb != null){
			String message = intent.getAction();
			int notiId = 0;
			String notiData = "";
			if (intent.hasExtra("NOTIFICATION_ID")) {
				Bundle pextras = intent.getExtras();
				notiId = pextras.getInt("NOTIFICATION_ID");
				notiData = pextras.getString("NOTIFICATION_DATA");
			}

			if (message.equals("music-controls-destroy")){
				// Close Button
				this.cb.success("{\"message\": \"music-controls-destroy\", \"data\": \""+notiData+"\"}");
				this.cb = null;
			} else {
				this.cb.success("{\"message\": \"" + message + "\",\"data\": \""+notiData+"\"}");
				this.musicControls.destroySinglePlayerNotification(notiId);
				this.cb = null;
			}


		}

	}
}

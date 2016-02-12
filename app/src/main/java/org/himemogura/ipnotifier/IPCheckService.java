package org.himemogura.ipnotifier;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by pri on 2016/02/13.
 */
public class IPCheckService extends Service {
	public static final String TAG = "IPNotifier";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "************** onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);

		Log.d(TAG, "############# onStartCommand");

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}

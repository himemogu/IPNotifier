package org.himemogura.ipnotifier;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pri on 2016/02/13.
 */
public class IPCheckService extends Service {
	public static final String TAG = "IPNotifier";
	public static final String URL = "https://www.cman.jp/network/support/go_access.cgi";
	private static final String PREF_IPADDR = "PREF_IPADDR";


	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String html = getHtml(URL);
		return START_STICKY;
	}

	private String getHtml(final String urlStrig) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d(TAG, "run");
				try {
					URL url = new URL(urlStrig);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(10000);
					conn.setConnectTimeout(15000);
					conn.setRequestMethod("GET");
					conn.setDoInput(true);
					conn.connect();
					String result = readIt(conn.getInputStream());
					onResultHttp(result);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		}).start();
		return "";
	}

	private void onResultHttp(String result) {
		String html = Util.seekWord(result, "あなたの利用しているIPアドレス");
		html = Util.seekWord(html, "<div");
		html = Util.seekWord(html, "<div");
		html = Util.seekWord(html, ">");
		String ip = Util.getBefoerWord(html, "</div>").trim();
		Log.d(TAG, ip);
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String ipAddr = pref.getString(PREF_IPADDR, "");
		if (ipAddr.equals(ip)) {
			Mail.sebdMail("pri-ed ip 通知", ip);
		} else {
			Mail.sebdMail("pri-ed ip 変更通知", ip);
			SharedPreferences.Editor editor = pref.edit();
			editor.putString(PREF_IPADDR, ip);
			editor.commit();
		}
	}

	public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		try {
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
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

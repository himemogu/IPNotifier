package org.himemogura.ipnotifier;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by pri on 2016/02/13.
 */
public class ScheduleManager {
	/**
	 * スケジュールを設定します。
	 * @param context
	 */
	public static void setSchedule(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		long time = System.currentTimeMillis();
		if(!isSetPending(context)) {
			Intent intent = new Intent(context, IPCheckService.class);
			PendingIntent p = PendingIntent.getService(
					context,
					-1,
					intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
//			long delay = 60 * 60 * 1000; // 1時間間隔で定期的に処理を行う
			long delay = 3 * 60 * 1000; // 1時間間隔で定期的に処理を行う
			am.setRepeating(AlarmManager.RTC, time, delay, p);
		}
	}

	/**
	 * スケジュールが設定されているかどうかを返します。
	 * @param context
	 */
	public static boolean isSetPending(Context context) {
		Intent intent = new Intent(context, IPCheckService.class);
		PendingIntent p = PendingIntent.getService(
				context,
				-1,
				intent,
				PendingIntent.FLAG_NO_CREATE);
		if(p == null) {
			return false;
		}else {
			return true;
		}
	}

	/**
	 * スケジュールをキャンセルします。
	 * @param context
	 */
	public static void cancelSchedule(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, IPCheckService.class);
		PendingIntent p = PendingIntent.getService(
				context,
				-1,
				intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(p);
		p.cancel(); // これを行わないとisSetPendingで意図した値が返ってこない
	}
}

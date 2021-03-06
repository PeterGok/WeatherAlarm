package com.mahcks.weatheralarm;

import android.app.*;
import android.content.*;
import android.os.IBinder;
import android.os.SystemClock;

import java.util.Calendar;


public class AlarmScheduler{
	
	public static void setSnooze(Context context){
		AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(AlarmReceiver.ACTION);

		PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+3*1000*60  , alarmIntent);

	}
	
	public static void setAlarm(Context context, Alarm alarm){
		
		AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(AlarmReceiver.ACTION);
		intent.putExtra("isCres", alarm.isCres);

		int hour = Integer.valueOf(alarm.time.substring(0,2));
		int min = Integer.valueOf(alarm.time.substring(3,5));
		Boolean used = false;
		for(int i=0;i<7;i++){
			if(alarm.days.charAt(i)=='t'){
				used = true;
				// Set the alarm to start at 8:30 a.m.
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(System.currentTimeMillis());
				calendar.set(Calendar.SECOND,0);
				calendar.set(Calendar.DAY_OF_WEEK, i+1);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, min);
				
				System.out.println("Setting alamr: "+i+" "+hour+" "+min+" "+calendar.getTimeInMillis());
		
				System.out.println((calendar.getTimeInMillis()-System.currentTimeMillis())/1000.0f);
				// setRepeating() lets you specify a precise custom interval--in this case,
				// 20 minutes.
				
				float time = calendar.getTimeInMillis();
				
				if(calendar.getTimeInMillis()-System.currentTimeMillis()<=0){
					time += 1000 * 60 * 60 * 24 * 7;
				}
			
				PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(),(alarm.id+1)*10+i,intent,PendingIntent.FLAG_UPDATE_CURRENT);
				alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, (long) time,
				        1000 * 60 * 60 * 24 * 7, alarmIntent);
			}
		}
		if(!used){
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.SECOND,0);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, min);
			
			float time = calendar.getTimeInMillis();
			
			if(calendar.getTimeInMillis()-System.currentTimeMillis()<=0){
				time += 1000 * 60 * 60 * 24 * 7;
			}
			
			PendingIntent alarmIntent = PendingIntent.getBroadcast(context.getApplicationContext(),(alarm.id+1)*10,intent,PendingIntent.FLAG_UPDATE_CURRENT);
			
			alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,(long) time,
			        1000 * 60 * 60 * 24 * 7, alarmIntent);
		}
		
	}
	
	public static void removeAlarm(Context context,int id){
		for(int i=0;i<7;i++){
			AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(context, AlarmReceiver.class);
			PendingIntent alarmIntent = PendingIntent.getBroadcast(context,(id+1)*10+i,intent,PendingIntent.FLAG_UPDATE_CURRENT);
			alarmMgr.cancel(alarmIntent);
		}
	}
	
	
}

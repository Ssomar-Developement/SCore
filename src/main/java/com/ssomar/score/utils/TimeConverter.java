package com.ssomar.score.utils;

import java.sql.Timestamp;

public class TimeConverter {

	@SuppressWarnings("deprecation")
	public static String LongToText(long time) {
		Timestamp ts = new Timestamp(time);
		String nanos = ts.getNanos()+"";
		nanos = nanos.substring(0, 1);

		String timer = "";
		if(ts.getHours()-1 == 0 ) {
			//timer = MessageMain.getInstance().getMessage(Message.shortDisplayTime);
			timer = timer.replaceAll("%M%", ts.getMinutes()+"");
			timer = timer.replaceAll("%S%", ts.getSeconds()+"");
			timer = timer.replaceAll("%N%", nanos);
		}
		else {
			//timer = MessageMain.getInstance().getMessage(Message.displayTime);
			timer = timer.replaceAll("%H%", (ts.getHours()-1)+"");
			timer = timer.replaceAll("%M%", ts.getMinutes()+"");
			timer = timer.replaceAll("%S%", ts.getSeconds()+"");
			timer = timer.replaceAll("%N%", nanos);
		}
		
		return timer;
	}
}

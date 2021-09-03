package com.ssomar.score.commands;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;

import com.ssomar.score.SsomarDev;

public class LogFilter implements Filter {

	private boolean debug = false;

	private List<String> messageToHide = new ArrayList<>();

	public LogFilter() {
		messageToHide.add("Applied effect");
		messageToHide.add("Gave");
		messageToHide.add("Displaying particle");
		messageToHide.add("Summoned new");
		messageToHide.add("Played sound");
		messageToHide.add("Teleported");
		messageToHide.add("Could not set the block");
		messageToHide.add("Changed the block at");
		messageToHide.add("Unable to apply this effect");
		messageToHide.add("That position is not loaded");
		messageToHide.add("The particle was not visible for anybody");
	}


	public Filter.Result checkMessage(String message) {
		//SsomarDev.testMsg(message+" > "+FilterManager.getInstance().getCurrentlyInRun());
		if(FilterManager.getInstance().isSilenceOuput()) {
			boolean hide = false;
			for(String s : messageToHide) {
				if(message.contains(s)) {
					hide=true;
					break;
				}
			}
			if (hide) return Filter.Result.DENY;
			else return Filter.Result.NEUTRAL;

		}return Filter.Result.NEUTRAL;
	}

	public Filter.Result filter(LogEvent event) {
		if(debug) SsomarDev.testMsg("FILTER: 0" );
		return checkMessage(event.getMessage().getFormattedMessage());
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
		if(debug) SsomarDev.testMsg("FILTER: 1" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4) {
		if(debug) SsomarDev.testMsg("FILTER: 2" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
		if(debug) SsomarDev.testMsg("FILTER: 3" );
		return checkMessage(message.toString());
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
		if(debug) SsomarDev.testMsg("FILTER: 4" );
		return checkMessage(message.getFormattedMessage());
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5) {
		if(debug) SsomarDev.testMsg("FILTER: 5" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6) {
		if(debug) SsomarDev.testMsg("FILTER: 6" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7) {
		if(debug) SsomarDev.testMsg("FILTER: 7" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8) {
		if(debug) SsomarDev.testMsg("FILTER: 8" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
		if(debug) SsomarDev.testMsg("FILTER: 9" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
		if(debug) SsomarDev.testMsg("FILTER: 10" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
		if(debug) SsomarDev.testMsg("FILTER: 11" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
		if(debug) SsomarDev.testMsg("FILTER: 12" );
		return checkMessage(message);
	}

	public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
		if(debug) SsomarDev.testMsg("FILTER: 13" );
		return checkMessage(message);
	}

	public Filter.Result getOnMatch() {
		return Filter.Result.NEUTRAL;
	}

	public Filter.Result getOnMismatch() {
		return Filter.Result.NEUTRAL;
	}

	@Override
	public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String msg,
			Object... params) {
		if(debug) SsomarDev.testMsg("FILTER: 14" );
		return null;
	}

	@Override
	public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Object msg,
			Throwable t) {
		if(debug) SsomarDev.testMsg("FILTER: 15" );
		return null;
	}

	@Override
	public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Message msg,
			Throwable t) {
		if(debug) SsomarDev.testMsg("FILTER: 16" );
		return null;
	}
}

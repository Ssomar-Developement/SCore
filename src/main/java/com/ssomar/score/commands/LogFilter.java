package com.ssomar.score.commands;


import com.ssomar.score.SsomarDev;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.List;

public class LogFilter implements Filter, LifeCycle {

    private final boolean debug = false;

    private final List<String> messageToHide = new ArrayList<>();

    public LogFilter() {
        messageToHide.add("Applied effect");
        messageToHide.add("Playing effect");
        messageToHide.add("Title command successfully executed");
        messageToHide.add("Gave");
        messageToHide.add("Given");
        messageToHide.add("Giving");
        messageToHide.add("Displaying particle");
        messageToHide.add("Summoned new");
        messageToHide.add("Played sound");
        messageToHide.add("Teleported");
        messageToHide.add("Could not set the block");
        messageToHide.add("Changed the block at");
        messageToHide.add("Unable to apply this effect");
        messageToHide.add("That position is not loaded");
        messageToHide.add("The particle was not visible for anybody");
        messageToHide.add("Disabling SCore");
        messageToHide.add("Successfully filled");
        messageToHide.add("Replaced a slot on");
        messageToHide.add("The number you have entered");
        messageToHide.add("Killed");
        messageToHide.add("No entity was found");
        messageToHide.add("Unknown command");
        messageToHide.add("Modified entity data of");
        messageToHide.add("Target has no effects to remove");
        messageToHide.add("Target doesn't have the requested effect");
        messageToHide.add("Showing new title");
        messageToHide.add("Showing new subtitle");
    }


    public Filter.Result checkMessage(String message) {
        //SsomarDev.testMsg(message+" > "+FilterManager.getInstance().getCurrentlyInRun());
        if (FilterManager.getInstance().isSilenceOuput()) {
            boolean hide = false;
            for (String s : messageToHide) {
                if (message != null && message.contains(s)) {
                    hide = true;
                    break;
                }
            }
            if (hide) return Filter.Result.DENY;
            else return Filter.Result.NEUTRAL;

        }
        return Filter.Result.NEUTRAL;
    }

    public Filter.Result filter(LogEvent event) {
        SsomarDev.testMsg("FILTER: 0", debug);
        return checkMessage(event.getMessage().getFormattedMessage());
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object... arg4) {
        SsomarDev.testMsg("FILTER: 1", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4) {
       SsomarDev.testMsg("FILTER: 2", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Object message, Throwable arg4) {
        SsomarDev.testMsg("FILTER: 3", debug);
        return checkMessage(message.toString());
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, Message message, Throwable arg4) {
       SsomarDev.testMsg("FILTER: 4", debug);
        return checkMessage(message.getFormattedMessage());
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5) {
        SsomarDev.testMsg("FILTER: 5", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6) {
        SsomarDev.testMsg("FILTER: 6", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7) {
        SsomarDev.testMsg("FILTER: 7", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8) {
       SsomarDev.testMsg("FILTER: 8", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9) {
        SsomarDev.testMsg("FILTER: 9", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10) {
        SsomarDev.testMsg("FILTER: 10", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11) {
        SsomarDev.testMsg("FILTER: 11", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12) {
        SsomarDev.testMsg("FILTER: 12", debug);
        return checkMessage(message);
    }

    public Filter.Result filter(Logger arg0, Level arg1, Marker arg2, String message, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Object arg10, Object arg11, Object arg12, Object arg13) {
        SsomarDev.testMsg("FILTER: 13", debug);
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
        SsomarDev.testMsg("FILTER: 14", debug);
        return null;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Object msg,
                         Throwable t) {
        SsomarDev.testMsg("FILTER: 15", debug);
        return null;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Message msg,
                         Throwable t) {
        SsomarDev.testMsg("FILTER: 16", debug);
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isStarted() {
        return false;
    }
}

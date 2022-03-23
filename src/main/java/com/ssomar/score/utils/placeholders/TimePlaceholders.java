package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.Optional;

public class TimePlaceholders extends PlaceholdersInterface implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Optional<Integer> timeOpt;
    private boolean isInTick;

    public void setTimePlcHldr(int time, boolean isInTick) {
        this.timeOpt = Optional.ofNullable(time);
        this.isInTick = isInTick;
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        if (timeOpt != null && timeOpt.isPresent()) {
            int time = timeOpt.get();
            int calculTime = time;
            if (isInTick) calculTime = calculTime / 20;
            int hour = calculTime / 3600;
            calculTime = calculTime % 3600;
            int min = calculTime / 60;
            int sec = calculTime % 60;


            toReplace = replaceCalculPlaceholder(toReplace, "%time%", time + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%time_H%", hour + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%time_M%", min + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%time_S%", sec + "", true);
        }

        return toReplace;
    }
}

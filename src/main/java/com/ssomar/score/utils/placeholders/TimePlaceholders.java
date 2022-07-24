package com.ssomar.score.utils.placeholders;

import java.io.Serializable;
import java.util.Optional;

public class TimePlaceholders extends PlaceholdersInterface implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Optional<Double> timeOpt;

    public void setTimePlcHldr(double time) {
        this.timeOpt = Optional.ofNullable(time);
    }

    public String replacePlaceholder(String s) {
        String toReplace = s;
        if (timeOpt != null && timeOpt.isPresent()) {
            double time = timeOpt.get();
            int hour;
            int min;
            int sec;
            if (time > 0) {
                int calculTime = (int) time;
                hour = calculTime / 3600;
                calculTime = calculTime % 3600;
                min = calculTime / 60;
                sec = calculTime % 60;
                toReplace = replaceCalculPlaceholder(toReplace, "%time_S%", sec + "", true);
            } else {
                hour = 0;
                min = 0;
                toReplace = replaceCalculPlaceholder(toReplace, "%time_S%", time + "", false);
            }


            toReplace = replaceCalculPlaceholder(toReplace, "%time%", time + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%time_H%", hour + "", true);
            toReplace = replaceCalculPlaceholder(toReplace, "%time_M%", min + "", true);
        }

        return toReplace;
    }
}

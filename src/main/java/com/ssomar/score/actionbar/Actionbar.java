package com.ssomar.score.actionbar;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Actionbar {

    private String name;

    private boolean active;

    private boolean desactivation;

    private Integer time;


    public Actionbar(String name, Integer time) {
        this.name = name;
        this.time = time;
        this.active = false;
        this.desactivation = false;
    }
}

package com.ssomar.score.actionbar;

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


    public boolean isDesactivation() {
        return desactivation;
    }

    public void setDesactivation(boolean desactivation) {
        this.desactivation = desactivation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }
}

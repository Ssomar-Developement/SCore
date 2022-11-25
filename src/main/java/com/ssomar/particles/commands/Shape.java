package com.ssomar.particles.commands;

import lombok.Getter;

import java.lang.reflect.Method;

@Getter
public class Shape {

    String name;
    Parameters parameters;
    Method method;


    public Shape(String name, Method method) {
        this.name = name;
        this.method = method;
        this.parameters = new Parameters();
        parameters.init(method);
    }

    public void run(Parameters parameters){
        try {
            method.invoke(this, parameters.getParametersValues());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

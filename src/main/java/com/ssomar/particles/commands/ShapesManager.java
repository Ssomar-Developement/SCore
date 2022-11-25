package com.ssomar.particles.commands;

import java.lang.reflect.Method;
import java.util.*;

public class ShapesManager {

    private static ShapesManager instance;

    Map<String, Shape> shapes;

    public ShapesManager() {
        instance = this;
        shapes = new HashMap<>();
        init();
    }

    public void init() {
        List<String> whitelistedShapes = new ArrayList<>();
        whitelistedShapes.add("blackSun");
        whitelistedShapes.add("circle");
        whitelistedShapes.add("diamond");
        whitelistedShapes.add("circularBeam");
        whitelistedShapes.add("filledCircle");
        whitelistedShapes.add("chaoticDoublePendulum");
        whitelistedShapes.add("magicCircles");
        whitelistedShapes.add("infinity");
        whitelistedShapes.add("cone");
        whitelistedShapes.add("ellipse");
        whitelistedShapes.add("blackhole");
        whitelistedShapes.add("rainbow");
        whitelistedShapes.add("crescent");
        whitelistedShapes.add("waveFunction");

        Method[] methods = XParticle.class.getDeclaredMethods();
        for (Method method : methods) {
            if (whitelistedShapes.contains(method.getName())) {
                //System.out.println("Loading shape: " + method.getName());
                Shape shape = new Shape(method.getName(), method);
                shapes.put(method.getName(), shape);
            }
        }
    }

    public Optional<Shape> getShape(String shapeName) {
       // SsomarDev.testMsg("Looking for shape: " + shapeName, true);
        for(Shape shape : shapes.values()){
            //SsomarDev.testMsg("Shape available: " + shape.getName(), true);
        }
        Shape shape = shapes.get(shapeName);
        return Optional.ofNullable(shape);
    }

    public static ShapesManager getInstance() {
        if (instance == null) instance = new ShapesManager();
        return instance;
    }
}

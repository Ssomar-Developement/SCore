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
        whitelistedShapes.add("vortex");
        whitelistedShapes.add("cylinder");
        whitelistedShapes.add("sphere");
        whitelistedShapes.add("spikeSphere");
        whitelistedShapes.add("ring");
        whitelistedShapes.add("spread");
        whitelistedShapes.add("heart");
        whitelistedShapes.add("atomic");
        whitelistedShapes.add("helix");
        // PRBLEM ARGUMENTS + INFINITE LOOP whitelistedShapes.add("lightning");
        //whitelistedShapes.add("dna");
        whitelistedShapes.add("dnaReplication");
        //whitelistedShapes.add("drawLine");
        // PROBLEM DOUBLE PARTICLE whitelistedShapes.add("cloud");
        whitelistedShapes.add("tesseract");
        whitelistedShapes.add("mandelbrot");
        whitelistedShapes.add("julia");
        whitelistedShapes.add("star");
        whitelistedShapes.add("eye");
        whitelistedShapes.add("illuminati");
        whitelistedShapes.add("polygon");
        //ROBLEM DOUBLE PARTICLE whitelistedShapes.add("neopaganPentagram");
        // PROBLEM DOUBLE PARTICLE whitelistedShapes.add("atom");
        whitelistedShapes.add("meguminExplosion");

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
        /*for(Shape shape : shapes.values()){
            SsomarDev.testMsg("Shape available: " + shape.getName(), true);
        }*/
        Shape shape = shapes.get(shapeName);
        return Optional.ofNullable(shape);
    }

    public List<String> getShapesNames(){
        List<String> shapesNames = new ArrayList<>();
        for(Shape shape : shapes.values()){
            shapesNames.add(shape.getName());
        }
        return shapesNames;
    }

    public static ShapesManager getInstance() {
        if (instance == null) instance = new ShapesManager();
        return instance;
    }
}

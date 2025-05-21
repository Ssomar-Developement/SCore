package com.ssomar.particles.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShapesExamples {

    private static ShapesExamples instance;

    public Map<String, String> examples;

    public ShapesExamples(){
        examples = new HashMap<>();
        examples.put("heart", "/score particles shape:heart particle:HEART cut:4 cutAngle:2 depth:2 compressHeight:1 rate:100");
        examples.put("blackhole", "/score particles target:731bec03-102e-3179-b676-04de47c40580 particle:SMOKE_NORMAL shape:blackhole points:30 radius:2.5 rate:1 mode:2 time:50");
        examples.put("vortex", "/score particles shape:vortex particle:crit points:5 rate:25 time:100");
        examples.put("diamond", "/score particles shape:diamond particle:glow radiusRate:0.6 rate:0.4 height:3");
        examples.put("ring", "/score particles shape:ring particle:PORTAL rate:100 radius:2 tubeRadius:1");
        examples.put("illuminati", "/score particles shape:illuminati particle:NAUTILUS size:5 extension:20");
        examples.put("meguminExplosion", "/score particles shape:meguminExplosion color:RED size:5");
        examples.put("circularBeam", "/score particles shape:circularBeam color:PURPLE maxRadius:5 rate:500 radiusRate:15 extend:1 time:100");
        examples.put("cone", "/score particles shape:cone color:GREEN height:5 radius:1 rate:0.2 circleRate:10");
        examples.put("magicCircles", "/score particles shape:magicCircles radius:1 rate:3 radiusRate:1 time:20");
        examples.put("eye", "/score particles shape:eye radius:2 radius2:2 extension:1 rate:100");
        examples.put("chaoticDoublePendulum", "/score particles shape:chaoticDoublePendulum radius:2 gravity:-1 length:200 length2:200 mass1:50 mass2:50 dimension3:false speed:2 time:200");
        examples.put("wall", "/score particles shape:wall particle:FLAME height:3 length:5 density:10 pitch:0 yaw:90 verticalOrder:up horizontalOrder:near");
        examples.put("animatedCircle", "/score particles shape:animatedCircle particle:FLAME radius:3 density:100 timeToDisplay:40 drawMode:clockwise plane:xz offset:0 pitch:0 yaw:0");


    }

    public Optional<String> getExample(String shape){
        return Optional.ofNullable(examples.get(shape));
    }

    public static ShapesExamples getInstance() {
        if(instance == null) instance = new ShapesExamples();
        return instance;
    }


}

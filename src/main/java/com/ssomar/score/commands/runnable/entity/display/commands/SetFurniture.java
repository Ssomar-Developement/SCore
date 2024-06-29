package com.ssomar.score.commands.runnable.entity.display.commands;

import com.ssomar.myfurniture.api.MyFurnitureAPI;
import com.ssomar.myfurniture.furniture.Furniture;
import com.ssomar.myfurniture.furniture.placedfurniture.FurniturePlaced;
import com.ssomar.score.commands.runnable.ActionInfo;
import com.ssomar.score.commands.runnable.entity.display.DisplayCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* SETFURNITURE */
public class SetFurniture extends DisplayCommand {

    @Override
    public void run(Player p, Entity entity, List<String> args, ActionInfo aInfo) {

        Optional<FurniturePlaced> furniturePlaced = MyFurnitureAPI.getFurniturePlacedManager().getFurniturePlaced(entity);
        if(furniturePlaced.isPresent()) {
            Optional<Furniture> furniture = MyFurnitureAPI.getFurnitureManager().getFurniture(args.get(0));
            furniturePlaced.get().replaceFurnitureConfig(furniture.get());
        }
    }

    @Override
    public Optional<String> verify(List<String> args, boolean isFinalVerification) {
        return Optional.empty();
    }

    @Override
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("SETFURNITURE");
        return names;
    }

    @Override
    public String getTemplate() {
        return "SETFURNITURE {furnitureId}";
    }

    @Override
    public ChatColor getColor() {
        return null;
    }

    @Override
    public ChatColor getExtraColor() {
        return null;
    }
}

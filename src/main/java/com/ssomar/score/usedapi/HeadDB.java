package com.ssomar.score.usedapi;

import org.bukkit.inventory.ItemStack;
import tsp.headdb.api.HeadAPI;

import java.lang.reflect.Method;

public class HeadDB {

    public static ItemStack getHead(int id){

        Method getByID = null;
        for(Method method : HeadAPI.class.getDeclaredMethods()){
            if(method.getName().equals("getHeadByID")){
                getByID = method;
                break;
            }
        }

        try{
            tsp.headdb.api.Head head = (tsp.headdb.api.Head)getByID.invoke(null, id);
            return head.getItemStack();
        }
        catch (Exception err ){}
        catch (Error err ){}
        try{
            tsp.headdb.implementation.Head head = (tsp.headdb.implementation.Head)getByID.invoke(null, id);
            return head.getMenuItem();
        }catch (Exception err){}
        catch (Error err ){}
        return null;
    }
}
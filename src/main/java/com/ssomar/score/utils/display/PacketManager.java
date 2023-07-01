package com.ssomar.score.utils.display;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.ssomar.score.SCore;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PacketManager {

    public static void newDisplay() {

        PacketAdapter.AdapterParameteters params = PacketAdapter.params().plugin(SCore.plugin)
                .listenerPriority(ListenerPriority.HIGH)
                .types(PacketType.Play.Server.WINDOW_ITEMS);

        SCore.protocolManager.addPacketListener(new PacketAdapter(params) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                /* if (event.getPacketType() == PacketType.Play.Server.SET_SLOT && event.getPlayer().getGameMode() != GameMode.CREATIVE) {

                    System.out.println("SET_SLOT PACKETTTTTTTTTTTTTTTTTTTTTTTTTTTTTT  ");

                    ItemStack item = packet.getItemModifier().read(0);
                    if (item == null || item.getType().isAir() || !item.hasItemMeta()) return;

                    DisplayRequestResult requestResult = Display.display(item, event.getPlayer());
                    if (requestResult.getResult() == DisplayResult.NOT_MODIFIED) {
                        return;
                    }
                    item = requestResult.getItemStack();
                    packet.getItemModifier().write(0, item);
                } else */ if (event.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
                    //System.out.println("WINDOW_ITEMS  PACKETTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");

                    List<ItemStack> itemStacks = packet.getItemListModifier().read(0);
                    for (ItemStack item : itemStacks) {
                        if (item == null) continue;

                        //System.out.println("SET_SLOT PACKETTTTTTTTTTTTTTTTTTTTTTTTTTTTTT  >> "+item.getType()+"   >>> "+PacketType.Play.Server.SET_SLOT.isAsyncForced());

                        DisplayRequestResult requestResult = Display.display(item, event.getPlayer());
                        if (requestResult.getResult() == DisplayResult.NOT_MODIFIED) {
                            continue;
                        }
                    }
                }

                event.setPacket(packet);
            }
        });

        /* try {

            SCore.protocolManager.addPacketListener(
                    new PacketAdapter(SCore.plugin, ListenerPriority.NORMAL,
                            PacketType.Play.Server.SET_SLOT) {
                        @Override
                        public void onPacketReceiving(PacketEvent event) {

                        }

                        @Override
                        public void onPacketSending(PacketEvent event) {

                            PacketContainer packet = event.getPacket();

                            ItemStack item = packet.getItemModifier().read(0);
                            if(item == null || item.getType().isAir() || !item.hasItemMeta()) return;

                            //System.out.println("SET_SLOT PACKETTTTTTTTTTTTTTTTTTTTTTTTTTTTTT  >> "+item.getType()+"   >>> "+PacketType.Play.Server.SET_SLOT.isAsyncForced());

                            DisplayRequestResult requestResult =  Display.display(item, event.getPlayer());
                            if(requestResult.getResult() == DisplayResult.NOT_MODIFIED) {
                                return;
                            }
                            item = requestResult.getItemStack();
                            packet.getItemModifier().write(0,item);
                        }
                    });
        } catch (Exception | Error ignored) {
        }*/

    }
}

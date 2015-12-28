package com.updg.sclobby.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

/**
 * Created by Alex
 * Date: 10.11.13  22:19
 */
public class SystemEvents implements Listener {
    @EventHandler
    public void event(ChunkUnloadEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onRainStart(WeatherChangeEvent event) {
        if (!event.isCancelled()) {
            event.setCancelled(true);
        }
    }
}

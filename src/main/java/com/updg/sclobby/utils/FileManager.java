package com.updg.sclobby.utils;

import com.updg.sclobby.sclobby;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public class FileManager {

    public static void removePlayerAsyncDelayed(final PlayerQuitEvent event) {
        BukkitTask bukkitTask = sclobby.plugin.getServer().getScheduler().runTaskLaterAsynchronously(sclobby.plugin, new Runnable() {
            public void run() {
                new File(Bukkit.getWorld("world").getWorldFolder().getPath() + File.separatorChar + event.getPlayer().getName() + ".dat").delete();

            }
        }, 60L);
    }
}

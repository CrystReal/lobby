package com.updg.sclobby.loginSystem.bungee;

import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;

public class PluginMessageTask extends BukkitRunnable {
    private final sclobby plugin;
    private final ByteArrayOutputStream bytes;
    private final Player player;

    public PluginMessageTask(sclobby plugin, Player player, ByteArrayOutputStream bytes) {
        this.plugin = plugin;
        this.bytes = bytes;
        this.player = player;
    }

    public void run() {
        player.sendPluginMessage(this.plugin, "BungeeCord", this.bytes.toByteArray());
    }
}
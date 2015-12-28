package com.updg.sclobby.events;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.loginSystem.bungee.Bungee;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.BukkitUtils;
import com.updg.sclobby.utils.FileManager;
import com.updg.sclobby.utils.Redis;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Alex
 * Date: 30.10.13  23:44
 */
public class JoinExit implements Listener {

    @EventHandler
    public void join(final PlayerJoinEvent event) {
        Redis.del("online_" + event.getPlayer().getName().toLowerCase());
        event.setJoinMessage(null);

        sclobby.addPlayer(event.getPlayer());
        BukkitUtils.newHideForce(event.getPlayer());
        event.getPlayer().setGameMode(GameMode.ADVENTURE);
        event.getPlayer().teleport(sclobby.lobbySpawn);
        LobbyPlayer p = sclobby.getPlayer(event.getPlayer().getName());
        BukkitUtils.hidePlayers(p);
        p.sendMessage("");
        p.sendMessage("");
        p.sendMessage(ChatColor.GREEN + "==========================================");
        p.sendMessage(ChatColor.GREEN + "С возвращением!");
        p.sendMessage(ChatColor.GREEN + "Секундочку. Идет проверка профиля.");
        p.sendMessage(ChatColor.GREEN + "==========================================");
        p.sendMessage("");
        p.sendMessage("");

        Bukkit.getScheduler().runTaskLaterAsynchronously(sclobby.plugin, new Runnable() {
            public void run() {
                Bungee.isLogged(event.getPlayer(), event.getPlayer().getName());
            }
        }, 1);
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        e.setLeaveMessage(null);
    }

    @EventHandler
    public void join(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        LobbyPlayer p = sclobby.getPlayer(event.getPlayer().getName());
        BukkitUtils.newShow(p);
        sclobby.removePlayer(event.getPlayer());
        FileManager.removePlayerAsyncDelayed(event);
    }
}

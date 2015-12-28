package com.updg.sclobby.events;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Alex
 * Date: 10.11.13  20:40
 */
public class ChatEvent implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void chat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) {
            return;
        }
        e.setCancelled(true);
        Player user = e.getPlayer();
        LobbyPlayer p = sclobby.getPlayer(user.getName());
        if (!p.isLoggedIn()) {
            return;
        }
        if (p.isAdmin()) {
            for (LobbyPlayer item : sclobby.players.values()) {
                if (item.isLoggedIn()) {
                    item.sendMessage(ChatColor.GRAY + "[" + ChatColor.GOLD + "Администратор" + ChatColor.GRAY + "] " + ChatColor.GOLD + p.getName() + ChatColor.GRAY + ": " + ChatColor.RESET + e.getMessage());
                }
            }
        } else if (p.isModer()) {
            for (LobbyPlayer item : sclobby.players.values()) {
                if (item.isLoggedIn()) {
                    item.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + "Модератор" + ChatColor.GRAY + "] " + ChatColor.GOLD + p.getName() + ChatColor.GRAY + ": " + ChatColor.RESET + e.getMessage());
                }
            }
        } else {
            for (LobbyPlayer item : sclobby.players.values()) {
                if (item.isLoggedIn()) {
                    item.sendMessage(ChatColor.GRAY + p.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}

package com.updg.sclobby.loginSystem.bungee;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Created by Alex
 * Date: 18.06.13  19:56
 */
public class PluginMessage implements PluginMessageListener {

    private sclobby plugin;

    public PluginMessage() {
        this.plugin = sclobby.plugin;
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("StreamBungee"))
            return;
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(
                message));

        String channel1 = null;
        try {
            channel1 = in.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (channel1 != null && channel1.equals("isLoggedBack")) {
            String msg = null;
            Boolean online = false;
            int id = 0, rang = 0, vip = 0;
            try {
                msg = in.readUTF();
                online = in.readBoolean();
                if (online) {
                    id = in.readInt();
                    rang = in.readInt();
                    vip = in.readInt();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (msg != null) {
                LobbyPlayer p = sclobby.players.get(msg);
                p.setBungeeCheked(true);
                if (online) {
                    p.goOnline();
                    p.setId(id);
                    p.setRang(rang);
                    p.setVip(vip);
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "==========================================");
                    p.sendMessage(ChatColor.GREEN + "С возвращением!");
                    p.sendMessage(ChatColor.GREEN + "Все хорошо. Приятной игры.");
                    p.sendMessage(ChatColor.GREEN + "==========================================");
                    p.sendMessage("");
                    p.sendMessage("");
                } else {
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "==========================================");
                    p.sendMessage(ChatColor.GREEN + "С возвращением!");
                    p.sendMessage(ChatColor.RED + "Введи свой пароль с помощью команды /login");
                    p.sendMessage(ChatColor.GREEN + "==========================================");
                    p.sendMessage("");
                    p.sendMessage("");
                    p.setLoggedIn(false);
                }

            }
        }
    }
}
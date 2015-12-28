package com.updg.sclobby.loginSystem.bungee;

import com.updg.sclobby.sclobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by Alex
 * Date: 18.06.13  19:49
 */
public class Bungee {
    public static void isLogged(final Player p, final String player) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(sclobby.plugin, new Runnable() {
            public void run() {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(b);
                try {
                    out.writeUTF("isLogged");
                    out.writeUTF(player);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new PluginMessageTask(sclobby.plugin, Bukkit.getOnlinePlayers()[0], b).runTaskAsynchronously(sclobby.plugin);
            }
        }, 1);
    }

    public static void goOnline(int id, String player, int rang, int vip) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("setLoggedIn");
            out.writeInt(id);
            out.writeUTF(player);
            out.writeInt(rang);
            out.writeInt(vip);
            out.writeInt(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new PluginMessageTask(sclobby.plugin, Bukkit.getOnlinePlayers()[0], b).runTaskAsynchronously(sclobby.plugin);
    }


    public static void goOffline(String player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("setLoggedOut");
            out.writeUTF(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new PluginMessageTask(sclobby.plugin, Bukkit.getOnlinePlayers()[0], b).runTaskAsynchronously(sclobby.plugin);
    }
}

package com.updg.sclobby.threads;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import org.bukkit.ChatColor;

/**
 * Created by Alex
 * Date: 10.11.13  22:28
 */
public class WatchThread extends Thread implements Runnable {

    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (LobbyPlayer p : sclobby.players.values()) {
                if (p.isLoggedIn()) {
                    if (p.isHidePlayers()) {
                        p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "У тебя выключен показ других игроков в лобби. Используй часы у себя в инвентаре чтобы показать игроков.");
                    } else {
                        p.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "У тебя включен показ других игроков в лобби. Используй часы у себя в инвентаре чтобы скрыть игроков.");
                    }
                }
            }
        }
    }
}

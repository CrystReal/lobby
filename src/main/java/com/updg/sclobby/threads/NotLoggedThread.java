package com.updg.sclobby.threads;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import org.bukkit.ChatColor;

/**
 * Created by Alex
 * Date: 10.11.13  22:28
 */
public class NotLoggedThread extends Thread implements Runnable {

    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (LobbyPlayer p : sclobby.players.values()) {
                if (!p.isBungeeCheked()) {
                    p.sendMessage("");
                    p.sendMessage("");
                    p.sendMessage(ChatColor.GREEN + "==========================================");
                    p.sendMessage(ChatColor.GREEN + "С возвращением!");
                    p.sendMessage(ChatColor.GREEN + "Секундочку. Идет проверка профиля.");
                    p.sendMessage(ChatColor.GREEN + "==========================================");
                    p.sendMessage("");
                    p.sendMessage("");
                } else if (!p.isLoggedIn()) {
                    if (p.incorrectPass) {
                        p.sendMessage("");
                        p.sendMessage("");
                        p.sendMessage(ChatColor.GREEN + "==========================================");
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Ошибка.");
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Пароль не верный.");
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
                    }
                }
            }
        }
    }
}

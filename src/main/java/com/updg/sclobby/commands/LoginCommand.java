package com.updg.sclobby.commands;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Alex
 * Date: 10.11.13  0:56
 */
public class LoginCommand implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        final LobbyPlayer p = sclobby.getPlayer(commandSender.getName());
        if (p.isLoggedIn()) {
            p.sendMessage("Ты уже вошел.");
            return true;
        }
        if (args.length < 1) {
            return true;
        }
        if (p.checkPassword(args[0])) {
            p.sendMessage("");
            p.sendMessage("");
            p.sendMessage(ChatColor.GREEN + "==========================================");
            p.sendMessage(ChatColor.GREEN + "С возвращением!");
            p.sendMessage(ChatColor.GOLD + "Ты успешно авторизирован");
            p.sendMessage(ChatColor.GREEN + "==========================================");
            p.sendMessage("");
            p.sendMessage("");
            p.goOnline();
            p.logginIn();
        } else {
            p.incorrectPass = true;
            p.sendMessage("");
            p.sendMessage("");
            p.sendMessage(ChatColor.GREEN + "==========================================");
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Ошибка.");
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Пароль не верный.");
            p.sendMessage(ChatColor.GREEN + "==========================================");
            p.sendMessage("");
            p.sendMessage("");

            return true;
        }
        return true;
    }
}

package com.updg.sclobby.commands;

import com.updg.sclobby.Models.GameSign;
import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.serverQ.senderToAllServers;
import com.updg.sclobby.utils.L;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Check implements CommandExecutor {
    private final sclobby plugin;

    public Check(sclobby plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        LobbyPlayer p;
        if (commandSender instanceof Player) {
            p = sclobby.getPlayer(commandSender.getName());
        } else {
            return false;
        }
        if (p.isLoggedIn() && p.isAdmin()) {
            for (GameSign sign : sclobby.signs.values()) {
                sign.loadDefault();
            }
            senderToAllServers.send("check");
            p.sendMessage("Запросик отправлен. С уважением, Почта России.");
        }
        return true;
    }
}

package com.updg.sclobby.commands;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.serverQ.senderToAllServers;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadLobby implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        LobbyPlayer p;
        if (commandSender instanceof Player) {
            p = sclobby.getPlayer(commandSender.getName());
        } else {
            return false;
        }
        if (p.isLoggedIn() && p.isAdmin()) {
            sclobby.plugin.reload();
            p.sendMessage("Reloaded");
        }
        return true;
    }
}

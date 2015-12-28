package com.updg.sclobby.commands;

import com.updg.sclobby.Models.Game;
import com.updg.sclobby.sclobby;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Leave implements CommandExecutor {
    private final sclobby plugin;

    public Leave(sclobby plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player p;
        if (commandSender instanceof Player) {
            p = (Player) commandSender;
        } else {
            return false;
        }
        if (sclobby.plugin.removeFromAllQ(sclobby.getPlayer(p.getName())))
            p.sendMessage("Ты покинул очереди на все игры.");
        else
            p.sendMessage("Ты не состоишь в очередях на игры.");
        return true;
    }
}

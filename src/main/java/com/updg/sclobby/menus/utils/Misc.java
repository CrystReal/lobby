package com.updg.sclobby.menus.utils;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import me.scarlet.bukkit.invmenu.Menu;
import me.scarlet.bukkit.invmenu.MenuInstanceManager;
import org.bukkit.Bukkit;

/**
 * Created by Alex
 * Date: 24.03.2014  0:02
 */
public class Misc {
    public static void runOpener(final Menu menu, final LobbyPlayer p) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(sclobby.plugin, new Runnable() {
            public void run() {
                MenuInstanceManager.newInstance(menu, p.getBukkitModel());
            }
        }, 1L);
    }
}

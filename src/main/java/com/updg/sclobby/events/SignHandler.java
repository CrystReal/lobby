package com.updg.sclobby.events;

import com.updg.sclobby.Models.Game;
import com.updg.sclobby.Models.GameSign;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.serverQ.senderToAllServers;
import com.updg.sclobby.utils.L;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * User: Alex
 * Date: 03.06.13
 * Time: 16:49
 */
public class SignHandler implements Listener {
    public SignHandler(JavaPlugin plugin) {

    }

    @EventHandler
    public void onPlayer(SignChangeEvent e) {
        if (!sclobby.players.get(e.getPlayer().getName()).isAdmin()) {
            e.setCancelled(true);
            return;
        }
        if (e.getLine(1).contains("SIGN|")) {
            int id = Integer.parseInt(e.getLine(1).replace("SIGN|", ""));
            sclobby.signs.put(e.getBlock().getLocation(), new GameSign(id, e.getBlock().getLocation()));
            sclobby.plugin.updateConf();
            e.setLine(1, "");
            senderToAllServers.send("check");
        }
    }

    @EventHandler
    public void onPlayer(BlockBreakEvent block) {
        if (!sclobby.players.get(block.getPlayer().getName()).isAdmin()) {
            block.setCancelled(true);
            return;
        }
        GameSign sign = sclobby.signs.get(block.getBlock().getLocation());
        if (sign != null) {
            sclobby.signs.remove(block.getBlock().getLocation());
            sclobby.plugin.updateConf();
        }
    }

    public static void updateSign(final int id, final String status, final String thirdline, final String line4, final boolean show) {
        for (final GameSign item : sclobby.signs.values()) {
            if (item.getGame() == id && item.getLocation().getBlock() != null) {
                sclobby.plugin.getServer().getScheduler().scheduleSyncDelayedTask(sclobby.plugin, new Runnable() {
                    public void run() {
                        item.getLocation().getChunk().load();
                        Sign b = (Sign) item.getLocation().getBlock().getState();
                        if (b instanceof Sign) {
                            if (show) {
                                b.setLine(1, status);
                                b.setLine(2, thirdline);
                                b.setLine(3, line4);
                            }
                            b.update();
                        }

                    }
                }, 1);
                return;
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        boolean go = false;
        if (!sclobby.players.get(e.getPlayer().getName()).isLoggedIn()) {
            e.setCancelled(true);
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST || e.getClickedBlock().getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) e.getClickedBlock().getState();
                if (sclobby.signs.containsKey(sign.getLocation())) {
                    GameSign s = sclobby.signs.get(sign.getLocation());
                    for (Game g : sclobby.gameQs) {
                        if (g.id == s.getGame()) {
                            go = true;
                            g.addPlayer(sclobby.getPlayer(e.getPlayer().getName()));
                        }
                    }
                    if (!go) {
                        e.getPlayer().sendMessage("Сервер временно недоступен.");
                    }
                }
            }
        }
    }
}

package com.updg.sclobby.events;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.menus.MenuType;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alex
 * Date: 10.11.13  1:10
 */
public class PlayerEvents implements Listener {

    @EventHandler
    public void watch(PlayerInteractEvent e) {
        if (!sclobby.players.get(e.getPlayer().getName()).isLoggedIn()) {
            e.setCancelled(true);
            return;
        }
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_AIR || ((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) && (e.getClickedBlock().getType() != Material.SIGN_POST && e.getClickedBlock().getType() != Material.WALL_SIGN)))) {
            if (e.getPlayer().getItemInHand().getType() == Material.WATCH)
                sclobby.players.get(e.getPlayer().getName()).togglePlayerVisibility();
            if (e.getPlayer().getItemInHand().getType() == Material.EMERALD) {
                if (sclobby.players.get(e.getPlayer().getName()).isAdmin())
                    sclobby.players.get(e.getPlayer().getName()).showMenu(MenuType.SETTINGS);
                else
                    e.getPlayer().sendMessage(ChatColor.RED + "В разработке. Запустим совсем скоро!");
            }

        }
        if (!sclobby.players.get(e.getPlayer().getName()).isAdmin() && e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(EntityType.ITEM_FRAME)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void InventoryClickEvent(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void move(PlayerMoveEvent e) {
        if (!this.hasChangedBlockCoordinates(e.getFrom(), e.getTo())) {
            return;
        }
        if (!sclobby.players.get(e.getPlayer().getName()).isLoggedIn()) {
            e.setCancelled(true);
            e.getPlayer().teleport(sclobby.lobbySpawn);
            return;
        }
        if (e.getTo().getY() <= sclobby.lobbySpawn.getY() - 5) {
            e.getPlayer().teleport(sclobby.lobbySpawn);
        }
        if (!sclobby.players.get(e.getPlayer().getName()).isAdmin()) {
            for (Location l : sclobby.signs.keySet()) {
                //if (2 <= Math.sqrt((l.getBlockX() - e.getTo().getBlockX()) * (l.getBlockX() - e.getTo().getBlockX()) + (l.getBlockZ() - e.getTo().getBlockZ()) * (l.getBlockZ() - e.getTo().getBlockZ()))) {
                if (l.getBlockX() == e.getTo().getBlockX() && l.getBlockZ() == e.getTo().getBlockZ())
                    e.setCancelled(true);
                //}
            }
        }
    }

    private boolean hasChangedBlockCoordinates(final Location fromLoc, final Location toLoc) {
        return !(fromLoc.getWorld().equals(toLoc.getWorld())
                && fromLoc.getBlockX() == toLoc.getBlockX()
                //     && fromLoc.getBlockY() == toLoc.getBlockY()
                && fromLoc.getBlockZ() == toLoc.getBlockZ());
    }

    @EventHandler
    public void event(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void event(BlockPlaceEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void event(PlayerDropItemEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
            if (sclobby.players.get(e.getPlayer().getName()).isLoggedIn()) {
                sclobby.players.get(e.getPlayer().getName()).clearInventory();
                sclobby.players.get(e.getPlayer().getName()).takeItems();
            }
        }
    }

    @EventHandler
    public void event(PlayerPickupItemEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void event(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void dmg(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.VOID)
            ((Player) e.getEntity()).teleport(sclobby.lobbySpawn);
        e.setCancelled(true);
    }

    @EventHandler
    public void inv(InventoryOpenEvent e) {
        if (!sclobby.players.get(e.getPlayer().getName()).isLoggedIn() || !sclobby.players.get(e.getPlayer().getName()).isAdmin()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void respawn(PlayerRespawnEvent e) {
        e.setRespawnLocation(sclobby.lobbySpawn);
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player user = event.getPlayer();
        LobbyPlayer p = sclobby.getPlayer(user.getName());
        String[] split = event.getMessage().split(" ");
        if (split[0].equalsIgnoreCase("/login") || split[0].equalsIgnoreCase("/l")) {
            return;
        } else {
            if (!p.isLoggedIn()) {
                event.setCancelled(true);
                return;
            }
        }
        if ((split[0].equalsIgnoreCase("/help") || split[0].equalsIgnoreCase("/?")) && !p.isAdminOrModer()) {
            event.setCancelled(true);
            user.sendMessage("Вы можете просмотреть команды на crystreal.net");
        }
        if ((split[0].equalsIgnoreCase("/op") || split[0].equalsIgnoreCase("/deop")) && !p.isAdminOrModer()) {
            event.setCancelled(true);
            user.sendMessage("Команда не найдена. Вы можете просмотреть команды на crystreal.net");
        }
        if ((split[0].equalsIgnoreCase("/plugins") || split[0].equalsIgnoreCase("/pl")) && !p.isAdminOrModer()) {
            user.sendMessage("Команда не найдена. Вы можете просмотреть команды на crystreal.net");
            event.setCancelled(true);
        }
        if ((split[0].equalsIgnoreCase("/ver") || split[0].equalsIgnoreCase("/version")) && !p.isAdminOrModer()) {
            user.sendMessage("Давным давно.");
            user.sendMessage("В далекой галактике.");
            user.sendMessage("Бонд создал сервер.");
            user.sendMessage("И читеры никак не могли понять, что за сервер он использовал.");
            user.sendMessage("Было известно одно - само ядро Spigot.");
            event.setCancelled(true);
        }
        if (split[0].equalsIgnoreCase("/reload") && !p.isAdminOrModer()) {
            user.sendMessage("Команда не найдена. Вы можете просмотреть команды на crystreal.net");
            event.setCancelled(true);
        }
        if (split[0].equalsIgnoreCase("/stop") && !p.isAdminOrModer()) {
            user.sendMessage("Команда не найдена. Вы можете просмотреть команды на crystreal.net");
            event.setCancelled(true);
        }
        if (split[0].equalsIgnoreCase("/give") && !p.isAdminOrModer()) {
            user.sendMessage("Команда не найдена. Вы можете просмотреть команды на crystreal.net");
            event.setCancelled(true);
        }
    }
}

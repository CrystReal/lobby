package com.updg.sclobby.utils;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.sclobby;
import org.bukkit.entity.Player;

/**
 * Created by Alex
 * Date: 10.11.13  20:53
 */
public class BukkitUtils {
    public static void hidePlayers(LobbyPlayer p) {
        for (LobbyPlayer item : sclobby.players.values()) {
            if (item.isLoggedIn()) {
                if (!item.isAdminOrModer())
                    p.getBukkitModel().hidePlayer(item.getBukkitModel());
            } else
                p.getBukkitModel().hidePlayer(item.getBukkitModel());
        }
    }

    public static void newHide(Player p) {
        for (LobbyPlayer item : sclobby.players.values()) {
            if (item.isHidePlayers()) {
                if (item.isLoggedIn()) {
                    if (!item.isAdminOrModer())
                        item.getBukkitModel().hidePlayer(p);
                } else
                    item.getBukkitModel().hidePlayer(p);
            }
        }
    }

    public static void newHideForce(Player p) {
        for (LobbyPlayer item : sclobby.players.values()) {
            item.getBukkitModel().hidePlayer(p);
        }
    }

    public static void showPlayers(LobbyPlayer p) {
        for (LobbyPlayer item : sclobby.players.values()) {
            if (item.isLoggedIn())
                p.getBukkitModel().showPlayer(item.getBukkitModel());
        }
    }

    public static void newShow(LobbyPlayer p) {
        for (LobbyPlayer item : sclobby.players.values()) {
            if (!item.isHidePlayers() || (p.isLoggedIn() && p.isAdminOrModer())) {
                item.getBukkitModel().showPlayer(p.getBukkitModel());
            }
        }
    }
}

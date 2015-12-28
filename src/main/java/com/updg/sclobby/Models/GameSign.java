package com.updg.sclobby.Models;

import com.updg.sclobby.Models.enums.GameType;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Sign;

public class GameSign {
    private int game;
    private Location loc;

    public GameSign(String input) {
        String[] line = input.split("\\|");
        int i = 0, x = 0, y = 0, z = 0;
        World w = null;
        for (String a : line) {
            i++;
            if (i == 1) {
                setGame(Integer.parseInt(a));
            }
            if (i == 2) {
                w = Bukkit.getWorld(a);
                if (w == null) {
                    sclobby.$("Error. World '" + a + "' not found!");
                }
            }
            if (i == 3) {
                x = Integer.parseInt(a);
            }
            if (i == 4) {
                y = Integer.parseInt(a);
            }
            if (i == 5) {
                z = Integer.parseInt(a);
            }
        }
        this.loc = new Location(w, x, y, z);
        this.loadDefault();
    }

    public GameSign(int game, Location loc) {
        setGame(game);
        this.loc = loc;
        this.loadDefault();
    }

    public void loadDefault() {
        for (Game item : sclobby.gameQs) {
            if (item.id == this.game) {
                if (item.type == GameType.NOW) {
                    Sign b = (Sign) this.loc.getBlock().getState();
                    if (b instanceof Sign) {
                        b.setLine(1, "");
                        b.setLine(2, ChatColor.RED + "" + ChatColor.BOLD + "ОФФЛАЙН");
                        b.setLine(3, "");
                        b.update();
                    }
                }
                return;
            }
        }
    }


    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public boolean isThisSign(Location loc) {
        if (this.loc.equals(loc)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return game + "|" + loc.getWorld().getName() + "|" + loc.getBlockX() + "|" + loc.getBlockY() + "|" + loc.getBlockZ();
    }

    public Location getLocation() {
        return this.loc;
    }
}

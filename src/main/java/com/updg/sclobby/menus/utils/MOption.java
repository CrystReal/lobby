package com.updg.sclobby.menus.utils;

import me.scarlet.bukkit.invmenu.Option;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Alex
 * Date: 23.03.2014  21:14
 */
public class MOption extends Option {
    public MOption(ItemStack symbol, String name, String description) {
        super(symbol, ChatColor.RESET + name, ChatColor.RESET + description);
    }
}

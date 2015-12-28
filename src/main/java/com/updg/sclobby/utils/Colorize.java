package com.updg.sclobby.utils;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by Alex
 * Date: 24.10.13  19:10
 */
public class Colorize {
    public static ItemStack armor(ItemStack item, Color color) {
        LeatherArmorMeta lam1 = (LeatherArmorMeta) item.getItemMeta();
        lam1.setColor(color);
        item.setItemMeta(lam1);
        return item;
    }
}

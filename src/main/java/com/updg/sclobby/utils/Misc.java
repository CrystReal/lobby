package com.updg.sclobby.utils;

import net.minecraft.server.v1_7_R2.NBTTagCompound;
import net.minecraft.server.v1_7_R2.NBTTagList;
import org.bukkit.craftbukkit.v1_7_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Alex
 * Date: 23.03.2014  21:26
 */
public class Misc {
    public static ItemStack add(ItemStack item) {
        net.minecraft.server.v1_7_R2.ItemStack handle = CraftItemStack.asNMSCopy(item);

        if (handle == null) {
            return item;
        }

        if (handle.tag == null) {
            handle.tag = new NBTTagCompound();
        }

        NBTTagList tag = handle.getEnchantments();
        if (tag == null) {
            tag = new NBTTagList();
            handle.tag.set("ench", tag);
        }

        return CraftItemStack.asCraftMirror(handle);
    }
}

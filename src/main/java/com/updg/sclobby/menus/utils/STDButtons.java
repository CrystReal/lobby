package com.updg.sclobby.menus.utils;

import com.updg.sclobby.menus.MenuType;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;
import me.scarlet.bukkit.invmenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Alex
 * Date: 23.03.2014  23:05
 */
public class STDButtons {
    public static void registerBackButton(Menu m, final MenuType dest, int place) {
        FunctionMenuButton fb;
        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.WOOD_DOOR), ChatColor.GREEN + "Назад", "Возвращение в меню выше"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("Back");
                sclobby.getPlayer(who.getName()).showMenu(dest);
                inst.close();
            }
        });
        m.setButton(place, fb);
    }
}

package com.updg.sclobby.menus.qc;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.menus.MenuType;
import com.updg.sclobby.menus.utils.MOption;
import com.updg.sclobby.menus.utils.STDButtons;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.Colorize;
import com.updg.sclobby.utils.DSUtils;
import me.scarlet.bukkit.invmenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Alex
 * Date: 19.04.2014  13:44
 */
public class ConfigArmor {
    public ConfigArmor(LobbyPlayer p) {
        Menu menu = new Menu("Настройки QuakeCraft :: Настройки шапки", 3);

        FunctionMenuButton fb;

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.LEATHER_CHESTPLATE), "Набор солдата", "Косметическая броня"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setArmor(who, 1);
            }
        });
        menu.setButton(11, fb);

        fb = new FunctionMenuButton(new MOption(Colorize.armor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLUE), "Елитная броня", "Цветная косметическая броня"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setArmor(who, 1);
            }
        });
        menu.setButton(12, fb);

        fb = new FunctionMenuButton(new MOption(Colorize.armor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW), "Крутая броня", "Цветная косметическая броня"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setArmor(who, 1);
            }
        });
        menu.setButton(14, fb);

        fb = new FunctionMenuButton(new MOption(Colorize.armor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK), "Коммандирская броня", "Цветная косметическая броня"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setArmor(who, 1);
            }
        });
        menu.setButton(15, fb);

        STDButtons.registerBackButton(menu, MenuType.QC_SETTINGS, 26);

    }

    public void setArmor(Player p, int id) {
        if (sclobby.getPlayer(p.getDisplayName()).hasProducts(id)) {
            String[] tmp = DSUtils.QC_getParams(p);
            tmp[1] = id + "";
            DSUtils.QC_setParams(p, tmp);
        } else {
            p.sendMessage(ChatColor.DARK_RED + "Для выбора данной брони сначала купите ее в магазине.");
        }
    }
}

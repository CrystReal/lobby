package com.updg.sclobby.menus.qc;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.menus.MenuType;
import com.updg.sclobby.menus.utils.MOption;
import com.updg.sclobby.menus.utils.STDButtons;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.DSUtils;
import com.updg.sclobby.utils.L;
import me.scarlet.bukkit.invmenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Alex
 * Date: 19.04.2014  13:44
 */
public class ConfigHat {
    public ConfigHat(LobbyPlayer p) {
        Menu menu = new Menu("Настройки QuakeCraft :: Настройки шапки", 3);

        FunctionMenuButton fb;

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.GLASS), "Скафандр", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(9, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "Хелоуин-шапка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(10, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "Кактус-шапка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(11, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "Редстоун шупка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(12, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "Алмазная шапка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(13, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "Арбузная шапка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(14, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "Раздачная шапка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(15, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "TNT шапка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(16, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.PUMPKIN), "Крутая шапка", ""));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                setHat(who, 1);
            }
        });
        menu.setButton(17, fb);

        STDButtons.registerBackButton(menu, MenuType.QC_SETTINGS, 26);

    }

    public void setHat(Player p, int id) {
        if (sclobby.getPlayer(p.getDisplayName()).hasProducts(id)) {
            String[] tmp = DSUtils.QC_getParams(p);
            tmp[0] = id + "";
            DSUtils.QC_setParams(p, tmp);
        } else {
            p.sendMessage(ChatColor.DARK_RED + "Для выбора данной шапки сначала купите ее в магазине.");
        }
    }
}

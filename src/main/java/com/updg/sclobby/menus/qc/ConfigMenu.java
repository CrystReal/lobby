package com.updg.sclobby.menus.qc;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.menus.MenuType;
import com.updg.sclobby.menus.utils.MOption;
import com.updg.sclobby.menus.utils.Misc;
import com.updg.sclobby.menus.utils.STDButtons;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;
import me.scarlet.bukkit.invmenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Alex
 * Date: 23.03.2014  22:32
 */
public class ConfigMenu {
    private HashMap bases = new HashMap();

    public ConfigMenu(LobbyPlayer p) {
        Menu menu = new Menu("Настройки QuakeCraft", 3);

        FunctionMenuButton fb;

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.STONE_HOE), "Название базы",
                ChatColor.WHITE + "Настройка 1: \\n" +
                        ChatColor.WHITE + "Настройка 2: \\n" +
                        ChatColor.WHITE + "Настройка 3:"));
        menu.setButton(4, fb);


        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.LEATHER_HELMET), "Настройки шапки", ChatColor.YELLOW + "Выбор шапки"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(12, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.LEATHER_CHESTPLATE), "Настройки брони", ChatColor.YELLOW + "Выбор брони"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(14, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.WORKBENCH), "Настройки базы", ChatColor.YELLOW + "Выбор базы для оружия"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(19, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.WORKBENCH), "Настройки перезярядки", ChatColor.YELLOW + "Увеличение или уменьшение скорости перезярядки"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(20, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.WORKBENCH), "Настройки дальности", ChatColor.YELLOW + "Увеличение или уменьшение дальности "));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(21, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.WORKBENCH), "Настройки разброса", ChatColor.YELLOW + "Увеличение или уменьшение разброса при взрыве снаряда"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(23, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.WORKBENCH), "Настройки формы", ChatColor.YELLOW + "Выбор формы эффекта взрыва"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(24, fb);

        fb = new FunctionMenuButton(new MOption(new ItemStack(Material.WORKBENCH), "Настройки цвета", ChatColor.YELLOW + "Выбор цвета эффекта взрыва"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                L.$("2");
            }
        });
        menu.setButton(25, fb);

        STDButtons.registerBackButton(menu, MenuType.QC_MAIN, 26);

        Misc.runOpener(menu, p);
    }
}

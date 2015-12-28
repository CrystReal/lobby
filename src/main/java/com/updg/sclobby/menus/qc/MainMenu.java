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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Alex
 * Date: 23.03.2014  22:32
 */
public class MainMenu {
    public MainMenu(LobbyPlayer p) {
        Menu menu = new Menu("QuakeCraft", 4);

        FunctionMenuButton fb;
        fb = new FunctionMenuButton(new MOption(new ItemStack(org.bukkit.Material.DIAMOND), "Магазин", ChatColor.YELLOW + "Магазин апгрейдов QuakeCraft"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_SHOP);
                inst.close();
            }
        });
        menu.setButton(12, fb);
        fb = new FunctionMenuButton(new MOption(new ItemStack(org.bukkit.Material.WOOD_HOE), "Настройки оружия", ChatColor.YELLOW + "Настройки оружия на основе купленных апгрейдов"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_SETTINGS);
                inst.close();
            }
        });
        menu.setButton(14, fb);

        STDButtons.registerBackButton(menu, MenuType.SETTINGS, 35);

        Misc.runOpener(menu, p);
    }
}

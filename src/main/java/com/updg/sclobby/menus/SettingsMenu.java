package com.updg.sclobby.menus;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.menus.utils.MOption;
import com.updg.sclobby.menus.utils.Misc;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.NBTUtils;
import me.scarlet.bukkit.invmenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
/**
 * Created by Alex
 * Date: 23.03.2014  19:46
 */
public class SettingsMenu {

    public SettingsMenu(LobbyPlayer p) {
        Menu menu = new Menu("Настройки", 6);

        FunctionMenuButton fb;
        this.updateVisibleToggle(menu, p);

        //QuakeCraft
        ItemStack is = new ItemStack(Material.GOLD_HOE, 1);
        is.addEnchantment(Enchantment.DURABILITY, 1);
        fb = new FunctionMenuButton(new MOption(NBTUtils.removeAttributes(is), "QuakeCraft", ChatColor.YELLOW + "Настройки и магазин QuakeCraft"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_MAIN);
                inst.close();
            }
        });
        menu.setButton(20, fb);

        //TNTRun
        is = new ItemStack(Material.TNT, 1);
        fb = new FunctionMenuButton(new MOption(NBTUtils.removeAttributes(is), "TNT Run", ChatColor.YELLOW + "Магазин TNT Run"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_MAIN);
                inst.close();
            }
        });
        menu.setButton(22, fb);

        //Bow Spleef
        is = new ItemStack(Material.BOW, 1);
        fb = new FunctionMenuButton(new MOption(NBTUtils.removeAttributes(is), "Bow Spleef", ChatColor.YELLOW + "Магазин Bow Spleef"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_MAIN);
                inst.close();
            }
        });
        menu.setButton(24, fb);

        //TNT Tag
        is = new ItemStack(Material.BLAZE_POWDER, 1);
        fb = new FunctionMenuButton(new MOption(NBTUtils.removeAttributes(is), "TNT Tag", ChatColor.YELLOW + "Магазин TNT Tag"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_MAIN);
                inst.close();
            }
        });
        menu.setButton(38, fb);

        //Survival Games
        is = new ItemStack(Material.CHEST, 1);
        fb = new FunctionMenuButton(new MOption(NBTUtils.removeAttributes(is), "Survival Games", ChatColor.YELLOW + "Магазин Survival Games"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_MAIN);
                inst.close();
            }
        });
        menu.setButton(40, fb);

        //Paintball
        is = new ItemStack(Material.SNOW_BALL, 1);
        fb = new FunctionMenuButton(new MOption(NBTUtils.removeAttributes(is), "Paintball", ChatColor.YELLOW + "Магазин Paintball"));
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                sclobby.getPlayer(who.getName()).showMenu(MenuType.QC_MAIN);
                inst.close();
            }
        });
        menu.setButton(42, fb);

        Misc.runOpener(menu, p);
    }

    public void updateVisibleToggle(Menu menu, LobbyPlayer p) {
        FunctionMenuButton fb;
        // Visible toggle
        if (p.isHidePlayers()) {
            fb = new FunctionMenuButton(new MOption(new ItemStack(Material.INK_SACK, 1, (short) 8), "Преключатель видимости", ChatColor.YELLOW + "Показать игроков"));
        } else {
            fb = new FunctionMenuButton(new MOption(new ItemStack(Material.INK_SACK, 1, (short) 10), "Преключатель видимости", ChatColor.YELLOW + "Скрыть игроков"));
        }
        fb.setButtonTask(new ButtonTask() {
            public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                LobbyPlayer p = sclobby.players.get(who.getName());
                p.togglePlayerVisibility();
                updateVisibleToggle(inst.getMenu(), p);
            }
        });
        menu.setButton(4, fb);
    }
}

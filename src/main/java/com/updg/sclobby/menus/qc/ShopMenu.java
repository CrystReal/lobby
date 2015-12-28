package com.updg.sclobby.menus.qc;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.menus.MenuType;
import com.updg.sclobby.menus.ShopMenuAC;
import com.updg.sclobby.menus.utils.Misc;
import com.updg.sclobby.menus.utils.STDButtons;
import com.updg.sclobby.utils.Colorize;
import me.scarlet.bukkit.invmenu.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by Alex
 * Date: 24.03.2014  0:09
 */
public class ShopMenu extends ShopMenuAC {
    public ShopMenu(LobbyPlayer p) {
        p.getCrystals();
        Menu menu = new Menu("QuakeCraft - Магазин", 6);

        //ШАПКИ
        addItem(menu, 0, new ItemStack(Material.GLASS), "Скафандр", "Собери все головы!", 1, p);
        addItem(menu, 1, new ItemStack(Material.PUMPKIN), "Хелоуин-шапка", "Собери все головы!", 2, p);
        addItem(menu, 2, new ItemStack(Material.CACTUS), "Кактус-шапка", "Собери все головы!", 1, p);
        addItem(menu, 3, new ItemStack(Material.REDSTONE_BLOCK), "Редстоун шупка", "Собери все головы!", 1, p);
        addItem(menu, 4, new ItemStack(Material.DIAMOND_BLOCK), "Алмазная шапка", "Собери все головы!", 1, p);
        addItem(menu, 5, new ItemStack(Material.MELON_BLOCK), "Арбузная шапка", "Собери все головы!", 1, p);
        addItem(menu, 6, new ItemStack(Material.DISPENSER), "Раздачная шапка", "Собери все головы!", 1, p);
        addItem(menu, 7, new ItemStack(Material.TNT), "TNT шапка", "Собери все головы!", 1, p);
        addItem(menu, 8, new ItemStack(Material.GOLD_HELMET), "Крутая шапка", "Собери все головы!", 1, p);

        //БРОНЯ
        addItem(menu, 9, new ItemStack(Material.LEATHER_CHESTPLATE), "Набор солдата", "Косметическая броня", 1, p);
        addItem(menu, 10, Colorize.armor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLUE), "Елитная броня", "Цветная косметическая броня", 1, p);
        addItem(menu, 11, Colorize.armor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.YELLOW), "Крутая броня", "Цветная косметическая броня", 1, p);
        addItem(menu, 12, Colorize.armor(new ItemStack(Material.LEATHER_CHESTPLATE), Color.BLACK), "Коммандирская броня", "Цветная косметическая броня", 1, p);

        // РАЗБРОС
        addItem(menu, 15, new ItemStack(Material.BLAZE_POWDER, 2), "2 блока", "Улучши разброс", 1, p);
        addItem(menu, 16, new ItemStack(Material.BLAZE_POWDER, 3), "3 блока", "Улучши разброс", 1, p);
        addItem(menu, 17, new ItemStack(Material.BLAZE_POWDER, 4), "4 блока", "Улучши разброс", 1, p);

        //ФОРМА
        addItem(menu, 18, new ItemStack(Material.FIREBALL), "Большой шар", "Оформи стиль взрыва жертвы!", 1, p);
        addItem(menu, 19, new ItemStack(Material.SKULL_ITEM, 1, (short) 4), "Крипер", "Оформи стиль взрыва жертвы!", 1, p);
        addItem(menu, 20, new ItemStack(Material.FEATHER), "Несимметричный взрыв", "Оформи стиль взрыва жертвы!", 1, p);
        addItem(menu, 21, new ItemStack(Material.GOLD_NUGGET), "Звезда", "Оформи стиль взрыва жертвы!", 1, p);

        //ЦВЕТ
        addItem(menu, 22, new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData()), "Зеленый", "Смени цвет взрыва жертвы!", 1, p);
        addItem(menu, 23, new ItemStack(Material.WOOL, 1, DyeColor.RED.getData()), "Красный", "Смени цвет взрыва жертвы!", 1, p);
        addItem(menu, 24, new ItemStack(Material.WOOL, 1, DyeColor.BLUE.getData()), "Синий", "Смени цвет взрыва жертвы!", 1, p);
        addItem(menu, 25, new ItemStack(Material.WOOL, 1, DyeColor.PURPLE.getData()), "Пурпурный", "Смени цвет взрыва жертвы!", 1, p);
        addItem(menu, 26, new ItemStack(Material.WOOL, 1, DyeColor.ORANGE.getData()), "Оранжевый", "Смени цвет взрыва жертвы!", 1, p);

        //ПЕРЕЗАРЯДКА
        addItem(menu, 27, new ItemStack(Material.STONE_BUTTON, 10), "1.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 28, new ItemStack(Material.STONE_BUTTON, 11), "1.1 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 29, new ItemStack(Material.STONE_BUTTON, 12), "1.2 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 30, new ItemStack(Material.STONE_BUTTON, 13), "1.3 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 31, new ItemStack(Material.STONE_BUTTON, 14), "1.4 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 32, new ItemStack(Material.STONE_BUTTON, 15), "1.5 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 33, new ItemStack(Material.STONE_BUTTON, 20), "2.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 34, new ItemStack(Material.STONE_BUTTON, 30), "3.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 35, new ItemStack(Material.STONE_BUTTON, 50), "5.0 сек", "Скорость перезарядки", 1, p);

        //БАЗА
        addItem(menu, 36, new ItemStack(Material.STONE_HOE), "5.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 37, new ItemStack(Material.IRON_HOE), "5.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 38, new ItemStack(Material.GOLD_HOE), "5.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 39, new ItemStack(Material.DIAMOND_HOE), "5.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 45, addEnch(new ItemStack(Material.STONE_HOE)), "5.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 46, addEnch(new ItemStack(Material.IRON_HOE)), "5.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 47, addEnch(new ItemStack(Material.GOLD_HOE)), "5.0 сек", "Скорость перезарядки", 1, p);
        addItem(menu, 48, addEnch(new ItemStack(Material.DIAMOND_HOE)), "5.0 сек", "Скорость перезарядки", 1, p);

        //ДАЛЬНОСТЬ
        addItem(menu, 41, new ItemStack(Material.ARROW, 1), "10 блоков", "Дальность полета", 1, p);
        addItem(menu, 42, new ItemStack(Material.ARROW, 2), "20 блоков", "Дальность полета", 1, p);
        addItem(menu, 50, new ItemStack(Material.ARROW, 4), "40 блоков", "Дальность полета", 1, p);
        addItem(menu, 51, new ItemStack(Material.ARROW, 5), "50 блоков", "Дальность полета", 1, p);


        STDButtons.registerBackButton(menu, MenuType.QC_MAIN, 53);

        final ItemStack is = new ItemStack(Material.DIAMOND);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.GREEN + "Баланс");
        ArrayList<String> a = new ArrayList<String>();
        a.add(ChatColor.GRAY + "Опыт: " + ChatColor.GOLD + p.getCachedCrystals());
        im.setLore(a);
        is.setItemMeta(im);
        p.getBukkitModel().getInventory().setItem(22, is);
        menu.setExitTask(new MenuExitTask() {
            public void run(MenuInstance menuInstance) {
                menuInstance.getViewer().getInventory().removeItem(is);
            }
        });
        Misc.runOpener(menu, p);
    }
}

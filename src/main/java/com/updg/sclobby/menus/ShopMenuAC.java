package com.updg.sclobby.menus;

import com.updg.sclobby.Models.LobbyPlayer;
import com.updg.sclobby.Models.Product;
import com.updg.sclobby.menus.utils.MOption;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;
import com.updg.sclobby.utils.StringUtils;
import me.scarlet.bukkit.invmenu.*;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;

/**
 * Created by Alex
 * Date: 12.04.2014  15:33
 */
public class ShopMenuAC {
    protected void addItem(Menu m, int pos, ItemStack l, String name, String desc, final int service, LobbyPlayer p) {
        this.addItem(m, pos, l, service, p);
    }

    /**
     * @param m       Menu object
     * @param pos     Position on list
     * @param l       ItemStack
     * @param service Id of service
     */
    protected void addItem(Menu m, int pos, ItemStack l, final int service, LobbyPlayer player) {
        FunctionMenuButton fb;
        Product p = sclobby.products.get(service);
        if (p == null) {
            fb = new FunctionMenuButton(new MOption(l, ChatColor.RED + "PRODUCTS NOT FOUND", ChatColor.RED + "PRODUCTS NOT FOUND"));
        } else {
            String desc = ChatColor.GRAY + p.getDesc() + "\\n" + ChatColor.GRAY + "Цена: " + ChatColor.GOLD + p.getBubliks();
            if (p.getBubliks() > player.getCachedCrystals()) {
                desc = desc + "\\n" + ChatColor.RED + "Надо еще " + (p.getBubliks() - player.getCachedCrystals()) + " " + StringUtils.plural(p.getBubliks() - player.getCachedCrystals(), "кристал", "кристала", "кристалов");
            }
            fb = new FunctionMenuButton(new MOption(l, ChatColor.DARK_RED + p.getName(), desc));
            fb.setButtonTask(new ButtonTask() {
                public void run(MenuInstance inst, MenuButton button, Player who, boolean isDenied) {
                    sclobby.getPlayer(who.getName()).buyProduct(service);
                }
            });
        }
        m.setButton(pos, fb);
    }

    protected ItemStack addEnch(ItemStack is) {
        is.addEnchantment(Enchantment.DURABILITY, 1);
        return is;
    }

}

package com.updg.sclobby.Models;

import com.updg.sclobby.Models.enums.PlayerVIP;
import com.updg.sclobby.loginSystem.bungee.Bungee;
import com.updg.sclobby.menus.MenuType;
import com.updg.sclobby.menus.SettingsMenu;
import com.updg.sclobby.menus.qc.ConfigMenu;
import com.updg.sclobby.menus.qc.MainMenu;
import com.updg.sclobby.menus.qc.ShopMenu;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.BukkitUtils;
import com.updg.sclobby.utils.DSUtils;
import com.updg.sclobby.utils.L;
import com.updg.sclobby.utils.Redis;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Alex
 * Date: 29.10.13  21:23
 */
public class LobbyPlayer {
    private int id;
    private Player bukkitModel;
    private ArrayList<Game> qGames = new ArrayList<Game>();
    private boolean bungeeCheked = false;
    private boolean loggedIn = false;

    private boolean hidePlayers = false;
    private long lastPlayerHideChange = 0;

    public boolean incorrectPass = false;
    private int rang;
    private int vip;
    private double crystals = 0;

    public LobbyPlayer(Player p) {
        this.bukkitModel = p;
        this.clearInventory();
        String r = Redis.get("lobbyHide_" + p.getName().toLowerCase());
        if (r == null) {
            Redis.set("lobbyHide_" + p.getName().toLowerCase(), "true");
            hidePlayers = true;
        } else {
            if (r.equals("true")) {
                hidePlayers = true;
            } else {
                hidePlayers = false;
            }
        }
    }

    public Player getBukkitModel() {
        return bukkitModel;
    }

    public String getName() {
        return bukkitModel.getName();
    }

    public PlayerVIP getVip() {
        return PlayerVIP.REGULAR;
    }

    public void toServer(String url) {
        for (Game item1 : sclobby.gameQs) {
            item1.removeFromQ(this);
        }
        sclobby.toServer(this.bukkitModel, url);
    }

    public void sendMessage(String s) {
        bukkitModel.sendMessage(s);
    }

    public boolean isBungeeCheked() {
        return bungeeCheked;
    }

    public void setBungeeCheked(boolean bungeeCheked) {
        this.bungeeCheked = bungeeCheked;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void goOnline() {
        if (hidePlayers)
            BukkitUtils.hidePlayers(this);
        else
            BukkitUtils.showPlayers(this);
        BukkitUtils.newShow(this);
        this.setLoggedIn(true);
        this.clearInventory();
        this.takeItems();
        if (this.isHidePlayers()) {
            this.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "У тебя выключен показ других игроков в лобби. Используй часы у себя в инвентаре чтобы показать игроков.");
        } else {
            this.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "У тебя включен показ других игроков в лобби. Используй часы у себя в инвентаре чтобы скрыть игроков.");
        }
    }

    public void clearInventory() {
        this.getBukkitModel().setFireTicks(0);
        this.bukkitModel.closeInventory();
        this.bukkitModel.setHealth(20);
        this.bukkitModel.setExp(0);
        this.bukkitModel.setFoodLevel(20);
        this.bukkitModel.getInventory().clear();
        this.bukkitModel.getInventory().setHelmet(null);
        this.bukkitModel.getInventory().setChestplate(null);
        this.bukkitModel.getInventory().setLeggings(null);
        this.bukkitModel.getInventory().setBoots(null);
    }

    public void takeItems() {
        Iterator it = sclobby.plugin.itemsForPlayers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, ItemStack> pairs = (Map.Entry<Integer, ItemStack>) it.next();
            this.bukkitModel.getInventory().setItem(pairs.getKey(), pairs.getValue());
        }
        this.bukkitModel.updateInventory();
    }

    public void logginIn() {
        Connection conn = sclobby.getDB();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = String.format("SELECT `id`,`rang`, `vip` FROM `users` WHERE `username` = ?");
            ps = conn.prepareStatement(sql);
            ps.setString(1, this.getName());
            rs = ps.executeQuery();
            rs.next();
            id = rs.getInt("id");
            rang = rs.getInt("rang");
            vip = rs.getInt("vip");
            Redis.incr("totalJoins_" + id);
            Redis.set("lastJoin_" + id, System.currentTimeMillis() / 1000 + "");
            Redis.set("online_" + this.getName().toLowerCase(), "lobby");
            ps = sclobby.getDB().prepareStatement("INSERT INTO `usersGameJoins` (`user_id`,`datetime`,`userIP`) VALUES (?,?,?)");
            ps.setInt(1, id);
            ps.setLong(2, System.currentTimeMillis() / 1000);
            ps.setString(3, this.getBukkitModel().getAddress().getAddress().getHostAddress());
            ps.executeUpdate();
            Bungee.goOnline(id, this.getName(), rs.getInt("rang"), rs.getInt("vip"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkPassword(String pass) {
        String pass1 = Redis.get("password_" + this.getName().toLowerCase());
        if (pass1 == null) {
            this.sendMessage("Что то тут не так.");
            return false;
        }
        String[] data = pass1.split(":");
        String checkPassHash = generatePassword(pass, data[1]);

        return checkPassHash.equals(data[0]);
    }

    public String generatePassword(String toHash, String salt) {
        return hash(hash(toHash) + salt);
    }

    public String hash(String toHash) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(toHash.getBytes(), 0, toHash.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            // TODO: ECHO ERROR
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return this.rang == 1;
    }

    public boolean isModer() {
        return this.rang == 2;
    }

    public boolean isHelper() {
        return false;
    }

    public boolean isHidePlayers() {
        return hidePlayers;
    }

    public void setHidePlayers(boolean hidePlayers) {
        this.hidePlayers = hidePlayers;
    }

    public void togglePlayerVisibility() {
        if (this.lastPlayerHideChange + 5000 > System.currentTimeMillis()) {
            this.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Переключать режим отображения игроков можно только раз в 5 секунд.");
            return;
        }
        if (this.isHidePlayers()) {
            Redis.set("lobbyHide_" + getName().toLowerCase(), "false");
            this.setHidePlayers(false);
            BukkitUtils.showPlayers(this);
            this.lastPlayerHideChange = System.currentTimeMillis();
        } else {
            Redis.set("lobbyHide_" + getName().toLowerCase(), "true");
            this.setHidePlayers(true);
            BukkitUtils.hidePlayers(this);
            this.lastPlayerHideChange = System.currentTimeMillis();
        }
        this.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Режим отображения игроков переключен.");
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public boolean isAdminOrModer() {
        return isAdmin() || isModer();
    }

    public void showMenu(MenuType menuType) {
        switch (menuType) {
            case SETTINGS:
                new SettingsMenu(this);
                break;
            case QC_MAIN:
                new MainMenu(this);
                break;
            case QC_SHOP:
                new ShopMenu(this);
                break;
            case QC_SETTINGS:
                new ConfigMenu(this);
                break;
        }
    }

    public double getCrystals() {
        String[] out = DSUtils.getExpAndMoney(this.getBukkitModel());
        return this.crystals = Double.parseDouble(out[0]);
    }

    public double getCachedCrystals() {
        return this.crystals;
    }

    public void withdrawCrystals(double v) {
        String[] out = DSUtils.withdrawPlayerExpAndMoney(this.getBukkitModel(), v, 0);
    }

    public void addExp(double v) {
        String[] out = DSUtils.addPlayerExpAndMoney(this.getBukkitModel(), v, 0);
    }

    public void buyProduct(int id) {
        Product p = sclobby.products.get(id);
        if (p != null) {
            if (p.getBubliks() > this.getCrystals()) {
                this.sendMessage(ChatColor.DARK_RED + "Недостаточно кристалов");
                return;
            }
            try {
                Connection conn = sclobby.getDB();
                PreparedStatement ps;
                ResultSet rs;
                String sql = String.format("SELECT `id` FROM `usersProducts` WHERE `user_id` = ? AND `product_id` = ?");
                ps = conn.prepareStatement(sql);
                ps.setInt(1, this.getId());
                ps.setInt(2, p.getId());
                rs = ps.executeQuery();
                if (rs.next()) {
                    this.sendMessage(ChatColor.DARK_RED + "Ты уже приобрел данный продукт");
                    return;
                }

                this.withdrawCrystals(p.getBubliks());

                ps = sclobby.getDB().prepareStatement("INSERT INTO `usersProducts` (`user_id`,`product_id`,`price`,`when`) VALUES(?,?,?,NOW())");
                ps.setInt(1, this.getId());
                ps.setInt(2, p.getId());
                ps.setFloat(3, p.getBubliks());
                ps.executeUpdate();
                this.sendMessage(ChatColor.GREEN + "Поздравляем с преобретением " + ChatColor.BOLD + p.getName());

            } catch (SQLException e) {
                e.printStackTrace();
            }
            //TODO
            L.$("User " + this.getName() + " trying to buy product " + p.getName());
        } else {
            this.sendMessage("Error. Service not found.");
        }
    }

    public boolean hasProducts(int id) {
        try {
            Connection conn = sclobby.getDB();
            PreparedStatement ps;
            ResultSet rs;
            String sql = String.format("SELECT `id` FROM `usersProducts` WHERE `user_id` = ? AND `product_id` = ?");
            ps = conn.prepareStatement(sql);
            ps.setInt(1, this.getId());
            ps.setInt(2, id);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

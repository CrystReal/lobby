package com.updg.sclobby;

import com.updg.sclobby.Models.*;
import com.updg.sclobby.Models.enums.GameType;
import com.updg.sclobby.commands.Check;
import com.updg.sclobby.commands.Leave;
import com.updg.sclobby.commands.LoginCommand;
import com.updg.sclobby.commands.ReloadLobby;
import com.updg.sclobby.events.*;
import com.updg.sclobby.loginSystem.bungee.Bungee;
import com.updg.sclobby.loginSystem.bungee.PluginMessage;
import com.updg.sclobby.serverQ.listenerUpdates;
import com.updg.sclobby.serverQ.qConnection;
import com.updg.sclobby.serverQ.senderToAllServers;
import com.updg.sclobby.threads.KeepMySQLThread;
import com.updg.sclobby.threads.NotLoggedThread;
import com.updg.sclobby.threads.WatchThread;
import com.updg.sclobby.utils.DSUtils;
import com.updg.sclobby.utils.Redis;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: Alex
 * Date: 03.06.13
 * Time: 16:39
 */
public class sclobby extends JavaPlugin {
    public static Location lobbySpawn;
    private Logger log;
    public static sclobby plugin;
    private YamlConfiguration cfg;

    public static HashMap<Location, GameSign> signs = new HashMap<Location, GameSign>();
    public static List<Game> gameQs = new ArrayList<Game>();
    public static HashMap<String, LobbyPlayer> players = new HashMap<String, LobbyPlayer>();
    public static HashMap<Integer, Product> products = new HashMap<Integer, Product>();
    private listenerUpdates listener;
    private senderToAllServers senderToAllServers;
    private static Connection DB;

    public HashMap<Integer, ItemStack> itemsForPlayers = new HashMap<Integer, ItemStack>();

    private KeepMySQLThread KeepMySQLThread;

    public static Connection getDB() {
        return DB;
    }

    public void onDisable() {
        this.KeepMySQLThread.interrupt();
        log.log(Level.INFO, "[Crystal Reality Lobby] Plugin stopped!");
    }

    public void onEnable() {
        plugin = this;
        lobbySpawn = new Location(Bukkit.getWorld("world"), 0, 110, 0, 0, 0);
        lobbySpawn.getWorld().setSpawnLocation(lobbySpawn.getBlockX(), lobbySpawn.getBlockY(), lobbySpawn.getBlockZ());
        this.log = getLogger();

        this.connectToDB();
        this.loadProducts();


        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File config = new File(getDataFolder() + "/config.yml");
        cfg = YamlConfiguration.loadConfiguration(config);
        if (!config.exists()) {
            try {
                log.info("Configuring Lobby...");

                config.createNewFile();

                cfg.set("signs", signs);
                cfg.set("games", gameQs);

                log.info("------------------------------------------------------------------------------");
                log.info("Файл настроек Lobby не найден. Файл будет создан с");
                log.info("стандартными значениями.");
                log.info("------------------------------------------------------------------------------");

                cfg.save(config);
            } catch (Exception ex) {
                log.log(Level.SEVERE, "Error creating configuration file", ex);
                return;
            }
        } else {
            cfg = YamlConfiguration.loadConfiguration(config);
            try {
                PreparedStatement ps = getDB().prepareStatement("SELECT * from servers where active=1 and gameType>0");
                ResultSet r = ps.executeQuery();
                while (r.next()) {
                    Game g = new Game(r.getInt("id"), r.getString("name"), GameType.NOW, r.getString("connectUrl"));
                    gameQs.add(g);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<String> tmp = cfg.getStringList("signs");
            for (String a : tmp) {
                GameSign sign = new GameSign(a);
                sign.loadDefault();
                sclobby.signs.put(sign.getLocation(), sign);
            }
        }
        log.log(Level.INFO, "Plugin started!");

        final World w = Bukkit.getWorld("world");
        w.setThundering(false);
        w.setStorm(false);
        w.setWeatherDuration(1000000);

        qConnection.connect(cfg.getString("qServerHost", "localhost"));
        new listenerUpdates().start();
        senderToAllServers = new senderToAllServers();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                senderToAllServers.send("check");
                log.log(Level.INFO, "Request sent!");
            }
        }).start();
        log.log(Level.INFO, "qServer connected!");

        new Redis(getConfig().getString("redisServer", "localhost"), getConfig().getInt("redisServerPort", 6379));

        getCommand("reloadlobby").setExecutor(new ReloadLobby());
        getCommand("leave").setExecutor(new Leave(this));
        getCommand("check").setExecutor(new Check(this));
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("l").setExecutor(new LoginCommand());

        getServer().getPluginManager().registerEvents(new SignHandler(this), this);
        getServer().getPluginManager().registerEvents(new JoinExit(), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(), this);
        getServer().getPluginManager().registerEvents(new SystemEvents(), this);

        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "StreamBungee", new PluginMessage());
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "StreamBungee");

        try {
            DSUtils.connect(getConfig().getString("dataServer.host", "localhost"), getConfig().getInt("dataServer.port", 9898));
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "FATAL ERROR: Cant connect to the DATA server");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.addStaffToStartItemsForPlayers();

        for (Player item : Bukkit.getOnlinePlayers()) {
            sclobby.addPlayer(item);
            item.setGameMode(GameMode.ADVENTURE);
            Bungee.isLogged(item, item.getName());
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                w.setTime(6500);
            }
        }, 1000, 1000);
        new WatchThread().start();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                Redis.set("lobby_online", System.currentTimeMillis() / 1000 + "");
            }
        }, 0, 600);

        //new NotLoggedThread().start();
        this.KeepMySQLThread = new KeepMySQLThread();
        this.KeepMySQLThread.start();
    }

    public void connectToDB() {
        try {
            DB = (java.sql.Connection) DriverManager.getConnection("jdbc:mysql://localhost/crmc?autoReconnect=true&user=sc_main&password=pass&characterEncoding=UTF-8");
            log.info("Crystal Reality db enabled.");
        } catch (SQLException e) {
            log.log(Level.WARNING, "Driver loaded, but cannot connect to db: " + e);
        }
    }

    public void reload() {
        this.loadProducts();
        gameQs = new ArrayList<Game>();
        try {
            PreparedStatement ps = getDB().prepareStatement("SELECT * from servers where active=1 and gameType>0");
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                Game g = new Game(r.getInt("id"), r.getString("name"), GameType.NOW, r.getString("connectUrl"));
                gameQs.add(g);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (GameSign s : signs.values()) {
            s.loadDefault();
        }
        com.updg.sclobby.serverQ.senderToAllServers.send("check");
    }

    public void loadProducts() {
        products = new HashMap<Integer, Product>();
        try {
            PreparedStatement ps = getDB().prepareStatement("SELECT * from products");
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                products.put(r.getInt(1), new Product(r.getInt(1), r.getString(2), r.getString(3), r.getInt(4), r.getInt(5), r.getInt(6), r.getFloat(7), r.getFloat(8), r.getInt(9)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setConf(String index, ArrayList var) {
        cfg.set(index, var);
        try {
            cfg.save(new File(getDataFolder() + "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateConf() {
        ArrayList<String> tmp = new ArrayList<String>();
        for (GameSign item : signs.values()) {
            tmp.add(item.toString());
        }
        cfg.set("signs", tmp);
        try {
            cfg.save(new File(getDataFolder() + "/config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void toServer(Player p, String url) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(url);
        } catch (IOException eee) {
            Bukkit.getLogger().info("You'll never see me!");
        }
        p.sendPluginMessage(sclobby.plugin, "BungeeCord", b.toByteArray());

    }

    public boolean removeFromAllQ(LobbyPlayer p) {
        boolean is = false;
        for (Game item : sclobby.gameQs) {
            if (item.isInQ(p)) {
                is = true;
                item.removeFromQ(p);
            }
        }
        return is;
    }

    public static LobbyPlayer getPlayer(String name) {
        if (players.containsKey(name))
            return players.get(name);
        else
            return null;
    }

    public static void addPlayer(Player p) {
        if (!players.containsKey(p.getName()))
            players.put(p.getName(), new LobbyPlayer(p));
    }

    public static void removePlayer(Player p) {
        if (players.containsKey(p.getName())) {
            LobbyPlayer p1 = players.get(p.getName());
            for (Game g : sclobby.gameQs) {
                g.removeFromQ(p1);
            }
            players.remove(p.getName());
        }
    }

    public static void $(Object a) {
        System.out.println(a);
    }

    public senderToAllServers getSenderToAllServers() {
        return this.senderToAllServers;
    }

    private void addStaffToStartItemsForPlayers() {
        ItemStack clock = new ItemStack(Material.WATCH);
        ItemMeta meta = clock.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Волшебные часы" + " " + ChatColor.DARK_GRAY + "(ПКМ)");
        List<String> name = new ArrayList<String>();
        name.add(ChatColor.YELLOW + "Преключают видимость игроков в лобби.");
        meta.setLore(name);
        clock.setItemMeta(meta);
        itemsForPlayers.put(2, clock);

        ItemStack cmd = new ItemStack(Material.EMERALD);
        meta = cmd.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Настройки" + " " + ChatColor.DARK_GRAY + "(ПКМ)");
        cmd.setItemMeta(meta);
        itemsForPlayers.put(6, cmd);
    }
}

package com.updg.sclobby.Models;

import com.updg.sclobby.Models.GameServer;
import com.updg.sclobby.Models.enums.GameType;
import com.updg.sclobby.Models.enums.PlayerVIP;
import com.updg.sclobby.Models.enums.ServerStatus;
import com.updg.sclobby.sclobby;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    public int id = 0;
    String name;
    int wait = 1;
    public GameType type;
    String url;
    private ServerStatus statusNow = ServerStatus.DISABLED;
    ArrayList<GameServer> servers = new ArrayList<GameServer>();
    ArrayList<LobbyPlayer> ochered = new ArrayList<LobbyPlayer>();

    public Game(int id, String game, int w, GameType t) {
        this.id = id;
        this.name = game;
        this.wait = w;
        this.type = t;
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (ochered.size() >= wait) {
                        for (GameServer item : servers) {
                            if (item.getStatus() == ServerStatus.WAITING) {
                                int i = 1;
                                for (Iterator<LobbyPlayer> iterator = ochered.iterator(); iterator.hasNext(); ) {
                                    LobbyPlayer p1 = iterator.next();
                                    //for (Player p1 : ochered) {
                                    if (i <= wait) {
                                        for (Game item1 : sclobby.gameQs) {
                                            if (item1.isInQ(p1)) {
                                                item1.removeFromQ(p1);
                                            }
                                        }
                                        p1.getBukkitModel().sendMessage("Телепортация...");
                                        p1.toServer(item.getUrl());
                                        iterator.remove();
                                    }
                                    i++;
                                }
                                item.setStatus(ServerStatus.IN_GAME);
                            }
                        }
                    }
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public Game(int id, String game, GameType t, String url) {
        this.id = id;
        this.name = game;
        this.type = t;
        this.url = url;
    }

    public void addPlayer(LobbyPlayer p) {
        if (this.type == GameType.NOW) {
            if (this.statusNow == ServerStatus.WAITING || (p.getVip() == PlayerVIP.VIP && this.statusNow == ServerStatus.WAITING_VIP))
                p.toServer(url);
            else {
                if (this.statusNow == ServerStatus.WAITING_VIP) {
                    p.sendMessage("Игра на сервере уже началась. Но ты можешь купить VIP и зайти на него!");
                } else
                    p.sendMessage("Сейчас этот сервер не доступен.");
            }

        } else {
            if (ochered.contains(p)) {
                p.sendMessage("Ты уже в очереди на эту игру.");
            } else {
                ochered.add(p);
                p.sendMessage("Ты поставлен в очередь на игру " + name);
                p.sendMessage("Для отмены введи /leave");
            }
        }
    }

    public void addServer(GameServer s) {
        this.servers.add(s);
    }

    public void removeFromQ(LobbyPlayer p) {
        if (ochered.contains(p))
            ochered.remove(p);
    }

    public boolean isInQ(LobbyPlayer p) {
        return ochered.contains(p);
    }

    public ArrayList<GameServer> getServers() {
        return this.servers;
    }

    public ServerStatus getStatusNow() {
        return statusNow;
    }

    public void setStatusNow(ServerStatus statusNow) {
        this.statusNow = statusNow;
    }
}

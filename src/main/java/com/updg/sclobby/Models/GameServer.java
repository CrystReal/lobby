package com.updg.sclobby.Models;

import com.updg.sclobby.Models.enums.ServerStatus;

/**
 * User: Alex
 * Date: 16.06.13
 * Time: 16:23
 */
public class GameServer {
    private int id = 0;
    private String url;
    private ServerStatus status = ServerStatus.DISABLED;
    private String displayStatus = "OFFLINE";
    private int players = 0;

    public GameServer(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ServerStatus getStatus() {
        return this.status;
    }

    public void setStatus(ServerStatus access) {
        this.status = access;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String displayStatus) {
        this.displayStatus = displayStatus;
    }
}

package com.updg.sclobby.utils;

import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Alex
 * Date: 13.11.13  0:14
 */
public class DSUtils {
    public static Socket socket;
    public static BufferedReader in;
    public static PrintWriter out;

    public static String serverAddress;
    public static int port;

    public static void connect(String serverAddress, int port) throws IOException {
        DSUtils.serverAddress = serverAddress;
        DSUtils.port = port;
        socket = new Socket(serverAddress, port);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private static void checkConnection() {
        if (!socket.isConnected() || socket.isClosed()) {
            try {
                connect(serverAddress, port);
            } catch (IOException e) {
                e.printStackTrace();
                checkConnection();
            }
        }
    }

    public static String[] getExpAndMoney(Player p) {
        checkConnection();
        out.println("getPlayerExpAndMoney\t" + p.getName());
        String response;
        try {
            response = in.readLine();
        } catch (IOException ex) {
            return null;
        }
        return response.split(":");
    }

    public static String[] withdrawPlayerExpAndMoney(Player p, double exp, double money) {
        checkConnection();
        out.println("withdrawPlayerExpAndMoney\t" + p.getName() + ":" + exp + ":" + money);
        String response;
        try {
            response = in.readLine();
        } catch (IOException ex) {
            return null;
        }
        return response.split(":");
    }

    public static String[] addPlayerExpAndMoney(Player p, double exp, double money) {
        checkConnection();
        out.println("addPlayerExpAndMoney\t" + p.getName() + ":" + exp + ":" + money);
        String response;
        try {
            response = in.readLine();
        } catch (IOException ex) {
            return null;
        }
        return response.split(":");
    }

    // QuakeCraft

    public static String[] QC_getParams(Player p) {
        checkConnection();
        out.println("QCgetParams\t" + p.getName());
        String response;
        try {
            response = in.readLine();
        } catch (IOException ex) {
            return null;
        }
        return response.split(":");
    }

    public static String[] QC_setParams(Player p, String[] params) {
        checkConnection();
        out.println("QCsetParams\t" + p.getName() + "\t" + StringUtils.join(params, ":"));
        String response;
        try {
            response = in.readLine();
        } catch (IOException ex) {
            return null;
        }
        return response.split(":");
    }

    // TNTRun

    public static String[] TNTRun_getParams(Player p) {
        checkConnection();
        out.println("TNTRUNgetParams\t" + p.getName());
        String response;
        try {
            response = in.readLine();
        } catch (IOException ex) {
            return null;
        }
        return response.split(":");
    }
}

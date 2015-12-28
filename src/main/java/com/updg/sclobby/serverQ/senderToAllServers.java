package com.updg.sclobby.serverQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.updg.sclobby.sclobby;
import org.bukkit.Bukkit;

import java.io.IOException;

/**
 * Created by Alex
 * Date: 29.10.13  21:51
 */
public class senderToAllServers {
    private Channel c = null;
    QueueingConsumer consumer = null;
    public static senderToAllServers instance = null;

    public senderToAllServers() {
        if (instance != null)
            return;
        try {
            c = qConnection.c.createChannel();
        } catch (IOException e) {
            sclobby.$("FATAL ERROR: Cant create channel for sender to all servers");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(sclobby.plugin);
            return;
        }
        try {
            c.exchangeDeclare("toAllServers", "fanout");
        } catch (IOException e) {
            sclobby.$("FATAL ERROR: cant declare channel for sender to all servers");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(sclobby.plugin);
            return;
        }
    }

    public static void send(String msg) {
        if (instance == null)
            instance = new senderToAllServers();
        try {
            instance.c.basicPublish("toAllServers", "",
                    null,
                    msg.getBytes());
        } catch (IOException e) {
            sclobby.$("ERROR: cant sent message to all servers");
            e.printStackTrace();
        }
    }
}

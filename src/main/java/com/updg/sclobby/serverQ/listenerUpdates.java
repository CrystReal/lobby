package com.updg.sclobby.serverQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.updg.sclobby.Models.Game;
import com.updg.sclobby.Models.GameServer;
import com.updg.sclobby.Models.enums.GameType;
import com.updg.sclobby.Models.enums.ServerStatus;
import com.updg.sclobby.events.SignHandler;
import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.IOException;

/**
 * Created by Alex
 * Date: 29.10.13  20:39
 */
public class listenerUpdates extends Thread {
    private Channel c = null;
    QueueingConsumer consumer = null;

    public void run() {
        try {
            c = qConnection.c.createChannel();
        } catch (IOException e) {
            sclobby.$("FATAL ERROR: Cant create channel for listing server updates");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(sclobby.plugin);
            return;
        }
        try {
            c.queueDeclare("serversUpdates", true, false, false, null);
            consumer = new QueueingConsumer(c);
            c.basicConsume("serversUpdates", true, consumer);
        } catch (IOException e) {
            sclobby.$("FATAL ERROR: cant start listen channel for servers updates");
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(sclobby.plugin);
            return;
        }
        while (true) {
            QueueingConsumer.Delivery delivery = null;
            try {
                delivery = consumer.nextDelivery();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String message = new String(delivery.getBody());
            String[] line = message.split(":");
            int i = 0, id = 0, i1 = 0, i2 = 0;
            String i3 = "";
            ServerStatus status = ServerStatus.DISABLED;
            String signStatus = "";

            for (String a : line) {
                i++;
                switch (i) {
                    case 1:
                        id = Integer.parseInt(a);
                        break;
                    case 2:
                        status = ServerStatus.valueOf(a);
                        break;
                    case 3:
                        signStatus = a;
                        if (signStatus.equals("В ОЖИДАНИИ"))
                            signStatus = ChatColor.DARK_GREEN + "В ОЖИДАНИИ";
                        break;
                    case 4:
                        i1 = (Integer.parseInt(a));
                        break;
                    case 5:
                        i2 = (Integer.parseInt(a));
                        break;
                    case 6:
                        i3 = a;
                        break;
                }
            }


            for (Game g : sclobby.gameQs) {
                if (g.id == id) {
                    if (g.type == GameType.OCHERED) {
                        for (GameServer gs : g.getServers()) {
                            if (gs.getId() == id) {
                                gs.setStatus(status);
                            }
                        }
                    } else if (g.type == GameType.NOW) {
                        g.setStatusNow(status);
                        //if (g.type == GameType.NOW)
                        SignHandler.updateSign(id, signStatus, i1 + "/" + i2, i3, true);
                       /* else
                            SignHandler.updateSign(id, signStatus, i1 + "/" + i2, i3, false);  */
                    }
                }
            }
        }
    }

}

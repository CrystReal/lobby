package com.updg.sclobby.threads;

import com.updg.sclobby.sclobby;
import com.updg.sclobby.utils.L;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Alex
 * Date: 2/8/14  15:31
 */
public class KeepMySQLThread extends Thread {
    private PreparedStatement ps;

    public void run() {
        while (!this.isInterrupted())
            try {
                ps = sclobby.getDB().prepareStatement("SELECT 1");
                ps.execute();
                Thread.sleep(10000);
            } catch (Exception e) {
                L.$("Reconnecting to DB");
            }
    }
}

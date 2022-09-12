package org.Ciuris;

import static org.Ciuris.Sys.frequency;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class MonitoringSystem implements Runnable{
    private double rpmBound;
    private double setpoint;
    private static String name = "Speed Channel";  // default name
    private Date currentTime;
    private Relay relayRunnable; // reference to the relay


    /**
     *  Default Constructor
     */
    public MonitoringSystem(Relay relayRunnable) {
        rpmBound = 5000;
        setpoint = 4000;
        currentTime = new Date(System.currentTimeMillis());
        this.relayRunnable = relayRunnable;
    }

    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            if(frequency> setpoint) {
                relayRunnable.setClosed(true);
            } else {
                relayRunnable.setClosed(false);
            }
        }
    }
}

package org.Ciuris;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import static org.Ciuris.Sys.frequency;

public class Relay implements Runnable {
    private boolean closed;
    private Date currentTime;


    public Relay() {
        closed = false;
        currentTime = new Date(System.currentTimeMillis());
    }
    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    public boolean getClosed(){
        return closed;
    }

    @Override
    public void run() {
        while (true) {
        }
    }
}

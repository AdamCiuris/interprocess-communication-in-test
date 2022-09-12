package org.Ciuris;


import static org.Ciuris.Sys.frequency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Sys  {
    public static volatile int frequency = 0; // volatile int meant to simulate a controller changing process field
    static MonitoringSystem monitorRunnable; // runnable class for monitor
    static Relay relayRunnable; // runnable for relay class
    static boolean shutdown = false; // if set to true the process will end after one loop

    public static void turnOff() {
        shutdown = true;
    }

    /**
     * Returns the state of the simulated relay.
     * @return true for closed, false for open
     */
    public static boolean ReadRelayState() {
        return relayRunnable.getClosed();
    }

    /**
     * Simulates controller changing frequency.
     * @param toSet - int to set the volatile frequency to.
     */
    public static void SetSignal(int toSet) {
        frequency = toSet;
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        // begin thread pool management
        ExecutorService service = Executors.newFixedThreadPool(2);
        monitorRunnable = new MonitoringSystem(relayRunnable);
        service.execute(relayRunnable);
        service.execute(monitorRunnable);
        // end thread pool
        // begin API initialization
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st ;
        List<String> inputs = new ArrayList<>();
        String in = "";
        // end init
        while (!shutdown) {
            // begin accepting API input
            try {
                in = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            st = new StringTokenizer(in);
            while(st.hasMoreTokens()) {
                inputs.add(st.nextToken()); // putting tokens into string array
            }
            // end accepting
            // process input
            switch (inputs.get(0)) {
                case "ReadRelayState":// return true if closed and false if open
                    System.out.println(ReadRelayState());
                case "SetFrequency": // changes frequency
                    if (inputs.size() >1) { // input parse
                        SetSignal(Integer.valueOf(inputs.get(1)));
                    } // else do nothing
                case "Shutdown": // ends process
                    break;
            }
            // end processing input
            inputs.clear(); // clearing string arraylist for new inputs

        } // end while loop
        System.exit(0);

    }

}
import org.Ciuris.Sys;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.Ciuris.Sys.ReadRelayState;
import static org.Ciuris.Sys.SetSignal;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class MonitorSystemTests {
    protected Sys mySystem;
    protected Thread main;
    protected static final String closed = "true"; // define closed = false
    protected static final String opened = "false"; // define opened = true
    OutputStream out;
    OutputStreamWriter osw;
    InputStream in;
    InputStreamReader inr;

    protected Process pro;
    String inputString = ""; // used to accept output from process

    /**
     * Create process and begin setup for api communication
     */
    @BeforeEach
    void setup()  { // runs once
        Path filepath = Paths.get("").toAbsolutePath(); // begin relative file path build
        filepath = Paths.get(filepath+ "\\src\\test\\java\\" + "System.jar"); // end relative filepath
        ProcessBuilder builder = new ProcessBuilder("java", "-jar", filepath.toString());
        pro = null;
        try{
            pro = builder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        out = pro.getOutputStream();
        osw = new OutputStreamWriter(out);
        in = pro.getInputStream();
        inr = new InputStreamReader(in);
    }

    /**
     * Kills process and clears input array. Absolutely necessary for resource management.
     */
    @AfterEach
    void teardown() {
        pro.destroyForcibly();
        inputString = "";
    }

    /**
     * tests volatile frequency field
     */
    @Test
    void frequencyTest01() {
        SetSignal(4001);
        assertEquals(4001, mySystem.frequency);
    }
    /**
     * tests volatile frequency field
     */
    @Test
    void frequencyTest02() {
        SetSignal(4000);
        assertEquals(4000, mySystem.frequency);
    }
    /**
     * tests volatile frequency field
     */
    @Test
    void frequencyTest03() {
        SetSignal(3999);
        assertEquals(3999, mySystem.frequency);
    }

    /**
     * Tests interprocess communication when frequency isn't yet set. Frequency should default to 4000.
     */
    @Test
    void interProcessCommunication01() throws IOException, InterruptedException {
        // BEGIN API READ/WRITE
        osw.write("ReadRelayState\n");
        osw.flush();
        Thread.sleep(1000); // have to wait for reply from process, I'm sure there's a much more elegant way to do this.
        while(inr.ready()) {
            char b =(char) inr.read();
            inputString += b;
        }
        // END API READ/WRITE

        inputString = inputString.replaceAll("\\n|\\r\\n", ""); // handles weird new line errors, could cause problems with faulty new lines
        assertEquals(opened, inputString);
    }
    /**
     * Tests interprocess communication when frequency is manually set to 3999.
     * Means that the process should return true and is therefore in closed state.
     */
    @Test
    void interProcessCommunication02() throws IOException, InterruptedException {
        // BEGIN API READ/WRITE
        osw.write("SetFrequency 3999\n");
        osw.flush();
        osw.write("ReadRelayState\n");
        osw.flush();
        Thread.sleep(1000);
        while(inr.ready()) {
            char b =(char) inr.read();
            inputString += b;
        }
        inputString = inputString.replaceAll("\\n|\\r\\n", ""); // handles weird new line errors, could cause problems with faulty new lines
        // END API READ/WRITE

        assertEquals(opened, inputString);
    }
    /**
     * Tests interprocess communication when frequency is manually set to 3999.
     * Means that the process should return true and is therefore in closed state.
     */
    @Test
    void interProcessCommunication03() throws IOException, InterruptedException {
        // BEGIN API READ/WRITE
        osw.write("SetFrequency 4000\n");
        osw.flush();
        osw.write("ReadRelayState\n");
        osw.flush();
        Thread.sleep(1000);
        while(inr.ready()) {
            char b =(char) inr.read();
            inputString += b;
        }
        inputString = inputString.replaceAll("\\n|\\r\\n", ""); // handles weird new line errors, could cause problems with faulty new lines
        // END API READ/WRITE

        assertEquals(opened, inputString);
    }
    /**
     * Tests interprocess communication when frequency is manually set to 4001.
     * Means that the process should return true and is therefore in closed state.
     */
    @Test
    void interProcessCommunication04() throws IOException, InterruptedException {
        // BEGIN API READ/WRITE
        osw.write("SetFrequency 4001\n");
        osw.flush();
        osw.write("ReadRelayState\n");
        osw.flush();
        Thread.sleep(1000);
        while(inr.ready()) {
            char b =(char) inr.read();
            inputString += b;
        }
        // END API READ/WRITE

        inputString = inputString.replaceAll("\\n|\\r\\n", ""); // handles weird new line errors, could cause problems with faulty new lines
        assertEquals(closed, inputString);
    }
}



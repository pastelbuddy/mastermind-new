/**
 * Log.java
 *
 * @author Jeremy
 * <p>
 * This class handles logging everything that happens in the game
 */

package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

    private boolean enabled;
    private String filename;
    private BufferedWriter output;

    public Log() {
        this.filename = "Log.txt";
        enabled = false;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (enabled)
            try {
                output = new BufferedWriter(new FileWriter(filename));
            } catch (IOException e) {
                System.err.println("Error opening log file for writing.");
                System.err.println(e.getMessage());
            }
        else
            try {
                if (output != null)
                    output.close();
            } catch (IOException e) {
                System.err.println("Error closing log file.");
                System.err.println(e.getMessage());
            }
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void addEvent(String event) {
        try {
            output.write(event + "\n");
            output.flush();
        } catch (IOException e) {
            System.err.println("Error writing to log file.");
            System.err.println(e.getMessage());
        }
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}

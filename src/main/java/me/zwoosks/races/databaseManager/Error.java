package me.zwoosks.races.databaseManager;

import me.zwoosks.races.Races;

import java.util.logging.Level;

public class Error {
    public static void execute(Races plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(Races plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}
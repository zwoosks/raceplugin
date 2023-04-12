package me.zwoosks.races;

import me.zwoosks.races.commands.RacesCommands;
import me.zwoosks.races.commands.framework.CommandFramework;
import me.zwoosks.races.databaseManager.Database;
import me.zwoosks.races.databaseManager.SQLite;
import me.zwoosks.races.events.RacesEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Races extends JavaPlugin {

    CommandFramework framework;

    private Database db;

    @Override
    public void onEnable() {
        // Config
        this.saveDefaultConfig();
        // Register commands and autocompleters
        framework = new CommandFramework(this);
        framework.registerCommands(new RacesCommands(this));
        framework.registerHelp();
        // Register event listener
        this.getServer().getPluginManager().registerEvents(new RacesEvents(this), this);
        // Load SQLite database
        this.db = new SQLite(this);
        this.db.load();
        // All good message
        this.getLogger().info("Races ::: Plugin initialized succesfully.");
    }

    public Database getDatabase() {
        return db;
    }

}

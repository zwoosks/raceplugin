package me.zwoosks.races.events;

import me.zwoosks.races.Races;
import me.zwoosks.races.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class RacesEvents implements Listener {

    private Races plugin;

    public RacesEvents(Races plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if(hasRace(e.getPlayer().getUniqueId())) Utils.updateEffects(e.getPlayer(), plugin.getDatabase().getRace(e.getPlayer().getUniqueId()));
    }

    private boolean hasRace(UUID uuid) {
        String race = plugin.getDatabase().getRace(uuid);
        if(race == null || race == "") return false;
        return true;
    }

}

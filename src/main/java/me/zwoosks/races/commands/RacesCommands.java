package me.zwoosks.races.commands;

import me.zwoosks.races.Races;
import me.zwoosks.races.commands.framework.Command;
import me.zwoosks.races.commands.framework.CommandArgs;
import me.zwoosks.races.commands.framework.Completer;
import me.zwoosks.races.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RacesCommands {

    Races plugin;

    public RacesCommands(Races plugin) {
        this.plugin = plugin;
    }

    @Command(name = "race", description = "View and assign your race", usage = "/race [select,change,info]")
    public void raceCommand(CommandArgs args) {
        String race = plugin.getDatabase().getRace(args.getPlayer().getUniqueId());
        if(race == null) {
            args.getSender().sendMessage(Utils.chat(plugin.getConfig().getString("messages.playerInfoNone")));
        } else {
            args.getSender().sendMessage(Utils.chat(plugin.getConfig().getString("messages.playerInfo").replace("%race%", race)));
        }
    }

    @Command(name = "race.select", permission = "race.select", noPerm = "You do not have permission to perform this action!")
    public void assignCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if(args.length() == 1) {
            if(!hasRace(player.getUniqueId())) {
                String argument = StringUtils.capitalize(args.getArgs(0));
                if(racesList().contains(argument)) {
                    plugin.getDatabase().setOrCreatePlayerData(player.getUniqueId(), false, argument);
                    Utils.updateEffects(player, argument);
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.assigned").replace("%race%", argument)));
                } else {
                    args.getSender().sendMessage(Utils.chat(plugin.getConfig().getString("messages.invalidRace")));
                }
            } else {
                args.getSender().sendMessage(Utils.chat(plugin.getConfig().getString("messages.raceAlreadyAssigned")));
            }
        } else {
            args.getSender().sendMessage(Utils.chat(plugin.getConfig().getString("messages.specifyAssign")));
        }
    }


    @Command(name = "race.info", permission = "race.info", noPerm = "You do not have permission to perform this action!")
    public void infoCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if(args.length() == 1) {
            String race = StringUtils.capitalize(args.getArgs(0));
            switch (race) {
                case "Human":
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("descriptions.human")));
                    break;
                case "Elf":
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("descriptions.elf")));
                    break;
                case "Orc":
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("descriptions.orc")));
                    break;
                case "Beastfolk":
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("descriptions.beastfolk")));
                    break;
                case "Dwarf":
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("descriptions.dwarf")));
                    break;
                default:
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.invalidRace")));
            }
        } else {
            player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.argsErrorInfo")));
        }
    }

    @Command(name = "race.change", permission = "race.change", noPerm = "You do not have permission to perform this action!")
    public void changeCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if(args.length() == 1) {
            String race = StringUtils.capitalize(args.getArgs(0));
            if(hasRace(player.getUniqueId())) {
                if(racesList().contains(race)) {
                    boolean didChange = plugin.getDatabase().hasChanged(player.getUniqueId());
                    if(didChange) {
                        player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.alreadyChanged")));
                    } else {
                        String oldRace = plugin.getDatabase().getRace(player.getUniqueId());
                        if(oldRace.equalsIgnoreCase(race)) {
                            player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.sameRacesChange")));
                        } else {
                            plugin.getDatabase().setOrCreatePlayerData(args.getPlayer().getUniqueId(), true, race);
                            Utils.updateEffects(player, race);
                            player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.changed").replace("%race%", race)));
                        }
                    }
                } else {
                    player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.invalidRace")));
                }
            } else {
                player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.cannotChangeNotAssigned")));
            }
        } else {
            player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.argsErrorChange")));
        }
    }

    private boolean hasRace(UUID uuid) {
        String race = plugin.getDatabase().getRace(uuid);
        if(race == null || race == "") return false;
        return true;
    }

    @Completer(name = "race.select")
    public List<String> selectCompleter(CommandArgs args) {
        return racesList();
    }

    @Completer(name = "race.info")
    public List<String> infoCompleter(CommandArgs args) {
        return racesList();
    }

    @Completer(name = "race.change")
    public List<String> changeCompleter(CommandArgs args) {
        return racesList();
    }

    @Completer(name = "race")
    public List<String> mainCompleter(CommandArgs args) {
        List<String> list = new ArrayList<String>();
        list.add("select");
        list.add("change");
        list.add("info");
        return list;
    }

    private List<String> racesList() {
        List<String> list = new ArrayList<>();
        list.add("Human");
        list.add("Elf");
        list.add("Orc");
        list.add("Beastfolk");
        list.add("Dwarf");
        return list;
    }

}

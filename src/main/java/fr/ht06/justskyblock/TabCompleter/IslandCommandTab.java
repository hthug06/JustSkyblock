package fr.ht06.justskyblock.TabCompleter;

import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IslandCommandTab implements TabCompleter {

    IslandManager islandManager = JustSkyblock.islandManager;

    public IslandCommandTab(JustSkyblock justSkyblock) {
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length == 1){
            List<String> listearg0 = new ArrayList<>();
            if (!islandManager.playerHasIsland(player.getName())){
                listearg0.add("create");
                listearg0.add("decline");
                listearg0.add("help");
                listearg0.add("join");
                listearg0.add("visit");

                return listearg0;
            }
            else{
                Island island = islandManager.getIslandbyplayer(player.getName());
                if (island.isOwner(player.getUniqueId())){
                    //listearg0.add("decline");
                    listearg0.add("delete");
                    listearg0.add("demote");
                    listearg0.add("help");
                    listearg0.add("invite");
                    //listearg0.add("join");
                    listearg0.add("kick");
                    //listearg0.add("leave");
                    listearg0.add("level");
                    listearg0.add("promote");
                    listearg0.add("quest");
                    listearg0.add("rankup");
                    listearg0.add("setname");
                    listearg0.add("setspawn");
                    listearg0.add("settings");
                    listearg0.add("team");
                    listearg0.add("upgrade");
                    listearg0.add("visit");

                    return listearg0;
                }
                else if (island.isModerator(player.getUniqueId())){
                    //listearg0.add("decline");
                    //listearg0.add("delete");
                    listearg0.add("demote");
                    listearg0.add("help");
                    listearg0.add("invite");
                    //listearg0.add("join");
                    listearg0.add("kick");
                    listearg0.add("leave");
                    listearg0.add("level");
                    listearg0.add("promote");
                    listearg0.add("quest");
                    listearg0.add("rankup");
                    listearg0.add("setspawn");
                    listearg0.add("settings");
                    listearg0.add("team");
                    listearg0.add("upgrade");
                    listearg0.add("visit");

                    return listearg0;
                }
                else if (island.isMember(player.getUniqueId())) {
                    //listearg0.add("decline");
                    //listearg0.add("delete");
                    //listearg0.add("demote");
                    listearg0.add("help");
                    //listearg0.add("invite");
                    //listearg0.add("join");
                    //listearg0.add("kick");
                    listearg0.add("leave");
                    //listearg0.add("promote");
                    listearg0.add("quest");
                    listearg0.add("rankup");
                    //listearg0.add("setspawn");
                    //listearg0.add("settings");
                    listearg0.add("team");
                    listearg0.add("visit");

                    return listearg0;
                }

            }
        }
        if (strings.length == 2){
            if (strings[0].equalsIgnoreCase("visit") || strings[0].equalsIgnoreCase("invite")){
                List<String> listearg1 = new ArrayList<>();

                for (Player p: Bukkit.getOnlinePlayers())
                        listearg1.add(p.getName());
                return listearg1;
            }
        }
        if (strings.length == 2){
            if (strings[0].equalsIgnoreCase("promote") || strings[0].equalsIgnoreCase("demote")){
                List<String> listearg1 = new ArrayList<>();

                for (Player p: Bukkit.getOnlinePlayers())
                    listearg1.add(p.getName());
                return listearg1;
            }
        }


        return List.of();
    }
}

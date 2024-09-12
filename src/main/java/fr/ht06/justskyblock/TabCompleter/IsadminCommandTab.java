package fr.ht06.justskyblock.TabCompleter;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IsadminCommandTab implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> listearg0 = new ArrayList<>();

        if (strings.length == 1) {
            listearg0.add("allisland");
            listearg0.add("help");
            listearg0.add("info");
            listearg0.add("reload");
            return listearg0;
        }
        if (strings.length == 2){
            if (strings[0].equalsIgnoreCase("info")){
                List<String> listearg1 = new ArrayList<>();

                listearg1.add("island");
                listearg1.add("player");
                return listearg1;
            }
        }

        if (strings.length == 3){
            if (strings[1].equalsIgnoreCase("player")){
                List<String> listearg1 = new ArrayList<>();

                for (Player p: Bukkit.getOnlinePlayers())
                    listearg1.add(p.getName());
                return listearg1;
            }
        }
        return List.of();
    }
}

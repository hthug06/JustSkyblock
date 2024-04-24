package fr.ht06.skyblockplugin.TabCompleter;

import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class IslandCommandTab implements TabCompleter {
    public IslandCommandTab(SkyblockPlugin skyblockPlugin) {
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        List<String> liste = new ArrayList<>();
        liste.add("setspawn");

        return liste;
    }
}

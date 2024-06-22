package fr.ht06.justskyblock.Commands;

import fr.ht06.justskyblock.Config.IslandLevel;
import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class skyblockpluginCommand implements CommandExecutor {
    JustSkyblock main;
    public skyblockpluginCommand(JustSkyblock main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        main.reloadConfig();
        if (new File(Bukkit.getServer().getPluginManager().getPlugin("justSkyblock").getDataFolder(), "level.yml").exists()) {
            IslandLevel.reload();
        }
        else{
            JustSkyblock.getInstance().createLevelConfig();
        }
        commandSender.sendMessage("§cConfig reload");
        return true;
    }

}

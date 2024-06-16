package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.Config.IslandLevel;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class skyblockpluginCommand implements CommandExecutor {
    SkyblockPlugin main;
    public skyblockpluginCommand(SkyblockPlugin main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        main.reloadConfig();
        if (new File(Bukkit.getServer().getPluginManager().getPlugin("SkyblockPlugin").getDataFolder(), "level.yml").exists()) {
            IslandLevel.reload();
        }
        else{
            SkyblockPlugin.getInstance().createLevelConfig();
        }
        commandSender.sendMessage("§cConfig reload");
        return true;
    }

}

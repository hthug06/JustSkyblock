package fr.ht06.skyblockplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Test implements CommandExecutor {
    SkyblockPlugin main;
    public Test(SkyblockPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;

        player.sendMessage(String.valueOf(main.getConfig().getConfigurationSection("IS").getKeys(false)));

        List<String> liste = new ArrayList<>(main.getConfig().getConfigurationSection("IS").getKeys(false));

        for(String string : liste){
            List<String> liste2 = new ArrayList<>(main.getConfig().getConfigurationSection("IS."+string).getKeys(false));
            for (String string1 : liste2)
            player.sendMessage(string1);
        }
        List<String> configIS = new ArrayList<>(SkyblockPlugin.getInstance().getConfig().getConfigurationSection("IS").getKeys(false));

        for (String name: configIS){
            List<String> configISchild = new ArrayList<>(SkyblockPlugin.getInstance().getConfig().getConfigurationSection("IS."+name).getKeys(false));
            player.sendMessage(String.valueOf(configISchild));
            player.sendMessage(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(0))));
            player.sendMessage(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(1))));
            player.sendMessage(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(2))));
            player.sendMessage(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(3))));
            player.sendMessage(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(4))));




        }
        player.sendMessage( String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS.Plain.Block")));
        player.teleport(new Location(Bukkit.getWorld("world_Skyblock"), 0, 70, 0));

        return true;
    }
}

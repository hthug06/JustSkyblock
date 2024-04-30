package fr.ht06.skyblockplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {


    SkyblockPlugin main;
    public Test(SkyblockPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if(strings.length == 0){
            if (player.getLocation().getWorld().getName().equalsIgnoreCase("world_Skyblock")){
                player.teleport(new Location(Bukkit.getWorld("world"), player.getX(), player.getY(), player.getZ()));
            }
        }


        if (strings.length == 1){
            int[] a = {1,3};
            player.sendMessage(String.valueOf(main.CoordsTaken));
            /*for (String v: DataConfig.get().getConfigurationSection("Players").getKeys(false)){
                System.out.println(v);
                FileConfiguration dataconfig = DataConfig.get();
                Location loc = new Location(Bukkit.getWorld(dataconfig.get("Players."+v+".world").toString()),
                        dataconfig.getDouble("Players."+v+".x"),
                        dataconfig.getDouble("Players."+v+".y"),
                        dataconfig.getDouble("Players."+v+".z"),
                        dataconfig.getInt("Players."+v+".pitch"),
                        dataconfig.getInt("Players."+v+".yaw"));
                player.sendMessage(String.valueOf(loc));
            }*/


        }


        return true;
    }
}

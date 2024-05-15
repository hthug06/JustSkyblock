package fr.ht06.skyblockplugin;

import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Test implements CommandExecutor {

    IslandManager islandManager  = SkyblockPlugin.islandManager;
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
            Location loc= new Location(Bukkit.getWorld("world_Skyblock"),islandManager.getIslandbyplayer(player.getName()).getIslandCoordinates().get(0), 70, islandManager.getIslandbyplayer(player.getName()).getIslandCoordinates().get(1));
            player.sendMessage(String.valueOf(islandManager.getIslandbyplayer(player.getName()).IStoMap()));
            //Island island=  new Island(player.getName()+"'s Island", player.getName(),list, new Location(Bukkit.getWorld("world"), 1000, 0, 1000));
            //SkyblockPlugin.islandManager.addIsland(island);
            //player.sendMessage(String.valueOf(SkyblockPlugin.islandManager.getAllIslandtoMap()));
            //player.sendMessage(String.valueOf(SkyblockPlugin.islandManager.getIslandtoMap(player.getName()+"'s Island")));
            //player.sendMessage(String.valueOf(SkyblockPlugin.islandManager.playerHasIsland(strings[0])));
            /*for (Map.Entry<String , Location> v : main.IScoor.entrySet()){
                if  (IslandCommand.onIsland(player.getLocation(),v.getValue().clone().add(-50, -200, -50), v.getValue().clone().add(50, 300, 50))){
                    player.sendMessage("you are on "+v.getKey()+"'s Island");
                }
            }*/
            //player.sendMessage(String.valueOf(SkyblockPlugin.playerIslandSettings));
            //player.sendMessage(String.valueOf(main.hasIS));

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

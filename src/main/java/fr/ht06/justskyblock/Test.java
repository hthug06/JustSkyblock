package fr.ht06.justskyblock;

import fr.ht06.justskyblock.Config.IslandLevel;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.Format;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Test implements CommandExecutor {

    IslandManager islandManager  = JustSkyblock.islandManager;
    MiniMessage miniMessage  =  MiniMessage.miniMessage();
    JustSkyblock main;
    public Test(JustSkyblock main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if(strings.length == 0){
            //if (player.getLocation().getWorld().getName().equalsIgnoreCase("world_Skyblock")){
            //    player.teleport(new Location(Bukkit.getWorld("world"), player.getX(), player.getY(), player.getZ()));
            //}
            //SkyblockPlugin.customConfig.
            //SkyblockPlugin.customConfig.set("Material.Block.DIRT", (Integer) SkyblockPlugin.customConfig.get("Material.Block.DIRT")+1);
            //player.sendMessage(String.valueOf(IslandLevel.get().getConfigurationSection("Material.Special").getKeys(false)));
//            for (String path : IslandLevel.get().getConfigurationSection("Material").getKeys(false)){
//                player.sendMessage(path);
//            }
//            player.sendMessage(String.valueOf(IslandLevel.get().getConfigurationSection("Material." +  "Special").getKeys(false).contains(player.getInventory().getItemInMainHand().getType().name())));
//            //player.sendMessage(String.valueOf(IslandLevel.get().getList("Material.Special").contains(player.getInventory().getItemInMainHand().getType().name())));
//            player.sendMessage(String.valueOf(IslandLevel.get().get("Material.Block.STONE")));
            Island island=islandManager.getIslandbyplayer(player.getName());
            //player.sendMessage(String.valueOf(island.getSize()));
            YamlConfiguration tradeConfig = JustSkyblock.tradeConfig;
            for (String base: tradeConfig.getConfigurationSection("Trade.").getKeys(false)){
                for (String map : tradeConfig.getConfigurationSection("Trade."+base+".mainItem.").getKeys(true)){
                    player.sendMessage(tradeConfig.getString("Trade."+base+".mainItem."+map));
                }
            }
//            player.sendMessage(String.valueOf(island.getCobbleGenLevel()));
//            player.sendMessage(String.valueOf(island.getCobbleGenLevelUnlock()));
        }


        if (strings.length >= 1){
//            Island island=islandManager.getIslandbyplayer(player.getName());
//            island.setCobbleGenLevelUnlock(6);
            player.sendMessage(Arrays.toString(player.getInventory().getContents()));
            Island island=islandManager.getIslandbyplayer(player.getName());
            island.setSize(Integer.parseInt(strings[0]));
            //SkyblockPlugin.customConfig.set("Material.Block.DIRT", (Integer) SkyblockPlugin.customConfig.get("Material.Block.DIRT")+1);
            //player.sendMessage(String.valueOf(islandManager.getIslandbyplayer(player.getName()).getAllMembers()));
            //player.sendMessage(String.valueOf(islandManager.getIslandbyplayer(player.getName()).getAllModerators()));
            //Location loc= new Location(Bukkit.getWorld("world_Skyblock"),islandManager.getIslandbyplayer(player.getName()).getIslandCoordinates().get(0), 70, islandManager.getIslandbyplayer(player.getName()).getIslandCoordinates().get(1));
            //player.sendMessage(String.valueOf(islandManager.getIslandbyplayer(player.getName()).IStoMap()));
            /*for (int i = 0; i<10001; i+=1000){//nombre d efoisd ou le code va aller en cercle
                for (int j = 0; j<10001; j+=1000){
                    twoValue.add(i);
                    twoValue.add(j);
                    if (!liste.contains(twoValue)){
                        liste.add(twoValue);
                        player.sendMessage(String.valueOf(i) +" "+ String.valueOf(j));
                    }
                    twoValue = new ArrayList<>();
                }
                for (int j = 0; j>-10001; j-=1000){
                    twoValue.add(i);
                    twoValue.add(j);
                    if (!liste.contains(twoValue)){
                        liste.add(twoValue);
                        player.sendMessage(String.valueOf(i) +" "+ String.valueOf(j));
                    }
                    twoValue = new ArrayList<>();
                }
            }
            for (int i = 0; i>-10001; i-=1000){//nombre d efoisd ou le code va aller en cercle
                for (int j = 0; j<10001; j+=1000){
                    twoValue.add(i);
                    twoValue.add(j);
                    if (!liste.contains(twoValue)){
                        liste.add(twoValue);
                        player.sendMessage(String.valueOf(i) +" "+ String.valueOf(j));
                    }
                    twoValue = new ArrayList<>();
                }
                for (int j = 0; j>-10001; j-=1000){
                    twoValue.add(i);
                    twoValue.add(j);
                    if (!liste.contains(twoValue)){
                        liste.add(twoValue);
                        player.sendMessage(String.valueOf(i) +" "+ String.valueOf(j));
                    }
                    twoValue = new ArrayList<>();
                }
            }*/
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

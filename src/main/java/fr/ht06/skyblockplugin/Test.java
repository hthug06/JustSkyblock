package fr.ht06.skyblockplugin;

import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

    IslandManager islandManager  = SkyblockPlugin.islandManager;
    MiniMessage miniMessage  =  MiniMessage.miniMessage();
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
            //player.sendMessage(String.valueOf(islandManager.getIslandbyplayer(player.getName()).IStoMap()));
            List<List<Integer>> liste = new ArrayList<>();
            List<Integer> twoValue = new ArrayList<>();
            int enHaut = 1;
            int aDroite = 0;
            int enBas = 1;
            int aGauche = 0;
            for (int i = 0; i<=2; i++){
                player.sendMessage(miniMessage.deserialize("<b><i><color:red>"+i));
                for (int x = -enHaut*1000; x<=enHaut*1000; x+=1000){
                    for (int z = -enHaut*1000; z<=enHaut*1000; z+=1000){
                        twoValue.add(x);
                        twoValue.add(z);
                        if (!liste.contains(twoValue)){
                            player.sendMessage(twoValue.toString());
                            liste.add(twoValue);
                        }
                        twoValue = new ArrayList<>();
                    }
                    twoValue = new ArrayList<>();
                }
                enHaut+=1;
                for (int x = -aDroite*1000; x<=aDroite*1000; x+=1000){
                    for (int z = -aDroite*1000; z<=aDroite*1000; z+=1000){
                        twoValue.add(x);
                        twoValue.add(z);
                        if (!liste.contains(twoValue)){
                            player.sendMessage(twoValue.toString());
                            liste.add(twoValue);
                        }
                        twoValue = new ArrayList<>();
                    }
                    twoValue = new ArrayList<>();
                }
                aDroite+=1;
                for (int x = -enBas*1000; x<=enBas*1000; x+=1000){
                    for (int z = -enBas*1000; z<=enBas*1000; z+=1000){
                        twoValue.add(x);
                        twoValue.add(z);
                        if (!liste.contains(twoValue)){
                            player.sendMessage(twoValue.toString());
                            liste.add(twoValue);
                        }
                        twoValue = new ArrayList<>();
                    }
                    twoValue = new ArrayList<>();
                }
                enBas+=1;
                for (int x = -aGauche*1000; x<=aGauche*1000; x+=1000){
                    for (int z = -aGauche*1000; z<=aGauche*1000; z+=1000){
                        twoValue.add(x);
                        twoValue.add(z);
                        if (!liste.contains(twoValue)){
                            player.sendMessage(twoValue.toString());
                            liste.add(twoValue);
                        }
                        twoValue = new ArrayList<>();
                    }
                    twoValue = new ArrayList<>();
                }
                aGauche+=1;

            }
            player.sendMessage(liste.toString());
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

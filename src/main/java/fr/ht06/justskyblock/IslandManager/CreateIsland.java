package fr.ht06.justskyblock.IslandManager;

import fr.ht06.justskyblock.Config.DataConfig;
import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CreateIsland {

    public static void createAllIsland(){
        DataConfig.setup();
        IslandManager islandManager = JustSkyblock.islandManager;
        Island island;
        FileConfiguration dataconfig = DataConfig.get();
        if (DataConfig.get().contains("Island")) {
            for (String v : DataConfig.get().getConfigurationSection("Island").getKeys(false)) {
                //if we don't have the coordinates, we don't create the island
                if (!DataConfig.get().contains("Island."+ v +".LocationSpawn")) return;

                Location spawnLoc = new Location(Bukkit.getWorld("world_Skyblock"),
                        dataconfig.getDouble("Island."+ v +".LocationSpawn.x"),
                        dataconfig.getDouble("Island."+ v +".LocationSpawn.y"),
                        dataconfig.getDouble("Island."+ v +".LocationSpawn.z"),
                        dataconfig.getInt("Island."+ v +".LocationSpawn.pitch"),
                        dataconfig.getInt("Island."+ v +".LocationSpawn.yaw"));

                //creation of the island
                Location islandLoc = new Location(Bukkit.getWorld("world_Skyblock"), (int)dataconfig.getList("Island."+v+".Coordinates").get(0), 70, (int)dataconfig.getList("Island."+v+".Coordinates").get(1));
                island = new Island(v, islandLoc, spawnLoc);

                //Players
                if (!DataConfig.get().contains("Island."+ v +".Players.Owner")){
                    return;  //No owner = no island
                }
                else{
                    island.setOwner(UUID.fromString(DataConfig.get().getString("Island."+ v +".Players.Owner")));
                }

                List<UUID> modsUUID = new ArrayList<>();
                if (DataConfig.get().contains("Island."+ v +".Players.Moderators")){
                    for (String mods : DataConfig.get().getStringList("Island."+ v +".Players.Moderators")){
                        modsUUID.add(UUID.fromString(mods));
                    }
                    island.setModerator(modsUUID);
                }

                List<UUID> membersUUID = new ArrayList<>();
                if (DataConfig.get().contains("Island."+ v +".Players.Members")){
                    for (String members : DataConfig.get().getStringList("Island."+ v +".Players.Members")){
                        membersUUID.add(UUID.fromString(members));
                    }
                    island.setMember(membersUUID);
                }

                //les settings
                if (!DataConfig.get().contains("Island."+v+".Settings")) {
                    //If the yml don't contain settings, it's okay
                    @NotNull Map<String, Object> maps =  DataConfig.get().getConfigurationSection("Island."+v+".Settings").getValues(true);
                    for (Map.Entry<String, Object> m : maps.entrySet()){
                        island.setSettings(m.getKey(), (Boolean) m.getValue());
                    }
                }



                //le level de l'île
                if (DataConfig.get().contains("Island."+ v +".Level")) {
                    island.setLevel(dataconfig.getDouble("Island." + v + ".Level"));
                }
                else island.setLevel(0);

                //le rank de l'ile
                if (DataConfig.get().contains("Island."+ v +".Rank")) {
                    island.setRank(dataconfig.getInt("Island." + v + ".Rank"));
                }
                else island.setRank(0);

                //la taille de l'ile
                if (DataConfig.get().contains("Island."+ v +".Size")) {
                    island.setSize(dataconfig.getInt("Island." + v + ".Size"));
                }
                else island.setSize(0);

                //le Generator de l'ile
                if (DataConfig.get().contains("Island."+ v +".Generator")) {
                    island.setCobbleGenLevel(dataconfig.getInt("Island." + v + ".Generator"));
                    island.setCobbleGenLevelUnlock(dataconfig.getInt("Island." + v + ".Generator"));
                }
                else{
                    island.setCobbleGenLevelUnlock(1);
                    island.setCobbleGenLevel(1);
                }

                //date
                if (DataConfig.get().contains("Island." + v + ".CreationDate")) {
                    LocalDateTime Date = LocalDateTime.of(dataconfig.getInt("Island." + v + ".CreationDate.Year"),
                            dataconfig.getInt("Island." + v + ".CreationDate.Month"),
                            dataconfig.getInt("Island." + v + ".CreationDate.Day"),
                            dataconfig.getInt("Island." + v + ".CreationDate.Hour"),
                            dataconfig.getInt("Island." + v + ".CreationDate.Minute"));
                    island.setDate(Date);
                }
                //Else date is set in the island constructor


                if (DataConfig.get().contains("Island."+v+".Quests.Farming")) {
                    @NotNull Map<String, Object> FarmingQuest = DataConfig.get().getConfigurationSection("Island." + v + ".Quests.Farming").getValues(true);
                    for (Map.Entry<String, Object> m : FarmingQuest.entrySet()) {
                        island.setCropsCounter(m.getKey(), (int) m.getValue());
                    }
                }//else set in the constructor

                if (DataConfig.get().contains("Island."+v+".Quests.Mining")) {
                    @NotNull Map<String, Object> MiningQuest = DataConfig.get().getConfigurationSection("Island." + v + ".Quests.Mining").getValues(true);
                    for (Map.Entry<String, Object> m : MiningQuest.entrySet()) {
                        island.setMineralCounter(m.getKey(), (int) m.getValue());
                    }
                }//else set in the constructor

                if (DataConfig.get().contains("Island."+v+".Quests.Mining")) {
                    @NotNull Map<String, Object> LumberQuest = DataConfig.get().getConfigurationSection("Island." + v + ".Quests.Lumbering").getValues(true);
                    for (Map.Entry<String, Object> m : LumberQuest.entrySet()) {
                        island.setLumberCounter(m.getKey(), (int) m.getValue());
                    }
                }//else set in the constructor

                //ajout de l'île a l'island manager
                islandManager.addIsland(island);
            }
        }


    }

}

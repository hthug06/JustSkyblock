package fr.ht06.justskyblock.IslandManager;

import fr.ht06.justskyblock.Config.DataConfig;
import fr.ht06.justskyblock.JustSkyblock;

import java.util.*;

public class SaveIsland {

    public static void saveAllIsland(){

        IslandManager islandManager = JustSkyblock.islandManager;
        setupDataConfig();

        for (Island v: islandManager.getAllIsland()) {

            DataConfig.get().getConfigurationSection("Island").createSection(v.getIslandName());

            //Island Type
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).set("Type", v.getType());

            //le Level
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).set("Level", v.getLevel());

            //Ranks
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).set("Rank", v.getRank());

            //CustomgenLevel
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).set("Generator", v.getCobbleGenLevelUnlock());

            //les joueur de l'île
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).createSection("Players").set("Owner", v.getOwner().toString()); //le chef
            List<String> modUuidList = new ArrayList<>();
            if (!v.getAllModerators().isEmpty()) {
                for (UUID mods : v.getAllModerators()){
                    modUuidList.add(mods.toString());
                }
                DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".Players").set("Moderators", modUuidList); //les modos
            }
            List<String> membersUuidList = new ArrayList<>();
            if (!v.getAllMembers().isEmpty()){
                for (UUID member: v.getAllMembers()){
                    membersUuidList.add(member.toString());
                }
                DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".Players").set("Members", membersUuidList);  //les membres
            }

            //la location du spawn
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).createSection("LocationSpawn", v.getIslandSpawn().serialize());

            //Size
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).set("Size", v.getSize());

            //les 2 coordonnée de l'île (en x et en z)
            List<Integer> XandZ = Arrays.asList(v.getCoordinates().getBlockX(), v.getCoordinates().getBlockZ());
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).createSection("Coordinates");
            DataConfig.get().set("Island." + v.getIslandName() + ".Coordinates", XandZ);

            //Les settings
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).createSection("Settings", v.getAllSettings());

            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).createSection("CreationDate");
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".CreationDate").set("Year", v.getDate().getYear());
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".CreationDate").set("Month", v.getDate().getMonth().getValue());
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".CreationDate").set("Day", v.getDate().getDayOfMonth());
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".CreationDate").set("Hour", v.getDate().getHour());
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".CreationDate").set("Minute", v.getDate().getMinute());

            DataConfig.get().getConfigurationSection("Island." + v.getIslandName()).createSection("Quests");
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".Quests").set("Farming", v.getCropsCounter());
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".Quests").set("Mining", v.getMineralCounter());
            DataConfig.get().getConfigurationSection("Island." + v.getIslandName() + ".Quests").set("Lumbering", v.getLumberCounter());

        }
        DataConfig.save();
    }

    private static void setupDataConfig(){
        DataConfig.setup();
        DataConfig.get().addDefault("WARNING 1", "This is the data file, it's stock data while the server is offline");
        DataConfig.get().addDefault("WARNING 2", "Don't touch this file or everything can be corrupt");
        DataConfig.get().addDefault("WARNING 3", "If you delete this file, every player data will be reset");
        DataConfig.get().setInlineComments("WARNING 1", Collections.singletonList("test"));
        DataConfig.get().createSection("Island");
        DataConfig.get().options().copyDefaults(true);
        DataConfig.get().options().parseComments(true);
        DataConfig.save();
    }
}

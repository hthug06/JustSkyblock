package fr.ht06.skyblockplugin.Config;

import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IslandLevel {

    private static File file;
    private static FileConfiguration dataFile;

    //Finds or generates the custom config file
    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "level.yml");

        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){

            }
        }
        dataFile = YamlConfiguration.loadConfiguration(file);

    }

    public static FileConfiguration get(){
        return dataFile;
    }

    public static void save(){
        try{
            dataFile.save(file);
        }catch (IOException e){
            System.out.println("Couldn't save file");
        }
    }

    public static void reload(){
        dataFile = YamlConfiguration.loadConfiguration(file);
    }

    public static double calculateIslandLevel(Island island){
        double level = 0;
        Map<String, Integer> itemList = new HashMap<>();
        List<String > Flower = List.of("DANDELION","POPPY","BLUE_ORCHID","ALLIUM", "AZURE_BLUET", "TULIP"," OXEYE_DAISY", "CORNFLOWER", "LILY_OF_THE_VALLEY",
                "SUNFLOWER", "LILAC", "ROSE", "PEONY");

        for (int y = -64; y < 320/*couche max à couche min*/; y++) {
            Location isLoc1 = new Location(Bukkit.getWorld("world_Skyblock"), island.getIslandCoordinates().get(0), 70, island.getIslandCoordinates().get(1)).clone().add(-50, -200, -50);;
            int x1 = isLoc1.getBlockX();
            int z1 = isLoc1.getBlockZ();

            Location isLoc2 = new Location(Bukkit.getWorld("world_Skyblock"), island.getIslandCoordinates().get(0), 70, island.getIslandCoordinates().get(1)).clone().add(50, 300, 50);;
            int x2 = isLoc2.getBlockX();
            int z2 = isLoc2.getBlockZ();
            //x
            for (int x = x1; x < x2; x++) {
                //z
                for (int z = z1; z < z2; z++) {
                    if (!Bukkit.getWorld("world_Skyblock").getBlockAt(new Location(Bukkit.getWorld("world_Skyblock"), x, y, z)).getType().isAir()){
                        String itemName = Bukkit.getWorld("world_Skyblock").getBlockAt(new Location(Bukkit.getWorld("world_Skyblock"), x, y, z)).getType().name();
                        if (itemList.containsKey(itemName)){
                            itemList.compute(itemName, (k, nbr) -> nbr + 1);
                        }
                        else{
                            itemList.put(itemName, 1);
                        }

                        if (itemName.contains("SLAB")) level += IslandLevel.get().getInt("Slab");
                        if (itemName.contains("STAIRS")) level += IslandLevel.get().getInt("Stairs");
                        if (itemName.contains("DOOR")) level += IslandLevel.get().getInt("Doors");
                        if (itemName.contains("TRAPDOOR")) level += IslandLevel.get().getInt("Trapdoors");
                        if (itemName.contains("BUTTON")) level += IslandLevel.get().getInt("Button");
                        if (itemName.contains("WALL") || itemName.contains("FENCE")) level += IslandLevel.get().getInt("Wall/Fence/FenceGate");
                        if (itemName.contains("WOOL")) level += IslandLevel.get().getInt("Wool");
                        if (itemName.contains("CARPET")) level += IslandLevel.get().getInt("Carpet");
                        for (String s: Flower){
                            if (itemName.contains(s)) level += IslandLevel.get().getInt("Flower");
                            break;
                        }
                        if (itemName.contains("VINE")) level += IslandLevel.get().getInt("Vines");
                        if (itemName.contains("TERRACOTTA") && !itemName.contains("GLAZED_TERRACOTTA")) level += IslandLevel.get().getInt("Terracotta");
                        if (itemName.contains("GLAZED_TERRACOTTA")) level += IslandLevel.get().getInt("Glazed_Terracotta");
                        if (itemName.contains("GLASS")) level += IslandLevel.get().getInt("Glass");
                        if (itemName.contains("CONCRETE")) level += IslandLevel.get().getInt("Concrete");
                        if (itemName.contains("CORAL")) level += IslandLevel.get().getInt("Coral");
                        if (itemName.contains("RAIL")) level += IslandLevel.get().getInt("Rails");
                        if (itemName.contains("CANDLE")) level += IslandLevel.get().getInt("Candle");


                        for (String path : IslandLevel.get().getConfigurationSection("Material").getKeys(false)){
                            //SkyblockPlugin.getInstance().getLogger().info(path);
                            //SkyblockPlugin.getInstance().getLogger().info(String.valueOf(IslandLevel.get().getConfigurationSection("Material."+path)));
                            if (IslandLevel.get().getConfigurationSection("Material." +  path).getKeys(false).contains(itemName)){
                                //SkyblockPlugin.getInstance().getLogger().info(String.valueOf(x + y + z));
                                //SkyblockPlugin.getInstance().getLogger().info(itemName);
                                level += (int) IslandLevel.get().get("Material." + path + "." + itemName);
                            }
                        }
                    }

                }
            }
        }
        SkyblockPlugin.getInstance().getLogger().info(itemList.toString());
        return level;
    }


}

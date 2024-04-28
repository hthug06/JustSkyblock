package fr.ht06.skyblockplugin;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import fr.ht06.skyblockplugin.Commands.IslandCommand;
import fr.ht06.skyblockplugin.Commands.skyblockpluginCommand;
import fr.ht06.skyblockplugin.Config.DataConfig;
import fr.ht06.skyblockplugin.Events.InventoryEvents;
import fr.ht06.skyblockplugin.Events.PlayerListeners;
import fr.ht06.skyblockplugin.TabCompleter.IslandCommandTab;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SkyblockPlugin extends JavaPlugin {

    public Map<String, Boolean> hasIS = new HashMap<>();

    public Map<String, Location> IScoor = new HashMap<>();
    public List<List<Integer>> CoordsTaken = new ArrayList<>();
    public static WorldBorderApi worldBorderApi;
    public static SkyblockPlugin instance;
    public static String test ="ygvfshbk";
    public static IslandList islandList;


    @Override
    public void onEnable() {

        instance =this;

        //Creation du world_skyblock si il existe pas et le loader sinon
        String settings = "{\"structures\":{\"structures\":{}},\"layers\":[{\"height\":9,\"block\":\"air\"},{\"height\":1,\"block\":\"air\"}],\"lakes\":false,\"features\":false,\"biome\":\"plains\"}";
        WorldCreator worldcreator = new WorldCreator("world_Skyblock");
        worldcreator.type(WorldType.FLAT).type(WorldType.FLAT).generatorSettings(settings).generateStructures(false);
        worldcreator.createWorld();
        new WorldCreator("world_Skyblock").createWorld();

        //Pour les worldBorder
        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = getServer().getServicesManager().getRegistration(WorldBorderApi.class);
        if (worldBorderApiRegisteredServiceProvider == null) {
            getLogger().info("API not found");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        worldBorderApi = worldBorderApiRegisteredServiceProvider.getProvider();

        getCommand("is").setExecutor(new IslandCommand(this));
        getCommand("test").setExecutor(new Test(this));
        getCommand("skyblockplugin").setExecutor(new skyblockpluginCommand(this));
        getCommand("is").setTabCompleter(new IslandCommandTab(this));
        getServer().getPluginManager().registerEvents(new InventoryEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        saveDefaultConfig();


        List<String> IS = new ArrayList<>(getConfig().getConfigurationSection("IS.").getKeys(false));
        List<String> verificationSlot = new ArrayList<>();
        List<String> verificationName = new ArrayList<>();
        islandList = new IslandList();
        int i =0;

        for (String caractere: IS){
            islandList.createIsland(caractere);
            if (verificationSlot.contains(islandList.getIsland(caractere).get("Slot"))){
                getLogger().warning("The config isn't valid (you can't have the same slot on 2 items)");
                resetConfig();
                break;
            }
            else{
                verificationSlot.add(islandList.getIsland(caractere).get("Slot"));
            }
            //MArche pas ??
            if (verificationName.contains(IS.get(i))){
                getLogger().warning("The config isn't valid (you can't have 2 same id)");
                resetConfig();
                break;
            }
            else{
                verificationName.add(IS.get(i));
            }
            i++;
        };

        //Dossier pour les schematic
        File dossier = new File(getServer().getPluginsFolder().getAbsoluteFile() + "/SkyblockPlugin/Schematic/");

        if (!dossier.exists()){
            dossier.mkdir();
        }

        //reajout des gens dans la hashmap
        DataConfig.setup();
        for (String v: DataConfig.get().getConfigurationSection("Players").getKeys(false)){
            System.out.println(v);
            FileConfiguration dataconfig = DataConfig.get();
            Location loc = new Location(Bukkit.getWorld("world_Skyblock"),
                    dataconfig.getDouble("Players."+v+".x"),
                    dataconfig.getDouble("Players."+v+".y"),
                    dataconfig.getDouble("Players."+v+".z"),
                    dataconfig.getInt("Players."+v+".pitch"),
                    dataconfig.getInt("Players."+v+".yaw"));
            IScoor.put(v, loc);
            hasIS.put(v, true);
        }

        //On dégage le data.yml
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("SkyblockPlugin").getDataFolder(), "data.yml");
        file.delete();

    }

    @Override
    public void onDisable() {

        //DataConfig.get().addDefault("Message", "this is the default message");
        DataConfig.setup();
        DataConfig.get().addDefault("WARNING 1", "This is the data file, it's stock data while the server is offline");
        DataConfig.get().addDefault("WARNING 2", "Don't touch this file or everything can be corrupt");
        DataConfig.get().createSection("Players");
        DataConfig.get().options().copyDefaults(true);
        //DataConfig.get().createSection("Players");
        DataConfig.save();

        //List<String> configIS = new ArrayList<>(DataConfig.get().getConfigurationSection("Players").getKeys(false));

        for (Map.Entry<String, Location> v: IScoor.entrySet()){
            DataConfig.get().getConfigurationSection("Players").createSection(v.getKey(), v.getValue().serialize());
        }

        DataConfig.save();

    }

    public static SkyblockPlugin getInstance(){
        return instance;
    }

    public void resetConfig(){
        File configFile = new File(getDataFolder(), "config.yml");
        configFile.delete();
        saveDefaultConfig();
        reloadConfig();
    }
}

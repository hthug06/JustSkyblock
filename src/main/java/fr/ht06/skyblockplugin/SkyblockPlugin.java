package fr.ht06.skyblockplugin;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import fr.ht06.skyblockplugin.Commands.IslandCommand;
import fr.ht06.skyblockplugin.Commands.skyblockpluginCommand;
import fr.ht06.skyblockplugin.Events.InventoryEvents;
import fr.ht06.skyblockplugin.Events.PlayerListeners;
import fr.ht06.skyblockplugin.TabCompleter.IslandCommandTab;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SkyblockPlugin extends JavaPlugin {

    public Map<Player, Boolean> hasIS = new HashMap<>();

    public Map<Player, Location> IScoor = new HashMap<>();
    public List<List<Integer>> CoordsTaken = new ArrayList<>();
    public static WorldBorderApi worldBorderApi;
    public static SkyblockPlugin instance;
    public static String test ="ygvfshbk";
    public static IslandList islandList;


    @Override
    public void onEnable() {
        instance =this;

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

        //System.out.println(islandList.seeallIsland());
        //System.out.println(islandList.getIslandBySlot(1));

        //System.out.println(verificationSlot);
        //System.out.println(verificationName);

        //Dossier pour les schematic
        File dossier = new File(getServer().getPluginsFolder().getAbsoluteFile() + "/SkyblockPlugin/Schematic/");

        if (!dossier.exists()){
            dossier.mkdir();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

package fr.ht06.skyblockplugin;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import fr.ht06.skyblockplugin.Commands.IslandCommand;
import fr.ht06.skyblockplugin.Commands.skyblockpluginCommand;
import fr.ht06.skyblockplugin.Config.DataConfig;
import fr.ht06.skyblockplugin.Events.InventoryEvents;
import fr.ht06.skyblockplugin.Events.PlayerListeners;
import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.TabCompleter.IslandCommandTab;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class SkyblockPlugin extends JavaPlugin {

    public Map<String, Boolean> hasIS = new HashMap<>();

    public Map<String, Location> IScoor = new HashMap<>();
    public Map<String, List<Integer>> CoordsTaken = new HashMap<>();
    public static WorldBorderApi worldBorderApi;
    public static SkyblockPlugin instance;
    public static String test ="ygvfshbk";
    public static IslandListByYAML islandList;
    public static Map<String, Map<String, Boolean>> playerIslandSettings = new HashMap<>();
    public static Map<String, List<String>> onIsland = new HashMap<>();
    public static Map<String, Boolean> ItemSettingBool = new HashMap<>();
    public static Map<String, Component> itemSettingName = new HashMap<>();
    public static IslandManager islandManager;


    @Override
    public void onEnable() {
        instance =this;
        addSettingsItem();


        islandManager = new IslandManager();

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

        //Toute les commandes , event et TabCompleter
        getCommand("is").setExecutor(new IslandCommand(this));
        getCommand("test").setExecutor(new Test(this));
        getCommand("skyblockplugin").setExecutor(new skyblockpluginCommand(this));
        getCommand("is").setTabCompleter(new IslandCommandTab(this));
        getServer().getPluginManager().registerEvents(new InventoryEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        saveDefaultConfig();

        //Les iles depuis la config
        List<String> IS = new ArrayList<>(getConfig().getConfigurationSection("IS.").getKeys(false));
        List<String> verificationSlot = new ArrayList<>();
        List<String> verificationName = new ArrayList<>();
        islandList = new IslandListByYAML();
        int i =0;

        //verification si les îles sont valide (vérifiactions des slots)
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
            //verification si les îles sont valide (vérifiactions des id)

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
        if (DataConfig.get().contains("Island")) {
            Island island;
            for (String v : DataConfig.get().getConfigurationSection("Island").getKeys(false)) {
                FileConfiguration dataconfig = DataConfig.get();
                Location loc = new Location(Bukkit.getWorld("world_Skyblock"),
                        dataconfig.getDouble("Island."+ v +".LocationSpawn.x"),
                        dataconfig.getDouble("Island."+ v +".LocationSpawn.y"),
                        dataconfig.getDouble("Island."+ v +".LocationSpawn.z"),
                        dataconfig.getInt("Island."+ v +".LocationSpawn.pitch"),
                        dataconfig.getInt("Island."+ v +".LocationSpawn.yaw"));

                island = new Island(v, //nom de l'île
                        (String) dataconfig.get("Island."+v+".Owner"),  //proprio de lîle OK
                        (List<Integer>) dataconfig.get("Island."+v+".Coordinates"),  //les coo en x et z
                        loc);   //spawn de l'île

                for (String s : (List<String>) dataconfig.get("Island."+v+".Players")){
                    if (!island.getPlayers().contains(s)){
                        island.addPlayers(s);
                    }
                }

                islandManager.addIsland(island);
            }
        }
        if (DataConfig.get().contains("Coordinates")) {
            for (String v : DataConfig.get().getConfigurationSection("Coordinates").getKeys(false)) {
                CoordsTaken.put(v, (List<Integer>) DataConfig.get().get("Coordinates."+v));
            }
        }


        //On dégage le data.yml
        File dataYML = new File(Bukkit.getServer().getPluginManager().getPlugin("SkyblockPlugin").getDataFolder(), "data.yml");
        dataYML.delete();

    }

    @Override
    public void onDisable() {

        //DataConfig.get().addDefault("Message", "this is the default message");
        DataConfig.setup();
        DataConfig.get().addDefault("WARNING 1", "This is the data file, it's stock data while the server is offline");
        DataConfig.get().addDefault("WARNING 2", "Don't touch this file or everything can be corrupt");
        DataConfig.get().addDefault("WARNING 3", "If you delete this file, every player data will be reset");
        DataConfig.get().setInlineComments("WARNING 1", Collections.singletonList("test"));
        DataConfig.get().createSection("Island");
        DataConfig.get().options().copyDefaults(true);
        //DataConfig.get().createSection("Players");
        DataConfig.save();

        //List<String> configIS = new ArrayList<>(DataConfig.get().getConfigurationSection("Players").getKeys(false));

        for (Island v: islandManager.getAllIsland()){
            //la location du spawn
            DataConfig.get().getConfigurationSection("Island")
                    .createSection(v.getIslandName()).createSection("LocationSpawn", v.getIslandSpawn().serialize());

            //le chef de l'ile
            DataConfig.get().set("Island."+v.getIslandName()+".Owner", v.getPlayerOwnerName());

            //les joueur de l'île
            Map<String, List<String >> isPlayer = new HashMap<>();
            isPlayer.put(v.getIslandName(), v.getPlayers());
            DataConfig.get().getConfigurationSection("Island."+v.getIslandName()).createSection("Players");
            DataConfig.get().set("Island."+v.getIslandName()+".Players", v.getPlayers());

            //les 2 coordonnée de l'île (en x et en z)
            DataConfig.get().getConfigurationSection("Island."+v.getIslandName()).createSection("Coordinates");
            DataConfig.get().set("Island."+v.getIslandName()+".Coordinates", v.getIslandCoordinates());}
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

    public static ItemStack setTrue(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> liste = new ArrayList<>();
        liste.add(Component.text("True").color(TextColor.color(0x2FCC33)).decoration(TextDecoration.ITALIC,false).decorate(TextDecoration.BOLD));
        liste.add(Component.text("False").color(TextColor.color(0x898F86)).decoration(TextDecoration.ITALIC,false));
        itemMeta.lore(liste);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack setFalse(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> liste = new ArrayList<>();
        liste.add(Component.text("True").color(TextColor.color(0x898F86)).decoration(TextDecoration.ITALIC,false));
        liste.add(Component.text("False").color(TextColor.color(0xCC322A)).decoration(TextDecoration.ITALIC,false).decorate(TextDecoration.BOLD));
        itemMeta.lore(liste);
        item.setItemMeta(itemMeta);
        return item;
    }

    private void addSettingsItem() {
        SkyblockPlugin.ItemSettingBool.put("OAK_DOOR", false);
        SkyblockPlugin.ItemSettingBool.put("CHEST", false);
        SkyblockPlugin.ItemSettingBool.put("OAK_PRESSURE_PLATE", false);
        SkyblockPlugin.ItemSettingBool.put("OAK_BUTTON", false);
        SkyblockPlugin.itemSettingName.put("OAK_DOOR", Component.text("Allow player to use doors").decoration(TextDecoration.ITALIC, false));
        SkyblockPlugin.itemSettingName.put("CHEST", Component.text("Allow player to use chest").decoration(TextDecoration.ITALIC, false));
        SkyblockPlugin.itemSettingName.put("OAK_PRESSURE_PLATE", Component.text("Allow player to use pressures plates").decoration(TextDecoration.ITALIC, false));
        SkyblockPlugin.itemSettingName.put("OAK_BUTTON", Component.text("Allow player to use buttons").decoration(TextDecoration.ITALIC, false));
    }


    public static void deleteIsland(Player player){
        Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
        Bukkit.dispatchCommand(player, "spawn");
        //y
        for (int y = -64; y < 320/*couche max à couche min*/; y++) {
            Location isLoc1 = island.getIslandSpawn().clone().add(-50, -200, -50);
            int x1 = isLoc1.getBlockX();
            int z1 = isLoc1.getBlockZ();

            Location isLoc2 = island.getIslandSpawn().clone().add(50, 300, 50);
            int x2 = isLoc2.getBlockX();
            int z2 = isLoc2.getBlockZ();
            //x
            for (int x = x1; x < x2; x++) {
                //z
                for (int z = z1; z < z2; z++) {
                    if (!Bukkit.getWorld("world_Skyblock").getBlockAt(new Location(Bukkit.getWorld("world_Skyblock"), x, y, z)).getType().isAir())
                        Bukkit.getWorld("world_Skyblock").getBlockAt(new Location(Bukkit.getWorld("world_Skyblock"), x, y, z)).setType(Material.AIR);
                }
            }
        }
        islandManager.deleteIsland(island.getIslandName());
        //SkyblockPlugin.islandCoordinates.deleteCoordinates(island.getIslandName());
        player.getInventory().clear();
    }
}

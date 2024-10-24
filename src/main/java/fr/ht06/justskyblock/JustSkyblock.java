package fr.ht06.justskyblock;

import fr.ht06.justskyblock.Commands.IsAdminCommand;
import fr.ht06.justskyblock.Commands.IslandCommand;
import fr.ht06.justskyblock.Config.BlockPlacedByPlayerConfig;
import fr.ht06.justskyblock.Config.IslandLevel;
import fr.ht06.justskyblock.Events.CobbleGenEvent;
import fr.ht06.justskyblock.Events.InventoryEvents;
import fr.ht06.justskyblock.Events.PlayerListeners;
import fr.ht06.justskyblock.Inventory.CreateIslandInventory;
import fr.ht06.justskyblock.Inventory.DeleteIslandInventoryAdmin;
import fr.ht06.justskyblock.Inventory.Quest.*;
import fr.ht06.justskyblock.Inventory.TradeInventory;
import fr.ht06.justskyblock.Inventory.upgrade.CustomCobbleGenUpgrade;
import fr.ht06.justskyblock.Inventory.upgrade.IslandSizeUpgrade;
import fr.ht06.justskyblock.Inventory.upgrade.UpgradeGenLvl;
import fr.ht06.justskyblock.Inventory.upgrade.UpgradeMain;
import fr.ht06.justskyblock.IslandManager.CreateIsland;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.IslandManager.SaveIsland;
import fr.ht06.justskyblock.TabCompleter.IsadminCommandTab;
import fr.ht06.justskyblock.TabCompleter.IslandCommandTab;
import fr.ht06.justskyblock.placeholder.IslandLevelPlaceholder;
import fr.ht06.justskyblock.recipe.ChainMailArmorRecipe;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class JustSkyblock extends JavaPlugin {

    //Color of the plugin : Green ->#52BE80   Blue -> #5499C7

    public static IslandManager islandManager;
    public static YamlConfiguration levelConfig;
    public static YamlConfiguration tradeConfig;
    public static YamlConfiguration customGeneratorConfig;
    public static List<Location> placeByPlayer;


    @Override
    public void onEnable() {

        //Need to create config (for the first start) or get error
        //For the config.yml
        saveDefaultConfig();

        //Island From the config
        islandManager = new IslandManager();
        islandManager.createAllIslandByConfigYAML();

        placeByPlayer = new ArrayList<>();


        //Creation du world_skyblock si il existe pas et le loader sinon
        String settings = "{\"structures\":{\"structures\":{}},\"layers\":[{\"height\":9,\"block\":\"air\"},{\"height\":1,\"block\":\"air\"}],\"lakes\":false,\"features\":false,\"biome\":\"plains\"}";
        WorldCreator worldcreator = new WorldCreator(getWorldName());
        worldcreator.type(WorldType.FLAT).generatorSettings(settings).generateStructures(false);
        worldcreator.createWorld();
        World world = new WorldCreator(getWorldName()).createWorld();
        world.setDifficulty(Difficulty.NORMAL);

        //metrics /Bstats
        Metrics metrics = new Metrics(this, 22941);

        //All the commands (not the subCommand like /is visit)
        getCommand("is").setExecutor(new IslandCommand(this));
        getCommand("test").setExecutor(new Test(this));
        getCommand("isadmin").setExecutor(new IsAdminCommand());

        //All the tabCompleter
        getCommand("is").setTabCompleter(new IslandCommandTab(this));
        getCommand("isadmin").setTabCompleter(new IsadminCommandTab());

        //for all the events
        registerEvents();

        //For the placeholder Like %justskyblock_level%
        registerPlaceholder();

        //CustomCraft
        ChainMailArmorRecipe chainMailArmorRecipe = new ChainMailArmorRecipe();
        chainMailArmorRecipe.createCraft();

        //Import du schematic
        //saveResource("Schematic/IslandPlains.schem", false);

        //Folder For the .schem
        File dossier = new File(getServer().getPluginsFolder(), "/JustSkyblock/Schematic/");
        if (!dossier.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dossier.mkdir();
        }

        //Create all the island
        CreateIsland.createAllIsland();

        //Pour les block placé par les joueur (block spécifique)
        BlockPlacedByPlayerConfig.setup();
        if (BlockPlacedByPlayerConfig.get().contains("placed")){
            placeByPlayer.addAll((List<Location>) BlockPlacedByPlayerConfig.get().getList("placed"));
        }
        else{
            BlockPlacedByPlayerConfig.get().createSection("placed");
        }


        //On dégage la data.yml
        File dataYML = new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "data.yml");
        dataYML.delete();

        //Pour les level
        createLevelConfig();
        IslandLevel.save();

        //For the customGenerator
        createCustomGeneratorConfig();

        //for the trade
        createTradeConfig();

        //for all the schematic
        getAllBaseSchematic();

    }

    @Override
    public void onDisable() {
        SaveIsland.saveAllIsland();

        //Save the BlockPlacedByPlayer (I need to put a persistantData to important Block later)
        BlockPlacedByPlayerConfig.setup();
        BlockPlacedByPlayerConfig.get().createSection("placed");
        BlockPlacedByPlayerConfig.get().set("placed", placeByPlayer);

        BlockPlacedByPlayerConfig.save();
    }

    public static JustSkyblock getInstance(){
        return getPlugin(JustSkyblock.class);
    }

    private void getAllBaseSchematic() {
        List<String> schematicName = new ArrayList<>(List.of("BasicIsland", "IslandDesert", "IslandMesa", "IslandPlains", "IslandSnow"));

        for (String name: schematicName){
            if (!new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "Schematic/" + name + ".schem").exists()){
                super.saveResource("Schematic/"+ name +".schem", false /* don't replace the file on disk if it exists */);
            }


            File file = new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "Schematic/"+ name +".schem");
            file.renameTo(new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "Schematic/"+ name +".schem"));
        }

    }

    public void resetConfig(){
        File configFile = new File(getDataFolder(), "config.yml");
        configFile.delete();
        saveDefaultConfig();
        reloadConfig();
    }

    public void createLevelConfig(){
        if (!new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "level.yml").exists()){
            super.saveResource("level.yml", false /* don't replace the file on disk if it exists */);
        }
        //recup le dossier .yml dans les ressources
        //Pourvoir l'utiliser
        levelConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "level.yml"));
        //Utiliser cette class pour simplifier
        IslandLevel.setup();
    }

    public void createCustomGeneratorConfig(){
        if (!new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "customgenerator.yml").exists()){
            super.saveResource("customgenerator.yml", false /* don't replace the file on disk if it exists */);
        }
        //recup le dossier .yml dans les ressources
        //Pourvoir l'utiliser
        customGeneratorConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "customgenerator.yml"));
        //Utiliser cette class pour simplifier
    }

    public void createTradeConfig(){
        if (!new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "trade.yml").exists()){
            super.saveResource("trade.yml", false /* don't replace the file on disk if it exists */);
        }
        //recup le dossier .yml dans les ressources
        //Pourvoir l'utiliser
        tradeConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "trade.yml"));
        //Utiliser cette class pour simplifier
    }

    private void registerPlaceholder(){
        new IslandLevelPlaceholder().register();
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        getServer().getPluginManager().registerEvents(new CreateIslandInventory(), this);
        getServer().getPluginManager().registerEvents(new CobbleGenEvent(), this);
        getServer().getPluginManager().registerEvents(new MainQuestInventory(), this);
        getServer().getPluginManager().registerEvents(new RankQuestInventory(), this);
        getServer().getPluginManager().registerEvents(new FarmingQuest(), this);
        getServer().getPluginManager().registerEvents(new FarmingQuest2(), this);
        getServer().getPluginManager().registerEvents(new MiningQuest(), this);
        getServer().getPluginManager().registerEvents(new MiningQuest2(), this);
        getServer().getPluginManager().registerEvents(new LumberQuest(), this);
        getServer().getPluginManager().registerEvents(new LumberQuest2(), this);
        getServer().getPluginManager().registerEvents(new UpgradeMain(), this);
        getServer().getPluginManager().registerEvents(new CustomCobbleGenUpgrade(), this);
        getServer().getPluginManager().registerEvents(new UpgradeGenLvl(), this);
        getServer().getPluginManager().registerEvents(new IslandSizeUpgrade(), this);
        getServer().getPluginManager().registerEvents(new DeleteIslandInventoryAdmin(), this);
        getServer().getPluginManager().registerEvents(new TradeInventory(), this);
    }

    public String getWorldName(){
        if (JustSkyblock.getInstance().getConfig().getString("WorldName") != null) {
            return JustSkyblock.getInstance().getConfig().getString("WorldName");
        }
        else{
            return "world_Skyblock";
        }
    }

    public static void  reloadAllConfig(){
        JustSkyblock.getInstance().reloadConfig();
        JustSkyblock.islandManager.createAllIslandByConfigYAML();
        tradeConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "trade.yml"));
        customGeneratorConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "customgenerator.yml"));
    }
}


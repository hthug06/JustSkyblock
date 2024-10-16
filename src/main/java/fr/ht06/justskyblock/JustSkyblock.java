package fr.ht06.justskyblock;

import fr.ht06.justskyblock.Commands.IsAdminCommand;
import fr.ht06.justskyblock.Commands.IslandCommand;
import fr.ht06.justskyblock.Config.BlockPlacedByPlayerConfig;
import fr.ht06.justskyblock.Config.DataConfig;
import fr.ht06.justskyblock.Config.IslandLevel;
import fr.ht06.justskyblock.Events.CobbleGenEvent;
import fr.ht06.justskyblock.Events.InventoryEvents;
import fr.ht06.justskyblock.Events.PlayerListeners;
import fr.ht06.justskyblock.Inventory.Quest.*;
import fr.ht06.justskyblock.Inventory.upgrade.CustomCobbleGenUpgrade;
import fr.ht06.justskyblock.Inventory.upgrade.IslandSizeUpgrade;
import fr.ht06.justskyblock.Inventory.upgrade.UpgradeGenLvl;
import fr.ht06.justskyblock.Inventory.upgrade.UpgradeMain;
import fr.ht06.justskyblock.IslandManager.CreateIsland;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.IslandManager.SaveIsland;
import fr.ht06.justskyblock.TabCompleter.IsadminCommandTab;
import fr.ht06.justskyblock.TabCompleter.IslandCommandTab;
import fr.ht06.justskyblock.placeholder.IslandLevelPlaceholder;
import fr.ht06.justskyblock.recipe.ChainMailArmorRecipe;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

public final class JustSkyblock extends JavaPlugin {

    //Color of the plugin : Green ->#52BE80   Blue -> #5499C7

    public static IslandListByYAML islandList;
    public static IslandManager islandManager;
    public static YamlConfiguration customConfig;
    public static List<Location> placeByPlayer;


    @Override
    public void onEnable() {
        islandManager = new IslandManager();
        placeByPlayer = new ArrayList<>();


        //Creation du world_skyblock si il existe pas et le loader sinon
        String settings = "{\"structures\":{\"structures\":{}},\"layers\":[{\"height\":9,\"block\":\"air\"},{\"height\":1,\"block\":\"air\"}],\"lakes\":false,\"features\":false,\"biome\":\"plains\"}";
        WorldCreator worldcreator = new WorldCreator("world_Skyblock");
        worldcreator.type(WorldType.FLAT).generatorSettings(settings).generateStructures(false);
        worldcreator.createWorld();
        new WorldCreator("world_Skyblock").createWorld();

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

        //For the config.yml
        saveDefaultConfig();

        //For the placeholder Like %justskyblock_level%
        registerPlaceholder();

        //CustomCraft
        ChainMailArmorRecipe chainMailArmorRecipe = new ChainMailArmorRecipe();
        chainMailArmorRecipe.createCraft();

        //Island From the config (maybe change for later)
        List<String> IS = new ArrayList<>(getConfig().getConfigurationSection("IS.").getKeys(false));
        List<String> verificationSlot = new ArrayList<>();
        List<String> verificationName = new ArrayList<>();
        islandList = new IslandListByYAML();
        int i =0;

        //Import du schematic
        //saveResource("Schematic/IslandPlains.schem", false);


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
        customConfig = YamlConfiguration.loadConfiguration(new File(Bukkit.getServer().getPluginManager().getPlugin("JustSkyblock").getDataFolder(), "level.yml"));
        //Utiliser cette class pour simplifier
        IslandLevel.setup();
    }

    private void registerPlaceholder(){
        new IslandLevelPlaceholder().register();
    }

    private void registerEvents(){
        getServer().getPluginManager().registerEvents(new InventoryEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
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
    }
}


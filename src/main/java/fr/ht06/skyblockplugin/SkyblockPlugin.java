package fr.ht06.skyblockplugin;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import fr.ht06.skyblockplugin.Commands.CreateWorld;
import fr.ht06.skyblockplugin.Commands.IslandCommand;
import fr.ht06.skyblockplugin.Events.InventoryEvents;

import fr.ht06.skyblockplugin.Events.PlayerListeners;
import fr.ht06.skyblockplugin.TabCompleter.IslandCommandTab;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SkyblockPlugin extends JavaPlugin {

    public Map<Player, Boolean> hasIS = new HashMap<>();

    public Map<Player, Location> IScoor = new HashMap<>();
    public List<List<Integer>> CoordsTaken = new ArrayList<>();
    public static WorldBorderApi worldBorderApi;




    @Override
    public void onEnable() {
        //Pour les worldBorder
        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = getServer().getServicesManager().getRegistration(WorldBorderApi.class);
        if (worldBorderApiRegisteredServiceProvider == null) {
            getLogger().info("API not found");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        worldBorderApi = worldBorderApiRegisteredServiceProvider.getProvider();

        getCommand("load").setExecutor(this);
        getCommand("createworld").setExecutor(new CreateWorld(this));
        getCommand("is").setExecutor(new IslandCommand(this));
        getCommand("is").setTabCompleter(new IslandCommandTab(this));
        getServer().getPluginManager().registerEvents(new InventoryEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        getCommand("test").setExecutor(new CommandTest(this));
        saveDefaultConfig();
        

        File dossier = new File(getServer().getPluginsFolder().getAbsoluteFile() + "/SkyblockPlugin/Schematic/");

        if (!dossier.exists()){
            dossier.mkdir();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //Player player = (Player) sender;

        //LOADING
        ClipboardFormat format = ClipboardFormats.findByFile(new File(getServer().getPluginsFolder().getAbsoluteFile() + "/SkyblockPlugin/Schematic/" + args[0]+".schem"));
        try (ClipboardReader reader = format.getReader(new FileInputStream(new File(getServer().getPluginsFolder().getAbsoluteFile() + "/SkyblockPlugin/Schematic/"+ args[0]+".schem")));
             EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(Bukkit.getWorld(args[4])), -1)
        ) {
            Clipboard clipboard = reader.read();

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3])))
                    .ignoreAirBlocks(false)
                    .build();
            Operations.complete(operation);
        } catch (IOException | WorldEditException e) {
            throw new RuntimeException(e);
        }

        sender.sendMessage(ChatColor.GREEN + "Schematic has been loaded.");

        return true;
    }
}

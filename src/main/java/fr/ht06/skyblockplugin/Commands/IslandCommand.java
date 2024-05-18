package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.Inventory.DeleteIslandInventory;
import fr.ht06.skyblockplugin.Inventory.IslandInventory;
import fr.ht06.skyblockplugin.Inventory.IslandSettingsInv;
import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IslandCommand implements CommandExecutor {
    SkyblockPlugin main;
    MiniMessage miniMessage = MiniMessage.miniMessage();
    public IslandCommand(SkyblockPlugin main) {
        this.main  = main;
    }
    IslandManager islandManager = SkyblockPlugin.islandManager;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (args.length == 0){
            if (!islandManager.playerHasIsland(player.getName())){
                IslandInventory gui = new IslandInventory();
                player.openInventory(gui.getInventory());

            }
            else {
                Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                player.teleport(islandManager.getIslandbyplayer(player.getName()).getIslandSpawn());
                SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(Bukkit.getWorld("world_Skyblock"),
                        island.getIslandSpawn().getBlockX(),
                        island.getIslandSpawn().getBlockY(),
                        island.getIslandSpawn().getBlockZ()));
                player.sendMessage("§aTeleportation to the Island");
            }
        }

        if (args.length == 1){
            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }

            if (args[0].equalsIgnoreCase("delete")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    DeleteIslandInventory deleteIslandInventory = new  DeleteIslandInventory();
                    player.openInventory(deleteIslandInventory.getInventory());
                }

            }

            if (args[0].equalsIgnoreCase("visit")){
                player.sendMessage("§c/is visit <player>");
            }

            if (args[0].equalsIgnoreCase("settings")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    IslandSettingsInv islandSettingsInv = new IslandSettingsInv(player);
                    player.openInventory(islandSettingsInv.getInventory());
                }
            }
            if (args[0].equalsIgnoreCase("team")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                    return true;
                }
                else{

                }
                player.sendMessage("ça va être les infos");
                if (args[1].equalsIgnoreCase("add")){
                }
            }


        }

        if (args.length == 2){

            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }



            if (args[0].equalsIgnoreCase("delete")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    DeleteIslandInventory deleteIslandInventory = new  DeleteIslandInventory();
                    player.openInventory(deleteIslandInventory.getInventory());
                }
            }

            if (args[0].equalsIgnoreCase("visit")){
                if (islandManager.playerHasIsland(args[1])){
                    player.teleport(islandManager.getIslandbyplayer(args[1]).getIslandSpawn());
                    player.sendMessage("§aTeleportation to "+args[1]+"'s Island");
                    SkyblockPlugin.worldBorderApi.setBorder(player, 100, islandManager.getIslandbyplayer(args[1]).getIslandSpawn());

                }
                else{
                    player.sendMessage("§cThis player doesn't have an island or doesn't exist.");
                }
            }

            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }


            if (args[0].equalsIgnoreCase("settings")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    IslandSettingsInv islandSettingsInv = new IslandSettingsInv(player);
                    player.openInventory(islandSettingsInv.getInventory());
                }
            }

        }
        if (args.length > 2){

            if (args[0].equalsIgnoreCase("setspawn")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else {
                    Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
                    //Si il n'y a rien en dessous
                    Location ploc = player.getLocation();
                    Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY() - 1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                    if (behindplayer.getBlock().getType().isAir()) {
                        player.sendMessage("§cSelect a valid spot for the spawn");
                    }
                    else {
                        player.sendMessage("Island spawn has changed");
                        island.setIslandSpawn(player.getLocation());
                    }
                }
            }

            if (args[0].equalsIgnoreCase("visit")){
                player.sendMessage("§c/visit <player>");
            }

            if (args[0].equalsIgnoreCase("delete")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    DeleteIslandInventory deleteIslandInventory = new  DeleteIslandInventory();
                    player.openInventory(deleteIslandInventory.getInventory());
                }

            }

            if (args[0].equalsIgnoreCase("settings")){
                if (!islandManager.playerHasIsland(player.getName())){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    IslandSettingsInv islandSettingsInv = new IslandSettingsInv(player);
                    player.openInventory(islandSettingsInv.getInventory());
                }
            }
        }

        return true;
    }
}

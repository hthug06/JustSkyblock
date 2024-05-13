package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.Inventory.IslandInventory;
import fr.ht06.skyblockplugin.Inventory.IslandSettingsInv;
import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
                    player.sendMessage(Component.text("Are you sure you want to delete your island?").color(TextColor.color(0xE74C3C)));
                    player.sendMessage(Component.text("You are going to lost all your progression.").color(TextColor.color(0xE74C3C)));
                    player.sendMessage(Component.text("If you really want to delete your island, ").color(TextColor.color(0xE74C3C)).append(miniMessage.deserialize("<click:run_command:/is delete confirm>Click here.</click>").asComponent().color(TextColor.color(0x7B241C))));
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
                if (args[1].equalsIgnoreCase("confirm")) {
                    if (!islandManager.playerHasIsland(player.getName())){
                        player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                    }
                    else {
                        SkyblockPlugin.deleteIsland(player);
                    }
                }
                else{
                    if (!islandManager.playerHasIsland(player.getName())){
                        player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                    }
                    else{
                        player.sendMessage(Component.text("Are you sure you want to delete your island?").color(TextColor.color(0xE74C3C)));
                        player.sendMessage(Component.text("You are going to lost all your progression.").color(TextColor.color(0xE74C3C)));
                        player.sendMessage(Component.text("If you really want to delete your island, ").color(TextColor.color(0xE74C3C)).append(miniMessage.deserialize("<click:run_command:/is delete confirm>Click here.</click>").asComponent().color(TextColor.color(0x7B241C))));
                    }
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
                    player.sendMessage(Component.text("Are you sure you want to delete your island?").color(TextColor.color(0xE74C3C)));
                    player.sendMessage(Component.text("You are going to lost all your progression.").color(TextColor.color(0xE74C3C)));
                    player.sendMessage(Component.text("If you really want to delete your island, ").color(TextColor.color(0xE74C3C)).append(miniMessage.deserialize("<click:run_command:/is delete confirm>Click here.</click>").asComponent().color(TextColor.color(0x7B241C))));
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

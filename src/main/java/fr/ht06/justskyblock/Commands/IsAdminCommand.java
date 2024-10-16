package fr.ht06.justskyblock.Commands;

import fr.ht06.justskyblock.Config.IslandLevel;
import fr.ht06.justskyblock.Inventory.IslandInfoInventory;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class IsAdminCommand implements CommandExecutor {

    IslandManager islandManager  = JustSkyblock.islandManager;
    MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        //Tout pour si la commande est utlisé en console
        if (!(sender instanceof Player)){
            if (args.length == 0){
                sender.sendMessage("--- Island Admin Help (Console version) ---");
                sender.sendMessage("IslandAdmin help: All subcommand and some indication about the IslandAdmin command");
                sender.sendMessage("IslandAdmin info player <player>: See info of the player's island");
                sender.sendMessage("IslandAdmin info island <island>: See info about this island");
            }
            if (args.length >= 1){
                if (args.length == 1) {
                    sender.sendMessage("IslandAdmin info player <player>");
                    sender.sendMessage("IslandAdmin info island <island>");
                    return true;
                }

                if(args[0].equalsIgnoreCase("info")){

                    if (args.length == 2) {
                        sender.sendMessage("IslandAdmin info player <player>");
                        sender.sendMessage("IslandAdmin info island <island>");
                        return true;
                    }


                    if (!(args[1].equalsIgnoreCase("player") || args[1].equalsIgnoreCase("island"))){
                        sender.sendMessage("IslandAdmin info player <player>");
                        sender.sendMessage("IslandAdmin info island <island>");
                        return true;
                    }



                    if (args[1].equalsIgnoreCase("player")){

                        if (islandManager.playerHasIsland(args[2])){
                            Island island = islandManager.getIslandbyplayer(args[2]);
                            sender.sendMessage("Island name : " + island.getIslandName());
                            sender.sendMessage("Island level  : " + island.getLevel());
                            sender.sendMessage("Island rank : " +island.getRank());
                            sender.sendMessage("Island size : " + island.getSize());
                            sender.sendMessage("Owner : " + island.getOwner());
                            sender.sendMessage("Moderator : " + island.getAllModerators());
                            sender.sendMessage("Member : " + island.getAllMembers());
                            sender.sendMessage("Creation Date : " + island.getDate());
                        }
                        else{
                            sender.sendMessage("This player didn't has an island");
                        }
                    }

                    if (args[1].equalsIgnoreCase("island")){

                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            stringBuilder.append(args[i]).append(" ");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        sender.sendMessage(stringBuilder.toString());
                        if (islandManager.IslandExist(stringBuilder.toString())){
                            Island island = islandManager.getIslandbyName(stringBuilder.toString());
                            sender.sendMessage("Island name : " + island.getIslandName());
                            sender.sendMessage("Island level  : " + island.getLevel());
                            sender.sendMessage("Island rank : " +island.getRank());
                            sender.sendMessage("Island size : " + island.getSize());
                            sender.sendMessage("Owner : " + island.getOwner());
                            sender.sendMessage("Moderator : " + island.getAllModerators());
                            sender.sendMessage("Member : " + island.getAllMembers());
                            sender.sendMessage("Creation Date : " + island.getDate());
                        }
                        else{
                            sender.sendMessage("This island didn't exist");
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("reload")){
                    JustSkyblock.getInstance().reloadConfig();
                    if (new File(Bukkit.getServer().getPluginManager().getPlugin("justSkyblock").getDataFolder(), "level.yml").exists()) {
                        IslandLevel.reload();
                    }
                    else{
                        JustSkyblock.getInstance().createLevelConfig();
                    }
                    sender.sendMessage("§cConfig reload");
                    return true;
                }
            }
        }
        else{
            Player player = (Player) sender;
            if (args.length == 0){
                commandHelp(args, player);
            }

            if (args.length>=1) {

                if (args[0].equalsIgnoreCase("allisland")) {
                    if (args.length == 1 || args[1].equalsIgnoreCase("1")) {
                        if (islandManager.getAllIsland().size()>10) {
                            player.sendMessage("--- list of island page 1 (total of "+ islandManager.getAllIsland().size()+ ") ---");
                            for (int i = 0; i < 10; i++) {
                                String islandName = islandManager.getAllIsland().get(i).getIslandName();
                                String ownerName =  Bukkit.getPlayer(islandManager.getAllIsland().get(i).getOwner()).getName();

                                Component msg = Component.text(ownerName + ": ")
                                        .append(Component.text(islandName).
                                                hoverEvent(HoverEvent.showText(Component.text("Click to copy island name to clickboard")))
                                                .clickEvent(ClickEvent.copyToClipboard(islandName)));
                                player.sendMessage(msg);
                            }

                            Component msgBarre = Component.text("---------------------")
                                    .append(Component.text(">")
                                            .hoverEvent(HoverEvent.showText(Component.text("Go to the next Page")))
                                            .clickEvent(ClickEvent.runCommand("/isa allisland 2")))
                                    .append(Component.text("-------"));
                            player.sendMessage(msgBarre);
                        }
                        else{
                            player.sendMessage("--- list of island page 1 ---");
                            for (int i = 0; i < islandManager.getAllIsland().size(); i++) {
                                String isName = islandManager.getAllIsland().get(i).getIslandName();
                                String ownerName =  Bukkit.getPlayer(islandManager.getAllIsland().get(i).getOwner()).getName();

                                Component msg = Component.text(ownerName + ": ")
                                        .append(Component.text(isName)
                                                .hoverEvent(HoverEvent.showText(Component.text("Click to copy island name to clickboard")))
                                                .clickEvent(ClickEvent.copyToClipboard(isName)));

                                player.sendMessage(msg);
                            }
                            player.sendMessage("----------------------------");
                        }
                    } else if (args.length == 2) {
                        int page = Integer.parseInt(args[1]);
                        player.sendMessage("--- list of island page " + page + " ---");


                        for (int i = (page - 1) * 10; i < page * 10; i++) {
                            if (i >= islandManager.getAllIsland().size()) break;
                            String isName = islandManager.getAllIsland().get(i).getIslandName();
                            String ownerName =  Bukkit.getPlayer(islandManager.getAllIsland().get(i).getOwner()).getName();
                            Component msg = Component.text(ownerName + ": ")
                                    .append(Component.text(isName)
                                            .hoverEvent(HoverEvent.showText(Component.text("Click to copy island name to clickboard")))
                                            .clickEvent(ClickEvent.copyToClipboard(isName)));
                            player.sendMessage(msg);
                        }

                        Component msgBarre = Component.text("---------------------")
                                .append(Component.text(">")
                                        .hoverEvent(HoverEvent.showText(Component.text("Go to the next Page")))
                                        .clickEvent(ClickEvent.runCommand("/isa allisland "+(page+1)))
                                .append(Component.text("-------")));
                        player.sendMessage(msgBarre);
                        player.sendMessage("");

                    }
                }

                if (args[0].equalsIgnoreCase("info")) {

                    if (args.length == 1){
                        player.sendMessage("§c/isadmin info player <player> or /isadmin info island <island>");
                        return true;
                    }
                    if (!(args[1].equalsIgnoreCase("player") || args[1].equalsIgnoreCase("island"))){
                        player.sendMessage("§c/isadmin info player <player> or /isadmin info island <island>");
                        return true;
                    }

                    if (args[1].equalsIgnoreCase("island")) {

                        if (args.length == 2){
                            player.sendMessage("§c/isadmin info player <player> or /isadmin info island <island>");
                            return true;
                        }

                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 2; i < args.length; i++) {
                            stringBuilder.append(args[i]).append(" ");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                        if (islandManager.IslandExist(stringBuilder.toString())){
                            Island island = islandManager.getIslandbyName(stringBuilder.toString());
                            IslandInfoInventory infoInventory = new IslandInfoInventory(island, player);
                            player.openInventory(infoInventory.getInventory());
                        }
                        else{
                            player.sendMessage("§cThis island doesn't exist");
                            return true;
                        }

                    }

                    if (args[1].equalsIgnoreCase("player")) {
                        if (args.length == 2){
                            player.sendMessage("§c/isadmin info player <player> or /isadmin info island <island>");
                            return true;
                        }
                        if (islandManager.playerHasIsland(args[2])) {
                            IslandInfoInventory infoInventory = new IslandInfoInventory(islandManager.getIslandbyplayer(args[2]).getIsland(), player);
                            player.openInventory(infoInventory.getInventory());
                        }
                        else{
                            player.sendMessage("§cThis player doesn't have an island");
                        }
                    }
                }

                if (args[0].equalsIgnoreCase("help")){
                    commandHelp(args, player);
                }

                if (args[0].equalsIgnoreCase("reload")){
                    JustSkyblock.getInstance().reloadConfig();
                    if (new File(Bukkit.getServer().getPluginManager().getPlugin("justSkyblock").getDataFolder(), "level.yml").exists()) {
                        IslandLevel.reload();
                    }
                    else{
                        JustSkyblock.getInstance().createLevelConfig();
                    }
                    sender.sendMessage("§cConfig reload");
                    return true;
                }
            }
        }
        return true;
    }


    private void commandHelp(String[] args, Player player){

        player.sendMessage(Component.text("     ----- ", TextColor.color(0x1E8449))
                .append(Component.text("Island Admin Help (Page 1)", TextColor.color(0x1F618D)))
                .append(Component.text(" -----", TextColor.color(0x1E8449))));  // --- Help Island ---

        player.sendMessage(Component.text("/islandAdmin allIsland: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandadmin allisland"))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("List every island with the name of the owner ", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("/islandAdmin help: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandadmin help"))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("All the command related to the /islandAdmin ", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("/islandAdmin info player <playername>: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandadmin info player "))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("See the info of an island by having the name of a player ", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("/islandAdmin info island <islandname>: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandadmin info island "))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("See the info of an island by having the name of an island", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("     ----------------------", TextColor.color(0x1E8449))
                //.append(Component.text(/*">>"*/"--", TextColor.color(0x1F618D))
                //.hoverEvent(HoverEvent.showText(Component.text("go to page 2", TextColor.color(0x1F618D))))
                //.clickEvent(ClickEvent.runCommand("/is help 2")))
                .append(Component.text("------", TextColor.color(0x1E8449))));

    }
}

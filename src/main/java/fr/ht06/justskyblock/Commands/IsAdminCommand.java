package fr.ht06.justskyblock.Commands;

import fr.ht06.justskyblock.Inventory.IslandInfoInventory;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickCallback;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.event.HoverEventSource;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

public class IsAdminCommand implements CommandExecutor {

    IslandManager islandManager  = JustSkyblock.islandManager;
    MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        //Tout pour si la commande est utlisé en console
        if (!(sender instanceof Player)){
            if (args.length == 0){
                sender.sendMessage("help menu (not finish LOL)");
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
                            player.sendMessage("--- list of island page 1 ---");
                            for (int i = 0; i < 10; i++) {
                                String isName = islandManager.getAllIsland().get(i).getIslandName();
                                String ownerName =  islandManager.getAllIsland().get(i).getOwner();

                                Component msg = Component.text(ownerName + ": ")
                                        .append(Component.text(isName).
                                                hoverEvent(HoverEvent.showText(Component.text("Click to copy island name to clickboard")))
                                                .clickEvent(ClickEvent.copyToClipboard(isName)));
                                player.sendMessage(msg);
                            }
                            player.sendMessage("----------------------------");
                        }
                        else{
                            player.sendMessage("--- list of island page 1 ---");
                            for (int i = 0; i < islandManager.getAllIsland().size(); i++) {
                                String isName = islandManager.getAllIsland().get(i).getIslandName();
                                String ownerName =  islandManager.getAllIsland().get(i).getOwner();

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
                            player.sendMessage(islandManager.getAllIsland().get(i).getOwner() + ": " + islandManager.getAllIsland().get(i).getIslandName());
                        }
                        player.sendMessage("----------------------------");
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
            }
        }
        return true;
    }


    private void commandHelp(String[] args, Player player){

        player.sendMessage(Component.text("     ----- ", TextColor.color(0x1E8449))
                .append(Component.text("Island Admin Help (Page 1)", TextColor.color(0x1F618D)))
                .append(Component.text(" -----", TextColor.color(0x1E8449))));  // --- Help Island ---

        player.sendMessage(Component.text("/islandAdmin allIsland: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandAdmin allisland"))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("List every island with the name of the owner ", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("/islandAdmin help: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandAdmin help"))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("All the command related to the /islandAdmin ", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("/islandAdmin info player <playername>: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandAdmin info player "))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("See the info of an island by having the name of a player ", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("/islandAdmin info island <islandname>: ", TextColor.color(0x1F618D))
                .clickEvent(ClickEvent.suggestCommand("/islandAdmin info island "))
                .hoverEvent(HoverEvent.showText(Component.text("Click to paste the command in the chat", TextColor.color(0x1F618D))))
                .append(Component.text("See the info of an island by having the name of an island", TextColor.color(0x1E8449))));

        player.sendMessage(Component.text("     ----------------------", TextColor.color(0x1E8449))
                //.append(Component.text(/*">>"*/"--", TextColor.color(0x1F618D))
                //.hoverEvent(HoverEvent.showText(Component.text("go to page 2", TextColor.color(0x1F618D))))
                //.clickEvent(ClickEvent.runCommand("/is help 2")))
                .append(Component.text("------", TextColor.color(0x1E8449))));

    }
}

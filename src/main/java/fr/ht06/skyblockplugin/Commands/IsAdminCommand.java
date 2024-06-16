package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IsAdminCommand implements CommandExecutor {

    IslandManager islandManager  = SkyblockPlugin.islandManager;

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
                player.sendMessage("toute les commandes");
            }

            if (args.length>=1){
                if (args[0].equalsIgnoreCase("allisland")){
                    if (args.length == 1 || args[1].equalsIgnoreCase("1")){
                        player.sendMessage("--- list of island page 1 ---");
                        for (int i = 0; i<10; i++) {
                            player.sendMessage(islandManager.getAllIsland().get(i).getOwner() + ": " + islandManager.getAllIsland().get(i).getIslandName());
                        }
                        player.sendMessage("----------------------------");
                    }
                    else if (args.length == 2) {
                        int page = Integer.parseInt(args[1]);
                        player.sendMessage("--- list of island page "+ page + " ---");
                        for (int i = (page-1)* 10; i < page*10; i++){
                            if (i >= islandManager.getAllIsland().size()) break;
                            player.sendMessage(islandManager.getAllIsland().get(i).getOwner() + ": " + islandManager.getAllIsland().get(i).getIslandName());
                        }
                        player.sendMessage("----------------------------");
                        player.sendMessage("");
                        
                    }
                }
            }

        }




        return false;
    }
}

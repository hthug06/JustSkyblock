package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.Inventory.IslandInventory;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IslandCommand implements CommandExecutor {
    SkyblockPlugin main;
    public IslandCommand(SkyblockPlugin main) {
        this.main  = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (args.length == 0){
            main.hasIS.putIfAbsent(player.getName(), false);
            if (!main.hasIS.get(player.getName()) || main.hasIS.get(player.getName())== null){
                IslandInventory gui = new IslandInventory();
                player.openInventory(gui.getInventory());
            }

            else {
                player.teleport(main.IScoor.get(player.getName()));
                SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(Bukkit.getWorld("world_Skyblock"),
                        main.IScoor.get(player.getName()).getX(), 0, main.IScoor.get(player.getName()).getZ() ) );
                player.sendMessage("téléportation sur l'île");
            }
        }

        if (args.length == 1){
            if (args[0].equalsIgnoreCase("setspawn")){
                //Si il n'y a rien en dessous
                Location ploc = player.getLocation();
                Location behindplayer = new Location(ploc.getWorld(), ploc.getX(), ploc.getY()-1, ploc.getZ(), ploc.getYaw(), ploc.getPitch());
                if (behindplayer.getBlock().getType().isAir()){
                    player.sendMessage("§cChoissisez un endroit valide");
                }
                else {
                    player.sendMessage("Le spawn de l'ile a été changé");
                    main.IScoor.put(player.getName(), player.getLocation());
                }



            }
        }


        return true;
    }
}

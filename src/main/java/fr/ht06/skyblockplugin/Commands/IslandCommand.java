package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.Inventory.IslandInventory;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

            if (args[0].equalsIgnoreCase("delete")){
                if (!main.hasIS.containsKey(player.getName()) ||!main.hasIS.get(player.getName()) ){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    player.sendMessage(Component.text("Are you sure you want to delete your island?").color(TextColor.color(0xE74C3C)));
                    player.sendMessage(Component.text("You are going to lost all your progression.").color(TextColor.color(0xE74C3C)));
                    player.sendMessage(Component.text("If you really want to delete your island, ").color(TextColor.color(0xE74C3C)).append(miniMessage.deserialize("<click:run_command:/is delete confirm>Click here.</click>").asComponent().color(TextColor.color(0x7B241C))));
                }

            }
        }

        if (args.length == 2){
            if (args[0].equalsIgnoreCase("delete") && args[1].equalsIgnoreCase("confirm")){
                if (!main.hasIS.containsKey(player.getName()) ||!main.hasIS.get(player.getName()) ){
                    player.sendMessage("§cYou don't have any island, use §7/is §cto create one.");
                }
                else{
                    Bukkit.dispatchCommand(player, "spawn");
                    //y
                    for (int i =-64; i<320/*couche max à couche min*/; i++){
                        Location isLoc1 = main.IScoor.get(player.getName()).clone().add(-50, -200, -50);
                        int x = isLoc1.getBlockX();
                        int y = i;
                        int z = isLoc1.getBlockZ();

                        Location isLoc2 = main.IScoor.get(player.getName()).clone().add(50, 300, 50);
                        int x2 = isLoc2.getBlockX();
                        int y2 = i;
                        int z2 = isLoc2.getBlockZ();
                        //x
                        for(int j = x; j< x2; j++){
                            //z
                            for(int k = z; k< z2; k++){
                                if (!Bukkit.getWorld("world_Skyblock").getBlockAt( new Location(Bukkit.getWorld("world_Skyblock"), j, i, k)).getType().isAir())
                                    Bukkit.getWorld("world_Skyblock").getBlockAt( new Location(Bukkit.getWorld("world_Skyblock"), j, i, k)).setType(Material.AIR);
                            }
                        }
                    }
                    main.hasIS.put(player.getName(), false);
                    main.IScoor.remove(player.getName());
                    main.CoordsTaken.remove(player.getName());
                    player.getInventory().clear();
                }
            }
            else{
                player.sendMessage("§cInvalid command, use §7/help JustSkyblock");
            }
        }


        return true;
    }
}

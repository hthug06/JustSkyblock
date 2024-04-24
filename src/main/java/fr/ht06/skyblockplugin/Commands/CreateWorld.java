package fr.ht06.skyblockplugin.Commands;

import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class CreateWorld implements CommandExecutor {
    private SkyblockPlugin main;
    public CreateWorld(SkyblockPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        Player player = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("createworld")){

            if (args.length == 0){
                if(new File(main.getServer().getWorldContainer().getAbsolutePath()+"/world_Skyblock/").exists()){
                    player.sendMessage("§cLe monde existe déjà");
                }
                else{
                    String settings = "{\"structures\":{\"structures\":{}},\"layers\":[{\"height\":9,\"block\":\"air\"},{\"height\":1,\"block\":\"air\"}],\"lakes\":false,\"features\":false,\"biome\":\"plains\"}";
                    WorldCreator worldcreator = new WorldCreator("world_Skyblock");
                    worldcreator.type(WorldType.FLAT).type(WorldType.FLAT).generatorSettings(settings).generateStructures(false);;
                    worldcreator.createWorld();


                    commandSender.sendMessage("§cMonde Skyblock créer");
                }


            }
            if (args.length == 1 && args[0].equalsIgnoreCase("tp")){
                if (player.getWorld().getName().equalsIgnoreCase("world")){
                    player.teleport(new Location(Bukkit.getWorld("world_Skyblock"), 0, 10, 0));

                }
                else {
                    player.teleport(new Location(Bukkit.getWorld("world"), 0, 10, 0));
                }
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("a")){
                player.sendMessage(player.getWorld().getName());
            }
        }


        return true;
    }
}

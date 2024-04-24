package fr.ht06.skyblockplugin;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CommandTest implements CommandExecutor {
    private SkyblockPlugin main;
    private WorldBorderApi worldBorderApi;
    private JavaPlugin javaPlugin;
    public CommandTest(SkyblockPlugin main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;

        main.hasIS.put(player, false);
        //@Nullable RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = main.getServer().getServicesManager().getRegistration(WorldBorderApi.class);
        //SkyblockPlugin.worldBorderApi.setBorder(player, 100, new Location(player.getWorld(), 0, 100, 0));


        if(strings.length == 1) player.sendMessage(String.valueOf(main.hasIS));


        return true;
    }
}

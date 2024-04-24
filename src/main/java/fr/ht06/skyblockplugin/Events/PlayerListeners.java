package fr.ht06.skyblockplugin.Events;

import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {
    SkyblockPlugin main;
    public PlayerListeners(SkyblockPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()){
            main.hasIS.put(player, false);
        }
    }


    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if (!player.isOp()){
            if (main.hasIS.get(player)==null || !main.hasIS.get(player)){
                event.setCancelled(true);
                return;
            }            if (!contains(player.getLocation(), main.IScoor.get(player).clone().add(-50, -200, -50), main.IScoor.get(player).clone().add(50, 300, 50))
                || main.hasIS.get(player) == null){
                event.setCancelled(true);
            }
        }
        else{
            if (main.hasIS.get(player)==null || !main.hasIS.get(player)) return;
        }


    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();

        if (!player.isOp()){
            if (main.hasIS.get(player)==null || !main.hasIS.get(player)){
                event.setCancelled(true);
                return;
            }
            if (!contains(player.getLocation(), main.IScoor.get(player).clone().add(-50, -200, -50), main.IScoor.get(player).clone().add(50, 300, 50))
                    || main.hasIS.get(player) == null){
                event.setCancelled(true);
            }
        }
        else{
            if (main.hasIS.get(player)==null || !main.hasIS.get(player)) return;
        }
    }



    public boolean contains(Location loc, Location l1, Location l2) {
        return loc.getBlockX() >= l1.getBlockX() && loc.getBlockX() <= l2.getBlockX()
                && loc.getBlockY() >= l1.getBlockY() && loc.getBlockY() <= l2.getBlockY()
                && loc.getBlockZ() >= l1.getBlockZ() && loc.getBlockZ() <= l2.getBlockZ();
    }
}

package fr.ht06.skyblockplugin.Events;

import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {
    SkyblockPlugin main;
    IslandManager islandManager = SkyblockPlugin.islandManager;
    Island island;

    public PlayerListeners(SkyblockPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (islandManager.playerHasIsland(player.getName())) island = islandManager.getIslandbyplayer(player.getName()).getIsland();


        /*if (!main.hasIS.containsKey(player.getName())) main.hasIS.put(player.getName(), false);
        if (!main.hasIS.containsKey(player.getName())) main.hasIS.put(player.getName(), false);

        if (!player.hasPlayedBefore()){
            main.hasIS.put(player.getName(), false);
        }
        if (player.hasPlayedBefore() && main.hasIS.get(player.getName())){
            main.hasIS.put(player.getName(), true);
        }
        else{
            main.hasIS.put(player.getName(), false);
        }*/
    }



    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        //if (islandManager.playerHasIsland(player.getName())) island = islandManager.getIslandbyplayer(player.getName()).getIsland();


        if (!player.isOp()){
            if (!islandManager.playerHasIsland(player.getName())){
                event.setCancelled(true);
                return;
            }
            if (!onHisIsland(player)){
                event.setCancelled(true);
            }
        }
        /*else{
            if (main.hasIS.get(player.getName())==null || !main.hasIS.get(player.getName())) return;
        }*/


    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        //if (islandManager.playerHasIsland(player.getName())) island = islandManager.getIslandbyplayer(player.getName()).getIsland();


        if (!player.isOp()){
            if (!islandManager.playerHasIsland(player.getName())){
                event.setCancelled(true);
                return;
            }
            if (!onHisIsland(player)){
                event.setCancelled(true);
            }
        }
        /*else{
            if (main.hasIS.get(player.getName())==null || !main.hasIS.get(player.getName())) return;
        }*/
    }

    public boolean contains(Location loc, Location l1, Location l2) {
        return loc.getBlockX() >= l1.getBlockX() && loc.getBlockX() <= l2.getBlockX()
                && loc.getBlockY() >= l1.getBlockY() && loc.getBlockY() <= l2.getBlockY()
                && loc.getBlockZ() >= l1.getBlockZ() && loc.getBlockZ() <= l2.getBlockZ();
    }

    public boolean onHisIsland(Player player) {
        Location loc;
        for (Island is : islandManager.getAllIsland()) {
            loc= new Location(Bukkit.getWorld("world_Skyblock"), is.getIslandCoordinates().get(0), 70, is.getIslandCoordinates().get(1));
            if (contains(player.getLocation(), loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                //player.sendMessage("you are on " +is.getIslandName());
                if (is.isOnThisIsland(player.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

}

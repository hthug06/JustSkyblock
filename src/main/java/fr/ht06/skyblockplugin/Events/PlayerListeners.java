package fr.ht06.skyblockplugin.Events;

import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlayerListeners implements Listener {
    SkyblockPlugin main;
    static IslandManager islandManager = SkyblockPlugin.islandManager;
    Island island;


    public PlayerListeners(SkyblockPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (islandManager.playerHasIsland(player.getName())) island = islandManager.getIslandbyplayer(player.getName()).getIsland();
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
    }

    @EventHandler //MARCHE SUIIII
    public void OnInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        Island island = islandManager.getIslandbyName(getAnotherPlayerIslandName(player));
        List<Material> listMat = new ArrayList<Material>();
        for (Map.Entry<String, Boolean> v : islandManager.getIslandbyplayer(player.getName()).getAllSettings().entrySet()){
            if (!v.getKey().equals("END_CRYSTAL")) listMat.add(Material.getMaterial(v.getKey()));
        }
        if (action.RIGHT_CLICK_AIR.isRightClick()) return;
        if (action.LEFT_CLICK_AIR.isLeftClick()) return;

         //if (player.isOp()) return; à mettre quand fini
        if (!player.getWorld().getName().equalsIgnoreCase("world_Skyblock")) return;
        if (!onHisIsland(player)){
            if (listMat.contains(Objects.requireNonNull(event.getClickedBlock()).getType())){
                if (!island.getAllSettings().get(event.getClickedBlock().getType().name())) event.setCancelled(true);
            }
            else if (event.getClickedBlock().getType().name().contains("BED") && !island.getAllSettings().get("WHITE_BED")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("SHULKER_BOX") && !island.getAllSettings().get("SHULKER_BOX")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("CAMPFIRE") && !island.getAllSettings().get("CAMPFIRE")) event.setCancelled(true);

        }
    }

    /*@EventHandler (priority = EventPriority.HIGH)
    public void onPlayerInteractAtEntity(EntityExplodeEvent event) {
        if(event.getEntityType() == EntityType.ENDER_CRYSTAL && !onHisIsland()) {
            Location loc = event.getLocation();
            event.setCancelled(true);
            EnderCrystal enderCrystal = (EnderCrystal) Bukkit.getWorld("world_Skyblock").spawnEntity(loc, EntityType.ENDER_CRYSTAL);
            enderCrystal.setShowingBottom(false);

        }
        event.setCancelled(true);
    }*/

    @EventHandler
    public void EntityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player)) return;
        if(event.getEntityType() == EntityType.ENDER_CRYSTAL && onHisIsland((Player) event.getDamager())) {
            event.setCancelled(false);
        }
        else if (event.getEntityType() == EntityType.ENDER_CRYSTAL && !onHisIsland((Player) event.getDamager())) {
            Player player = (Player) event.getDamager();
            player.sendActionBar(Component.text("You are not allowed to do this"));
            event.setCancelled(true);
        }
    }


    public static boolean contains(Location loc, Location l1, Location l2) {
        return loc.getBlockX() >= l1.getBlockX() && loc.getBlockX() <= l2.getBlockX()
                && loc.getBlockY() >= l1.getBlockY() && loc.getBlockY() <= l2.getBlockY()
                && loc.getBlockZ() >= l1.getBlockZ() && loc.getBlockZ() <= l2.getBlockZ();
    }

    public static boolean onHisIsland(Player player) {
        Location loc;
        for (Island is : islandManager.getAllIsland()) {
            loc= new Location(Bukkit.getWorld("world_Skyblock"), is.getIslandCoordinates().get(0), 70, is.getIslandCoordinates().get(1));
            if (contains(player.getLocation(), loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                if (is.isOnThisIsland(player.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getAnotherPlayerIslandName(Player player) {
        Location loc;
        for (Island is : islandManager.getAllIsland()) {
            loc= new Location(Bukkit.getWorld("world_Skyblock"), is.getIslandCoordinates().get(0), 70, is.getIslandCoordinates().get(1));
            if (contains(player.getLocation(), loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                return is.getIslandName();
            }
        }
        return null;
    }

}

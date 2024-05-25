package fr.ht06.skyblockplugin.Events;

import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
    MiniMessage miniMessage = MiniMessage.miniMessage();


    public PlayerListeners(SkyblockPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendMessage(miniMessage.deserialize("<gradient:#2E86C1:#229954:#2E86C1>This server is in developpement (mainly the skyblock) "));
        player.sendMessage(miniMessage.deserialize("<gradient:#2E86C1:#229954:#2E86C1>Skyblock is in version alpha-1.0 created by me (ht06)"));
        player.sendMessage(miniMessage.deserialize("<gradient:#2E86C1:#229954:#2E86C1>NO MORE RESET! you can now play witouth fear!"));
        player.sendMessage(Component.text("If you find a bug, contact ht06 on discord").color(TextColor.color(0xE74C3C)));

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

        //toutes les sécurités

        //si le joueur est OP/Admin/à la perm il bypass
        if (player.isOp() || player.hasPermission("skyblockplugin.bypass")) return;

        //si le joueur à une île ou n'en a pas

        island = islandManager.getIslandbyName(getAnotherPlayerIslandName(player));

        //Si le joueur essaye de cramer l'ile avec briquet /firecharge
        if (player.getInventory().getItemInMainHand().getType().equals(Material.FLINT_AND_STEEL)) event.setCancelled(true);
        if (player.getInventory().getItemInMainHand().getType().equals(Material.FIRE_CHARGE)) event.setCancelled(true);

        //Si le joueur n'est pas dans le monde des îles
        if (!player.getWorld().getName().equalsIgnoreCase("world_Skyblock")) return;


        List<Material> listMat = new ArrayList<Material>();
        for (Map.Entry<String, Boolean> v : island.getAllSettings().entrySet()){
            listMat.add(Material.getMaterial(v.getKey()));
        }

        if (!onHisIsland(player)){
            //permet d'enelver une erreur que j'ai pas vraiment compris
            //en gros quand on clique comme un gogole dans tout les sens en interragissant avec un block ça met une erreur
            try {
                event.getClickedBlock().getType();
            }catch (NullPointerException ignored){
                return;
            }

            if (listMat.contains(Objects.requireNonNull(event.getClickedBlock()).getType())){
                if (!island.getAllSettings().get(event.getClickedBlock().getType().name())) event.setCancelled(true);
            }
            else if (event.getClickedBlock().getType().name().contains("BED") && !island.getAllSettings().get("WHITE_BED")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("BUTTON") && !island.getAllSettings().get("OAK_BUTTON")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("SHULKER_BOX") && !island.getAllSettings().get("SHULKER_BOX")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("CAMPFIRE") && !island.getAllSettings().get("CAMPFIRE")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.BEACON)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.COMPOSTER)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.LODESTONE)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.BEE_NEST)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.BEEHIVE)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.CHISELED_BOOKSHELF)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.COMPARATOR)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.REPEATER)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.DRAGON_EGG)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().equals(Material.RESPAWN_ANCHOR)) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("SIGN")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("CAULDRON")) event.setCancelled(true);
            else if (event.getClickedBlock().getType().name().contains("REDSTONE")) event.setCancelled(true);
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
        else{
            Player player = (Player) event.getDamager();
            if (player.isOp() || player.hasPermission("skyblockplugin.bypass")) return;
        }

        if(event.getEntityType() == EntityType.ENDER_CRYSTAL && onHisIsland((Player) event.getDamager()) ) {
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

    public static String getAnotherPlayerIsland_PlayerName(Player player) {
        Location loc;
        for (Island is : islandManager.getAllIsland()) {
            loc= new Location(Bukkit.getWorld("world_Skyblock"), is.getIslandCoordinates().get(0), 70, is.getIslandCoordinates().get(1));
            if (contains(player.getLocation(), loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                return is.getOwner();
            }
        }
        return null;
    }

}

package fr.ht06.justskyblock.IslandManager;

import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DeleteIsland {
    static IslandManager islandManager = JustSkyblock.islandManager;
    public static void deleteIsland(Player player) {

        Island island = islandManager.getIslandbyplayer(player.getName()).getIsland();
        Bukkit.dispatchCommand(player, "spawn");
        //y
        List<Player> PlayerList = new ArrayList<>();
        for (int y = -64; y < 320/*couche max à couche min*/; y++) {
            Location isLoc1 = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), island.getIslandCoordinates().getBlockX(), 70, island.getIslandCoordinates()
                    .getBlockZ())
                    .clone().add(-((double) island.getSize() / 2), -200, -((double) island.getSize() / 2));
            ;
            int x1 = isLoc1.getBlockX();
            int z1 = isLoc1.getBlockZ();

            Location isLoc2 = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), island.getIslandCoordinates().getBlockX(), 70, island.getIslandCoordinates()
                    .getBlockZ())
                    .clone().add(((double) island.getSize() / 2), 300, ((double) island.getSize() / 2));
            ;
            int x2 = isLoc2.getBlockX();
            int z2 = isLoc2.getBlockZ();
            //x
            for (int x = x1; x < x2; x++) {
                //z
                for (int z = z1; z < z2; z++) {
                    if (!Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()).getBlockAt(new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z)).getType().isAir())
                        Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()).getBlockAt(new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z)).setType(Material.AIR);

                    if (x == 0 && y == 70 && z == 0) {
                        @NotNull Collection<Entity> livingEntity = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z).getNearbyEntities(((double) island.getSize() / 2), 70, ((double) island.getSize() / 2));
                        PlayerList = new ArrayList<>();
                        Player playertarget;
                        for (Entity e : livingEntity) {
                            if (e instanceof Player) {
                                playertarget = ((Player) e).getPlayer();
                                PlayerList.add(playertarget);
                            } else e.remove();
                        }
                    }
                }
            }
        }
        //When the island is deleted, we delete it frome the IslandManager
        islandManager.deleteIsland(island.getIslandName());

        //All the player present on the island execute a command (the command is in the config.yml)
        //And the player that belong to the island get their inventory clear
        for (Player p : PlayerList) {
            if (island.isOnThisIsland(p.getUniqueId())) {
                p.getInventory().clear();
                Bukkit.dispatchCommand(p, JustSkyblock.getInstance().getConfig().getString("IslandDeleteCommand"));
            } else {
                Bukkit.dispatchCommand(p, JustSkyblock.getInstance().getConfig().getString("IslandDeleteCommand"));
            }
        }

    }

    //only admin can acces this
    public static void deleteIsland(Island island) {

        //y
        List<Player> PlayerList = null;
        for (int y = -64; y < 320/*couche max à couche min*/; y++) {
            Location isLoc1 = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), island.getIslandCoordinates().getBlockX(), 70, island.getIslandCoordinates()
                    .getBlockZ())
                    .clone().add(-((double) island.getSize() / 2), -200, -((double) island.getSize() / 2));
            ;
            int x1 = isLoc1.getBlockX();
            int z1 = isLoc1.getBlockZ();

            Location isLoc2 = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), island.getIslandCoordinates().getBlockX(), 70, island.getIslandCoordinates()
                    .getBlockZ())
                    .clone().add(((double) island.getSize() / 2), 300, ((double) island.getSize() / 2));
            ;
            int x2 = isLoc2.getBlockX();
            int z2 = isLoc2.getBlockZ();
            //x
            for (int x = x1; x < x2; x++) {
                //z
                for (int z = z1; z < z2; z++) {
                    if (!Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()).getBlockAt(new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z)).getType().isAir())
                        Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()).getBlockAt(new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z)).setType(Material.AIR);

                    if (x == 0 && y == 70 && z == 0) {
                        @NotNull Collection<Entity> livingEntity = new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), x, y, z).getNearbyEntities(((double) island.getSize() / 2), 70, ((double) island.getSize() / 2));
                        PlayerList = new ArrayList<>();
                        Player playertarget;
                        for (Entity e : livingEntity) {
                            if (e instanceof Player) {
                                playertarget = ((Player) e).getPlayer();
                                PlayerList.add(playertarget);
                            } else e.remove();
                        }
                    }
                }
            }
        }
        //When the island is deleted, we delete it frome the IslandManager
        islandManager.deleteIsland(island.getIslandName());

        //All the player present on the island execute a command (the command is in the config.yml)
        //And the player that belong to the island get their inventory clear
        for (Player p : PlayerList){
            if (island.isOnThisIsland(p.getUniqueId())) p.getInventory().clear();
            p.sendMessage("&cThis island has been deleted by an admin");
            Bukkit.dispatchCommand(p, JustSkyblock.getInstance().getConfig().getString("IslandDeleteCommand"));
        }
    }
}

package fr.ht06.justskyblock.IslandManager;

import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;

public class IslandWorldBorder {

    public static WorldBorder setWorldBorder(Island island){
        WorldBorder worldBorder = Bukkit.createWorldBorder();
        worldBorder.setCenter(island.getCoordinates());
        worldBorder.setSize(island.getSize());
        return worldBorder;
    }

    //Maybe set it red or green for later

}

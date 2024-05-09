package fr.ht06.skyblockplugin.IslandManager;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Island {
    private String IslandName;
    private String playerOwnerName;
    private List<Integer> islandCoordinates;
    private Location islandSpawn;
    private List<String> players = new ArrayList<>();
    private Map<String , Boolean> allSettings = new HashMap<>();
    private Map<Object, Object> Island = new HashMap<>();

    public Island(String IslandName, String playerOwnerName, List<Integer> islandCoordinates, Location islandSpawn){
        this.IslandName = IslandName;
        this.playerOwnerName = playerOwnerName;
        this.players.add(playerOwnerName);
        this.islandCoordinates = islandCoordinates;
        this.islandSpawn = islandSpawn;
        this.createSettings();
    }

    public String getIslandName() {
        return IslandName;
    }

    public void setIslandName(String islandName) {
        IslandName = islandName;
    }

    public Island getIsland(){
        return this;
    }


    public Map<Object, Object> getIslandtoMap() {
        return Island;
    }

    public Map<String, Boolean> getAllSettings() {
        return this.allSettings;
    }

    public void setSettings(String key, Boolean value){
        this.allSettings.put(key, value);
    }

    public org.bukkit.Location getIslandSpawn() {
        return islandSpawn;
    }

    public void setIslandSpawn(Location islandSpawn) {
        this.islandSpawn = islandSpawn;
    }

    public List<Integer> getIslandCoordinates() {
        return islandCoordinates;
    }

    public void setIslandCoordinates(List<Integer> islandCoordinates) {
        this.islandCoordinates = islandCoordinates;
    }

    public String getPlayerOwnerName() {
        return playerOwnerName;
    }

    public void setPlayerOwnerName(String playerOwnerName) {
        this.playerOwnerName = playerOwnerName;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void addPlayers(String player) {
        this.players.add(player);
    }
    public void addPlayers(List<String> players) {
        this.players.addAll(players);
    }

    public Boolean isOnThisIsland(String playerName){
        return this.players.contains(playerName);
    }

    public Map<Object, Object> IStoMap(){
        Island.put("Name", IslandName);
        Island.put("Owner", playerOwnerName);
        Island.put("Players", players);
        Island.put("Settings", allSettings);
        Island.put("IslandLoc", islandCoordinates);
        Island.put("IslandSpawn", islandSpawn);
        return Island;
    }



    private void createSettings(){
        this.allSettings.put("OAK_DOOR", true);
        this.allSettings.put("OAK_TRAPDOOR", true);
        this.allSettings.put("OAK_PRESSURE_PLATE", true);
        this.allSettings.put("CHEST", true);
        this.allSettings.put("OAK_BUTTON", true);
    }
}

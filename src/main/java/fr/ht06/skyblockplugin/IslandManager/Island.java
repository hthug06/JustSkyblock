package fr.ht06.skyblockplugin.IslandManager;

import org.bukkit.Location;

import java.util.*;


public class Island {
    private String IslandName;
    private String playerOwnerName;
    private List<Integer> islandCoordinates;
    private Location islandSpawn;
    private List<String> players = new ArrayList<>();
    private Map<String , Boolean> allSettings = new LinkedHashMap<>();  //Pour ne pas ranger par ordre alphbétique
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

    public Boolean isplayerOwner(String player){
        return player.equalsIgnoreCase(playerOwnerName);
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
        this.allSettings.put("OAK_FENCE_GATE", true);//0
        this.allSettings.put("OAK_DOOR", true);
        this.allSettings.put("OAK_TRAPDOOR", true);
        this.allSettings.put("OAK_PRESSURE_PLATE", true);
        this.allSettings.put("OAK_BUTTON", true);
        this.allSettings.put("LEVER", true);
        this.allSettings.put("WHITE_BED", true);
        this.allSettings.put("CHEST", true);
        this.allSettings.put("BARREL", true);
        this.allSettings.put("SHULKER_BOX", true);
        this.allSettings.put("CRAFTING_TABLE", true);//10
        this.allSettings.put("STONECUTTER", true);
        this.allSettings.put("CARTOGRAPHY_TABLE", true);
        this.allSettings.put("SMITHING_TABLE", true);
        this.allSettings.put("GRINDSTONE", true);
        this.allSettings.put("FURNACE", true);
        this.allSettings.put("SMOKER", true);
        this.allSettings.put("BLAST_FURNACE", true);
        this.allSettings.put("LOOM", true);
        this.allSettings.put("ANVIL", true);
        this.allSettings.put("CAMPFIRE", true);//20
        this.allSettings.put("NOTE_BLOCK", true);
        this.allSettings.put("JUKEBOX", true);
        this.allSettings.put("ENCHANTING_TABLE", true);
        this.allSettings.put("BREWING_STAND", true);
        this.allSettings.put("BELL", true);//25
        this.allSettings.put("HOPPER", true);//25
    }
}

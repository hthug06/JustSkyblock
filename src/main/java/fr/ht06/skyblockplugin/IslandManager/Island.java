package fr.ht06.skyblockplugin.IslandManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;


public class Island {
    private String IslandName;
    private List<Integer> islandCoordinates;
    private Location islandSpawn;
    private String Owner;
    private List<String> Moderator = new ArrayList<>();
    private List<String> Member = new ArrayList<>();
    //private List<Map<String/*Les joueur*/, String/*leur grade sur l'île*/>> players = new ArrayList<>();
    //private List</*Liste de tout */Map<String/*Grade*/, List<String /*Joueur*/>>> players = new ArrayList<>();  // [{Owner=[ht06]}, {Mod=[sxg919, fneris]}, {Member=[Benny232, GCZ115, MeatEmy]}
    private Map<String , Boolean> allSettings = new LinkedHashMap<>();  //Pour ne pas ranger par ordre alphbétique
    private Map<Object, Object> Island = new HashMap<>();

    public Island(String IslandName, List<Integer> islandCoordinates, Location islandSpawn){
        this.IslandName = IslandName;
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

    public String getOwner() {
        //il ne peut y avoir qu'un seul Owner
        return Owner;
    }

    public Boolean isOwner(String player){
        return Owner.equalsIgnoreCase(player);
    }

    public void setOwner(String playerOwnerName) {
        this.Owner = playerOwnerName;
    }

    public List<String> getAllModerators(){
        return this.Moderator;
    }

    public void setModerator(List<String> list){
        this.Moderator = list;
    }

    public void addModerator(String playerName) {
        this.Moderator.add(playerName);
    }

    public void removeModerator(String playerName){
        this.Moderator.remove(playerName);
    }

    public Boolean isModerator(String playerName){
        return this.Moderator.contains(playerName);
    }

    public List<String> getAllMembers(){
        return this.Member;
    }

    public void setMember(List<String> list){
        this.Member = list;
    }

    public void addMember(String playerName) {
        this.Member.add(playerName);
    }

    public void removeMember(String playerName){
        this.Member.remove(playerName);
    }

    public Boolean isMember(String playerName){
        return this.Member.contains(playerName);
    }

    public Boolean isOnThisIsland(String playerName){
        return this.Owner.equalsIgnoreCase(playerName) || this.Moderator.contains(playerName) || this.Member.contains(playerName);
    }

    public void BroadcastLeave(Player player){
        Player playerOwner = Bukkit.getPlayerExact(Owner);
        if (playerOwner != null && playerOwner.isOnline()) {
            playerOwner.sendMessage(player.getName() + " leave the island");
        }
        for (String modo : Moderator){
            Player playerModo = Bukkit.getPlayerExact(modo);
            if (playerModo != null && playerModo.isOnline()) {
                playerModo.sendMessage(player.getName() + " leave the island");
            }
        }
        for (String member : Member){
            Player playerMember = Bukkit.getPlayerExact(member);
            if (playerMember != null && playerMember.isOnline()) {
                playerMember.sendMessage(player.getName() + " leave the island");
            }
        }
    }

    public void BroadcastJoin(Player player) {
        if (player != null && player.isOnline()) {
            player.sendMessage("You join " + this.getIslandName());
        }

        Player playerOwner = Bukkit.getPlayerExact(Owner);
        if (playerOwner != null && playerOwner.isOnline()) {
            playerOwner.sendMessage(player.getName() + " join the island");
        }

        for (String modo : Moderator){
            Player playerModo = Bukkit.getPlayerExact(modo);
            if (playerModo != null && playerModo.isOnline()) {
                playerModo.sendMessage(player.getName() + " join the island");
            }
        }
        for (String member : Member){
            Player playerMember = Bukkit.getPlayerExact(member);
            if (playerMember != null && playerMember.isOnline()) {
                playerMember.sendMessage(player.getName() + " join the island");
            }
        }
    }

    public Map<Object, Object> IStoMap(){
        Island.put("Name", IslandName);
        Island.put("Owner", Owner);
        Island.put("Moderator", getAllModerators());
        Island.put("Member", getAllMembers());
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

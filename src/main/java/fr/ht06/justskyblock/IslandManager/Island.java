package fr.ht06.justskyblock.IslandManager;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;


public class Island {
    private String IslandName;
    private Location islandCoordinates;
    private Location islandSpawn;
    private UUID Owner;
    private List<UUID> Moderator = new ArrayList<>();
    private List<UUID> Member = new ArrayList<>();
    private double level = 0;
    private double size = 50;
    private int rank = 0;
    private LocalDateTime Date;
    private Date aujourdhui;
    private Integer cobbleGenLevel = 1;
    private Integer cobbleGenLevelUnlock = 1;
    private Map<String , Boolean> allSettings = new LinkedHashMap<>();  //Pour ne pas ranger par ordre alphbétique
    private Map<String, Boolean> farmingQuest = new HashMap<>();
    private Map<String, Integer> cropsCounter = new HashMap<>();
    private Map<String, Boolean> miningQuest = new HashMap<>();
    private Map<String, Integer> mineralCounter = new HashMap<>();
    private Map<String, Boolean> lumberQuest = new HashMap<>();
    private Map<String, Integer> lumberCounter = new HashMap<>();
    private Map<Object, Object> Island = new HashMap<>();

    public Island(String IslandName, Location islandCoordinates, Location islandSpawn){
        this.IslandName = IslandName;
        this.islandCoordinates = islandCoordinates;
        this.islandSpawn = islandSpawn;
        Date = LocalDateTime.now();
        aujourdhui = new Date();
        this.createSettings();
        this.createQuest();
        this.initCropsCounter();
        this.initMineralCounter();
        this.initLumberCounter();
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

    public Location getIslandCoordinates() {
        return islandCoordinates;
    }

    public void setIslandCoordinates(Location islandCoordinates) {
        this.islandCoordinates = islandCoordinates;
    }

    public UUID getOwner() {
        //il ne peut y avoir qu'un seul Owner
        return Owner;
    }


    public Boolean isOwner(UUID playerUUID){
        return Owner.equals(playerUUID);
    }

    public void setOwner(UUID playerOwnerName) {
        this.Owner = playerOwnerName;
    }

    public List<UUID> getAllModerators(){
        return this.Moderator;
    }

    public void setModerator(List<UUID> list){
        this.Moderator = list;
    }

    public void addModerator(UUID playerName) {
        this.Moderator.add(playerName);
    }

    public void removeModerator(UUID playerName){
        this.Moderator.remove(playerName);
    }

    public Boolean isModerator(UUID playerName){
        return this.Moderator.contains(playerName);
    }

    public List<UUID> getAllMembers(){
        return this.Member;
    }

    public void setMember(List<UUID> list){
        this.Member = list;
    }

    public void addMember(UUID playerName) {
        this.Member.add(playerName);
    }

    public void removeMember(UUID playerName){
        this.Member.remove(playerName);
    }

    public Boolean isMember(UUID playerName){
        return this.Member.contains(playerName);
    }

    public double getLevel(){
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public LocalDateTime getDate(){
        return Date;
    }

    public String getDateToString(){
        return Date.getDayOfMonth() +  "/" + Date.getMonth().getValue() + "/" + Date.getYear() + " " + Date.getHour()+":"+Date.getMinute();
    }

    public void setDate(LocalDateTime date) {
        Date = date;
    }

    public Boolean isOnThisIsland(UUID playerName){
        return this.Owner.equals(playerName) || this.Moderator.contains(playerName) || this.Member.contains(playerName);
    }

    public void BroadcastMessage(String message) {
        Player playerOwner = Bukkit.getPlayer(Owner);
        if (playerOwner != null && playerOwner.isOnline()) {
            playerOwner.sendMessage(message);
        }

        for (UUID modo : Moderator) {
            Player playerModo = Bukkit.getPlayer(modo);
            if (playerModo != null && playerModo.isOnline()) {
                playerModo.sendMessage(message);
            }
        }
        for (UUID member : Member) {
            Player playerMember = Bukkit.getPlayer(member);
            if (playerMember != null && playerMember.isOnline()) {
                playerMember.sendMessage(message);
            }
        }
    }

    public void BroadcastMessage(Component message) {
        Player playerOwner = Bukkit.getPlayer(Owner);
        if (playerOwner != null && playerOwner.isOnline()) {
            playerOwner.sendMessage(message);
        }

        for (UUID modo : Moderator) {
            Player playerModo = Bukkit.getPlayer(modo);
            if (playerModo != null && playerModo.isOnline()) {
                playerModo.sendMessage(message);
            }
        }
        for (UUID member : Member) {
            Player playerMember = Bukkit.getPlayer(member);
            if (playerMember != null && playerMember.isOnline()) {
                playerMember.sendMessage(message);
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

    public void createQuest(){
        List<String> listFarm = List.of("Wheat", "Carrot", "Potato", "Sugar_Cane", "Cactus");
        for (String s:listFarm) {
            for (int i = 1; i <= 9; i++) {
                this.farmingQuest.put(s + i, false);

            }
        }

        List<String> listMine = List.of("Stone", "Coal", "Iron", "Copper", "Lapis_lazuli", "Gold", "Redstone", "Diamond", "Emerald", "Obsidian");
        for (String s:listMine) {
            for (int i = 1; i <= 9; i++) {
                this.miningQuest.put(s + i, false);

            }
        }

        List<String> listLumber = List.of("Oak", "Birch", "Spruce", "Jungle", "Acacia", "Dark_Oak", "Mangrove", "Cherry", "Crimson", "Warped");
        for (String s:listLumber) {
            for (int i = 1; i <= 9; i++) {
                this.lumberQuest.put(s + i, false);

            }
        }
    }

    private void initCropsCounter(){
        List<String> listFarm = List.of("Wheat", "Carrot", "Potato", "Sugar_Cane", "Cactus");
        for (String s:listFarm) {
            cropsCounter.put(s, 0);
        }
    }

    private void initMineralCounter(){
        List<String> listMinerals = List.of("Stone", "Coal", "Iron", "Copper", "Lapis_lazuli", "Gold", "Redstone", "Diamond", "Emerald", "Obsidian");
        for (String s: listMinerals) {
            mineralCounter.put(s, 0);
        }
    }

    private void initLumberCounter(){
        List<String> listWood = List.of("Oak", "Birch", "Spruce", "Jungle", "Acacia", "Dark_Oak", "Mangrove", "Cherry", "Crimson", "Warped");
        for (String s: listWood) {
            lumberCounter.put(s, 0);
        }
    }
    //FARM
    public Map<String, Boolean> getFarmingQuest(){
        return farmingQuest;
    }

    public void setFarmingQuest(String s, Boolean b){
        this.farmingQuest.put(s, b);
    }

    public Map<String, Integer> getCropsCounter(){
        return cropsCounter;
    }

    public void setCropsCounter(String s, Integer i){
        cropsCounter.put(s, i);
    }

    //Mining
    public Map<String, Boolean> getMiningQuest(){
        return miningQuest;
    }

    public void setMiningQuest(String s, Boolean b){
        this.miningQuest.put(s, b);
    }

    public Map<String, Integer> getMineralCounter(){
        return mineralCounter;
    }

    public void setMineralCounter(String s, Integer i){
        mineralCounter.put(s, i);
    }

    //Lumber /  Wood
    public Map<String, Boolean> getLumberQuest(){
        return lumberQuest;
    }

    public void setLumberQuest(String s, Boolean b){
        this.lumberQuest.put(s, b);
    }

    public Map<String, Integer> getLumberCounter(){
        return lumberCounter;
    }

    public void setLumberCounter(String s, Integer i){
        lumberCounter.put(s, i);
    }

    public Integer getCobbleGenLevel() {
        return cobbleGenLevel;
    }

    public void setCobbleGenLevel(Integer cobbleGenLevel) {
        this.cobbleGenLevel = cobbleGenLevel;
    }

    public Integer getCobbleGenLevelUnlock() {
        return cobbleGenLevelUnlock;
    }

    public void setCobbleGenLevelUnlock(Integer cobbleGenLevelUnlock) {
        this.cobbleGenLevelUnlock = cobbleGenLevelUnlock;
    }

    public List<Player> getAllPlayerOnIsland(){
        @NotNull Collection<Entity> livingEntity = this.getIslandCoordinates().getNearbyEntities(( this.getSize() / 2), 70, (this.getSize() / 2));
        List<Player> PlayerList = new ArrayList<>();
        Player playertarget;
        for (Entity e : livingEntity) {
            if (e instanceof Player) {
                playertarget = ((Player) e).getPlayer();
                PlayerList.add(playertarget);
            }
        }
        return PlayerList;
    }
}

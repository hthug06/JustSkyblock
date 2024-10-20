package fr.ht06.justskyblock.IslandManager;

import fr.ht06.justskyblock.Events.PlayerListeners;
import fr.ht06.justskyblock.JustSkyblock;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.*;

public class IslandManager {

    private List<Island> allIsland = new ArrayList<>();
    private List<IslandByConfigYAML> allIslandByconfigYML = new ArrayList<>();
    private Map<String, Location> allCoordinate = new HashMap<>();
    private Map<String, List<String>> invitation = new HashMap<>();

    public Map<String, Location> getAllCoordinate() {
        return allCoordinate;
    }


    public void addAllCoordinate(Island island) {
        this.allCoordinate.put(island.getIslandName(), island.getCoordinates());
    }

    public void addIsland(Island island) {
        this.allCoordinate.put(island.getIslandName(), island.getCoordinates());
        this.allIsland.add(island);
    }

    public Boolean IslandExist(String IslandName){
        for (Island v: allIsland){
            if (v.getIslandName().equalsIgnoreCase(IslandName)){
                return true;
            }
        }
        return false;
    }

    public Boolean playerHasIsland(String playerName){
        for (Island v: allIsland){
            if (v.isOnThisIsland(Bukkit.getPlayerUniqueId(playerName))){
                return true;
            }
        }
        return false;
    }

    public Island getIslandbyName(String IslandName){
        for (Island v: allIsland){
            if (v.getIslandName().equalsIgnoreCase(IslandName)){
                return v.getIsland();
            }
        }
        return null;
    }

    public Island getIslandbyplayer(String playerName){
        for (Island v: allIsland){
            if (v.isOnThisIsland(Bukkit.getPlayerUniqueId(playerName))){
                return v.getIsland();
            }
        }
        return null;
    }

    public Island getIslandbyLocation(Location location){
        Location loc;
        for (Island is : getAllIsland()) {
            loc= new Location(Bukkit.getWorld(JustSkyblock.getInstance().getWorldName()), is.getCoordinates().getBlockX(), 70, is.getCoordinates().getBlockZ());
            if (PlayerListeners.contains(location, loc.clone().add(-50, -200, -50), loc.clone().add(50, 300, 50))) {
                return is;
            }
        }
        return null;
    }


    public Map<Object, Object> getIslandtoMap(String IslandName){
        for (Island v: allIsland){
            if (v.getIslandName().equalsIgnoreCase(IslandName)){
                return v.IStoMap();
            }
        }
        return null;
    }

    public List<Map<Object, Object>> getallIslandtoMap(String IslandName){
        List<Map<Object, Object>> liste = new ArrayList<>();
        for (Island v: allIsland){
           liste.add(v.IStoMap());
        }
        return liste;
    }

    public List<Map<Object, Object>> getAllIslandtoMap(){
        List<Map<Object, Object>> list = new ArrayList<>();
        for (Island v: allIsland){
            list.add(v.IStoMap());
        }
        return list;
    }

    public List<Island> getAllIsland(){
        List<Island> list = new ArrayList<>(allIsland);
        return list;
    }

    public void deleteIsland(String IslandName){
        for (int i = 0; i<= allIsland.size(); i++){
            if (allIsland.get(i).getIslandName().equalsIgnoreCase(IslandName)){
                allIsland.remove(i);
                break;
            }
        }
    }

    public Boolean IslandCoordinateTaken(List<Integer> xz){
        for (Island i : this.allIsland){
            if (i.getCoordinates().getBlockX() == xz.get(0) && i.getCoordinates().getBlockZ() == xz.get(1)){
                return true;
            }
        }
        return false;
    }

    public Boolean isInvitedByPlayer(String playerName, String Inviter){
        if (this.invitation.containsKey(playerName)){
            return this.invitation.get(playerName).contains(Inviter);
        }
        else{
            return false;
        }

    }

    public void addPlayerInvitation(String playerName, String Inviter) {
        invitation.computeIfAbsent(playerName, k -> new ArrayList<>()).add(Inviter);
    }

    public Boolean isInvitedby(String playerInvited, String invitedBy){
        return this.invitation.containsKey(playerInvited) && this.invitation.get(playerInvited).contains(invitedBy);
    }

    public void removePlayerInvitation(String playerName, String Inviter){
        if (this.invitation.containsKey(playerName) && this.invitation.get(playerName).contains(Inviter)) {
            this.invitation.get(playerName).remove(Inviter);

            if (this.invitation.get(playerName).isEmpty()) {
                this.invitation.remove(playerName);
            }
        }
    }

    public void createAllIslandByConfigYAML(){
        this.allIslandByconfigYML.clear();
        for (String str: JustSkyblock.getInstance().getConfig().getConfigurationSection("Island.").getKeys(false)){
            this.allIslandByconfigYML.add(new IslandByConfigYAML(str));
        }
    }

    public List<IslandByConfigYAML> getAllIslandByconfigYML() {
        return allIslandByconfigYML;
    }

    public IslandByConfigYAML getIslandByConfigYAMLBySlot(int slot){
        for (IslandByConfigYAML island : this.allIslandByconfigYML){
            if (island.slot ==  slot) return island;
        }
        return null;
    }
}

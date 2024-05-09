package fr.ht06.skyblockplugin.IslandManager;

import java.util.*;

public class IslandManager {

    private List<Island> allIsland = new ArrayList<>();
    private Map<String, List<Integer>> allCoordinate = new HashMap<>();

    public Map<String, List<Integer>> getAllCoordinate() {
        return allCoordinate;
    }

    public void addAllCoordinate(Island island) {
        this.allCoordinate.put(island.getIslandName(), island.getIslandCoordinates());
    }

    public void addIsland(Island island) {
        this.allCoordinate.put(island.getIslandName(), island.getIslandCoordinates());
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
            if (v.isOnThisIsland(playerName)){
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
            if (v.isOnThisIsland(playerName)){
                return v.getIsland();
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

}

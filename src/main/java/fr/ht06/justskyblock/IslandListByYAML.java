package fr.ht06.justskyblock;

import java.util.*;

public class IslandListByYAML {
    private JustSkyblock main = JustSkyblock.getInstance();
    private Map<String, Map<String, String>> listeIS = new HashMap<>();

    //String id;

    public IslandListByYAML(/* id, String block, String name, String lore, String schem, Integer slot*/){
        //this.id = id;
    }

    public void createIsland(String id){
        Map<String, String> map1 = new HashMap<>();

        map1.put("Block", String.valueOf(JustSkyblock.getInstance().getConfig().get("IS." + id + "." + "Block")));

        map1.put("Name", String.valueOf(JustSkyblock.getInstance().getConfig().get("IS." + id + "." + "Name")));

        map1.put("Schematic", String.valueOf(JustSkyblock.getInstance().getConfig().get("IS." + id + "." + "Schematic")));

        map1.put("Slot", String.valueOf(JustSkyblock.getInstance().getConfig().get("IS." + id + "." + "Slot")));

        listeIS.put(id,map1);
    }

    public Map<String, Map<String, String>> seeallIsland(){
        return listeIS;
    }

    public Map<String, String> getIsland(String id){
        return listeIS.get(id);
    }

    public Map<String, String> getIslandBySlot(Integer slot){
        Map<String, String> liste;
        if (listeIS.size() < slot) JustSkyblock.getInstance().resetConfig();

        for (Map.Entry<String, Map<String, String>> a : listeIS.entrySet()){
            if (a.getValue().get("Slot").equals(slot.toString())){
                liste = listeIS.get(a.getKey());
                return liste;
            }
        }

        return null;
    }



}

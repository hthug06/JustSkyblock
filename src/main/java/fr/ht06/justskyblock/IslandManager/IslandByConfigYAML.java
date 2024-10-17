package fr.ht06.justskyblock.IslandManager;

import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class IslandByConfigYAML {
    String id;
    Material block;
    Component name;
    List<Component> lore= new ArrayList<>();
    String schematic;
    int slot;
    MiniMessage miniMessage = MiniMessage.miniMessage();

    public IslandByConfigYAML(String id){
        this.id = id;
        JustSkyblock.getInstance().getConfig().getConfigurationSection("IS."+id).getKeys(false).stream().forEach(s -> {
            switch (s){
                case "Block" -> this.block = Material.getMaterial(JustSkyblock.getInstance().getConfig().getString("IS."+id+".Block"));
                case "Name" -> this.name = miniMessage.deserialize(JustSkyblock.getInstance().getConfig().getString("IS."+id+".Name"));
                case "Lore" -> JustSkyblock.getInstance().getConfig().getStringList("IS."+id+".Lore").stream().forEach(str -> {
                    lore.add(miniMessage.deserialize(str));
                });
                case "Schematic" ->  this.schematic = JustSkyblock.getInstance().getConfig().getString("IS."+id+".Schematic");
                case "Slot" ->  this.slot = JustSkyblock.getInstance().getConfig().getInt("IS."+id+".Slot");
            }
        });
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getSchematic() {
        return schematic;
    }

    public void setSchematic(String schematic) {
        this.schematic = schematic;
    }

    public List<Component> getLore() {
        return lore;
    }

    public void setLore(List<Component> lore) {
        this.lore = lore;
    }

    public Component getName() {
        return name;
    }

    public void setName(Component name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Material getBlock() {
        return block;
    }

    public void setBlock(Material block) {
        this.block = block;
    }



}

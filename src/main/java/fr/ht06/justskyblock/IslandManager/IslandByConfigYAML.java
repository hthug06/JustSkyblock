package fr.ht06.justskyblock.IslandManager;

import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class IslandByConfigYAML {
    String type;
    Material block;
    Component name;
    List<Component> lore= new ArrayList<>();
    String schematic;
    int slot;
    MiniMessage miniMessage = MiniMessage.miniMessage();

    public IslandByConfigYAML(String type){
        this.type = type;
        JustSkyblock.getInstance().getConfig().getConfigurationSection("Island."+ type).getKeys(false).stream().forEach(s -> {
            switch (s){
                case "Block" -> this.block = Material.getMaterial(JustSkyblock.getInstance().getConfig().getString("Island."+ type +".Block"));
                case "Name" -> this.name = miniMessage.deserialize(JustSkyblock.getInstance().getConfig().getString("Island."+ type +".Name"));
                case "Lore" -> JustSkyblock.getInstance().getConfig().getStringList("Island."+ type +".Lore").stream().forEach(str -> {
                    lore.add(miniMessage.deserialize(str));
                });
                case "Schematic" ->  this.schematic = JustSkyblock.getInstance().getConfig().getString("Island."+ type +".Schematic");
                case "Slot" ->  this.slot = JustSkyblock.getInstance().getConfig().getInt("Island."+ type +".Slot");
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Material getBlock() {
        return block;
    }

    public void setBlock(Material block) {
        this.block = block;
    }



}

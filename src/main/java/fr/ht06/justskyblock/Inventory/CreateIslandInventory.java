package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.CreateItem;
import fr.ht06.justskyblock.IslandManager.IslandByConfigYAML;
import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreateIslandInventory implements InventoryHolder {
    private Inventory inv;
    private IslandManager islandManager = JustSkyblock.islandManager;

    public CreateIslandInventory(){
        inv = Bukkit.createInventory(this, 9, Component.text("Create your Island"));  //création de l'inventaire
        init();//Pour mettre les item
    }

    private void init(){

        ItemStack item;
        List<IslandByConfigYAML> configIsland = islandManager.getAllIslandByconfigYML();

        for (IslandByConfigYAML island: configIsland){
            item = CreateItem.createItem(island.getName(), 1, island.getBlock(), island.getLore());

            inv.setItem(island.getSlot(), item);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}

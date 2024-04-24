package fr.ht06.skyblockplugin.Inventory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class IslandInventory implements InventoryHolder {
    private Inventory inv;

    public IslandInventory(){
        inv = Bukkit.createInventory(this, 9, "Island");  //création de l'inventaire
        init();//Pour mettre les item
    }

    private void init(){
        ItemStack item;

        item = createItem("§cIsland", Material.GRASS_BLOCK, Collections.singletonList(Component.text("Create an island").decoration(TextDecoration.ITALIC, false)));
        inv.setItem(0, item);

        item = createItem("§cIsland", Material.SNOW_BLOCK, Collections.singletonList(Component.text("Create an island/ Snow Island").decoration(TextDecoration.ITALIC, false)));
        inv.setItem(1, item);

    }

    private ItemStack createItem(String name, Material material, List<Component> lore){

        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(Component.text(name));
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}

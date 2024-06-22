package fr.ht06.justskyblock.Inventory;

import com.sk89q.worldedit.world.registry.ItemMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class IslandInfoInventory implements InventoryHolder {

    Inventory inv;

    public IslandInfoInventory(){
        inv = Bukkit.createInventory(this, 54, "INFO");
        init();
    }

    private void init() {
        ItemStack itemStack = new ItemStack(Material.GLASS_PANE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setHideTooltip(true);
        itemStack.setItemMeta(itemMeta);

        inv.setItem(0, itemStack);

    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}

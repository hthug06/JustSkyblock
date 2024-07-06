package fr.ht06.justskyblock.Inventory;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;


public class QuestInventory implements InventoryHolder {

    Inventory inv = Bukkit.createInventory(this, 9, text("Island Quests", TextColor.color(0x85929E )));

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}

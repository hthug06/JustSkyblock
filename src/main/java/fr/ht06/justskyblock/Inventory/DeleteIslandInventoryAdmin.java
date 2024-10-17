package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.CreateItem;
import fr.ht06.justskyblock.Inventory.upgrade.CustomCobbleGenUpgrade;
import fr.ht06.justskyblock.Inventory.upgrade.IslandSizeUpgrade;
import fr.ht06.justskyblock.Inventory.upgrade.UpgradeMain;
import fr.ht06.justskyblock.IslandManager.DeleteIsland;
import fr.ht06.justskyblock.IslandManager.Island;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeleteIslandInventoryAdmin implements InventoryHolder, Listener {

    public String island;
    private Inventory inv;
    private ItemStack item;

    public DeleteIslandInventoryAdmin(){}

    public DeleteIslandInventoryAdmin(String island){
        this.island = island;
        inv = Bukkit.createInventory(this, 27, Component.text("Delete " + island).color(TextColor.color(0xB03A2E)));  //création de l'inventaire
        init();//Pour mettre les item
    }
    private void init(){
        for (int i  = 0; i<27; i++){
            if (i == 13){
                List<Component> lore = new ArrayList<>();
                lore.add(Component.text("Are you really sure to do that, if you delete the island:").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text(" - Players will not be able to recover their progress").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text(" - Their inventory will be cleared").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text(" - The island will be deleted Forever").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                lore.add(Component.text("Click if you really want to delete it").color(TextColor.color(0x5B0D00)).decoration(TextDecoration.ITALIC, false));
                item = CreateItem.createItem(Component.text("Click here if you want to delete " + island).color(TextColor.color(0x922B21)).decoration(TextDecoration.ITALIC, false)
                        , 1
                        , Material.RED_WOOL,
                        lore);
                inv.setItem(i, item);
            }
            else{
                ItemStack itemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setHideTooltip(true);
                itemStack.setItemMeta(itemMeta);

                inv.setItem(i, itemStack);
            }
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        if (event.getClickedInventory().getHolder() instanceof DeleteIslandInventoryAdmin) {//Vérification si c'estle bon inventaire
            String islandName = PlainTextComponentSerializer.plainText().serialize(event.getView().title()).replace("Delete ", "");
            event.setCancelled(true);
            if (event.getSlot() == 13) DeleteIsland.deleteIsland(JustSkyblock.islandManager.getIslandbyName(islandName));
        }
    }
}

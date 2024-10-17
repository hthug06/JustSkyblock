package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.CreateItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DeleteIslandInventory implements InventoryHolder {

    private Inventory inv;
    private ItemStack item;
    public DeleteIslandInventory(){
        inv = Bukkit.createInventory(this, 27, Component.text("Delete The island").color(TextColor.color(0xB03A2E)));  //création de l'inventaire
        init();//Pour mettre les item

    }
    private void init(){
        for (int i  = 0; i<27; i++){
            if (i == 13){
                List<Component> liste = new ArrayList<>();
                liste.add(Component.text("Are you really sure to do that, if you delete your island, you:").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text(" - You will not be able to recover your progress").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text(" - Your inventory will be cleared").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text(" - Your island will be deleted").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text("").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text("Click if you really want to delete it").color(TextColor.color(0x5B0D00)).decoration(TextDecoration.ITALIC, false));
                item = CreateItem.createItem(Component.text("Click here if you want to delete your Island").color(TextColor.color(0x922B21)).decoration(TextDecoration.ITALIC, false),
                        1,Material.RED_WOOL, liste);
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
}

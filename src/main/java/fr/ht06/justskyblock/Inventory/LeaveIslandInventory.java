package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.IslandManager.IslandManager;
import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LeaveIslandInventory implements InventoryHolder {
    private Inventory inv;
    private ItemStack item;
    IslandManager islandManager = JustSkyblock.islandManager;

    public LeaveIslandInventory(Player player){
        inv = Bukkit.createInventory(this, 27, "Leave "+ islandManager.getIslandbyplayer(player.getName()).getIslandName());  //création de l'inventaire
        init();//Pour mettre les item
    }

    private void init(){
        for (int i  = 0; i<27; i++){
            if (i == 13){
                List<Component> liste = new ArrayList<>();
                liste.add(Component.text("Are you really sure to do that, if you leave your island:").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text(" - Your inventory will be cleared").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text("").color(TextColor.color(0xB23324)).decoration(TextDecoration.ITALIC, false));
                liste.add(Component.text("Click if you really want to leave it").color(TextColor.color(0x5B0D00)).decoration(TextDecoration.ITALIC, false));
                item = createItem(Component.text("Click here if you want to leave your Island").color(TextColor.color(0x922B21)).decoration(TextDecoration.ITALIC, false),
                        Material.RED_WOOL, liste);
                inv.setItem(i, item);
            }
            else{
                item = createItemGlass(Component.text("").decoration(TextDecoration.ITALIC, false), Material.RED_STAINED_GLASS_PANE, null);
                inv.setItem(i, item);
            }
        }
    }


    private ItemStack createItem(Component name, Material material, List<Component> lore){

        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack createItemGlass(Component name, Material material, List<Component> lore){

        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.addItemFlags(ItemFlag.HIDE_ITEM_SPECIFICS);
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);

        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }
}

package fr.ht06.skyblockplugin.Inventory;

import fr.ht06.skyblockplugin.SkyblockPlugin;
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

public class IslandInventory implements InventoryHolder {
    private Inventory inv;

    public IslandInventory(){
        inv = Bukkit.createInventory(this, 9, "Island");  //création de l'inventaire
        init();//Pour mettre les item
    }

    private void init(){
        ItemStack item;
        List<String> configIS = new ArrayList<>(SkyblockPlugin.getInstance().getConfig().getConfigurationSection("IS").getKeys(false));

        for (String name: configIS){
            List<String> configISchild = new ArrayList<>(SkyblockPlugin.getInstance().getConfig().getConfigurationSection("IS."+name).getKeys(false));

            Component lore = Component.text(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(2))));

            item = createItem(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(1))),
                    Material.getMaterial(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(0)))),
                    Collections.singletonList(lore.decoration(TextDecoration.ITALIC, false)));

            inv.setItem(Integer.parseInt(String.valueOf(SkyblockPlugin.getInstance().getConfig().get("IS."+name+"."+configISchild.get(4)))), item);
        }





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

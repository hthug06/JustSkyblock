package fr.ht06.skyblockplugin.Inventory;

import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class IslandSettingsInv implements InventoryHolder {

    private Inventory inv;
    ItemStack item;
    int i =0;

    public IslandSettingsInv(Player player){
        this.inv = SkyblockPlugin.getInstance().getServer().createInventory(this, 9, Component.text(player.getName() +"'s island settings")); //création de l'inventaire
        init(player);//Pour mettre les item
    }


    private void init(Player player){
        for (Map.Entry<String, Boolean> v: SkyblockPlugin.playerIslandSettings.get(player.getName()).entrySet()){
            item = createItem("Test"+String.valueOf(i), Material.getMaterial(v.getKey()),null);
            if (SkyblockPlugin.playerIslandSettings.get(player.getName()).get(v.getKey())){
                SkyblockPlugin.setTrue(item);
            }
            else{
                SkyblockPlugin.setFalse(item);
            }
            inv.setItem(i, item);
            i++;
        }
        player.sendMessage(String.valueOf(SkyblockPlugin.playerIslandSettings.get(player.getName())));

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

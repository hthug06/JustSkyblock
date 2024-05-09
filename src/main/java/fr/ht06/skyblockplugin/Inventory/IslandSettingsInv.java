package fr.ht06.skyblockplugin.Inventory;

import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IslandSettingsInv implements InventoryHolder {

    private Inventory inv;
    ItemStack item;
    int i =0;
    IslandManager islandManager = SkyblockPlugin.islandManager;

    public IslandSettingsInv(Player player){
        Island island = islandManager.getIslandbyplayer(player.getName());
        this.inv = SkyblockPlugin.getInstance().getServer().createInventory(this, 9, Component.text(island.getIslandName())); //création de l'inventaire
        init(player);//Pour mettre les item
    }


    private void init(Player player){
        Island island = islandManager.getIslandbyplayer(player.getName());
        for (Map.Entry<String, Boolean> v: island.getAllSettings().entrySet()){
            item = createItem(getSettingsname(v.getKey()), Material.getMaterial(v.getKey()),null);

            if (v.getValue()){
                SkyblockPlugin.setTrue(item);
            }
            else{
                SkyblockPlugin.setFalse(item);
            }
            inv.setItem(i, item);
            i++;
        }
        //player.sendMessage(String.valueOf(SkyblockPlugin.playerIslandSettings.get(player.getName())));

    }


    private ItemStack createItem(Component name, Material material, List<Component> lore){

        ItemStack item = new ItemStack(material, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.displayName(name);
        itemMeta.lore(lore);
        item.setItemMeta(itemMeta);


        return item;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    private Map<String , String> settingsName(){
        Map<String , String> map = new HashMap<>();
        map.put("OAK_DOOR", "Allow player to use Doors");
        map.put("OAK_TRAPDOOR", "Allow player to use Trapdoors");
        map.put("OAK_PRESSURE_PLATE", "Allow player to use activate pressure plate");
        map.put("CHEST", "Allow player to open chest");
        map.put("OAK_BUTTON", "Allow player to use button");
        return map;
    }

    private Component getSettingsname(String name){
        Map<String , String> map = settingsName();

        for (Map.Entry<String, String> v : map.entrySet()){
            if (v.getKey().equalsIgnoreCase(name)){
                return Component.text(v.getValue());
            }
        }
        return null;
    }

}

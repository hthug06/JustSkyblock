package fr.ht06.skyblockplugin.Inventory;

import fr.ht06.skyblockplugin.IslandManager.Island;
import fr.ht06.skyblockplugin.IslandManager.IslandManager;
import fr.ht06.skyblockplugin.SkyblockPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

public class IslandSettingsInv2 implements InventoryHolder {

    private Inventory inv;
    private Boolean State = false;
    ItemStack item;
    int i =0;
    IslandManager islandManager = SkyblockPlugin.islandManager;

    public IslandSettingsInv2(Player player){
        Island island = islandManager.getIslandbyplayer(player.getName());
        this.inv = SkyblockPlugin.getInstance().getServer().createInventory(this, 27, Component.text(island.getIslandName() + " Settings Page 2")); //création de l'inventaire
        init(player);//Pour mettre les item
    }




    private void init(Player player) {
        Island island = islandManager.getIslandbyplayer(player.getName());
        for (Map.Entry<String, Boolean> v : island.getAllSettings().entrySet()) {
            if (v.getKey().equalsIgnoreCase("BEACON") && State.equals(false)) {
                State = true;
            }
            if (State.equals(true)) {
                item = createItem(getSettingsname(v.getKey()).decoration(TextDecoration.ITALIC, false).color(TextColor.color(0xBFC9CA)), Material.getMaterial(v.getKey()), null);

                if (v.getValue()) {
                    SkyblockPlugin.setTrue(item);
                } else {
                    SkyblockPlugin.setFalse(item);
                }
                inv.setItem(i, item);
                i++;
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

    @Override
    public @NotNull Inventory getInventory() {
        return inv;
    }

    private Map<String , String> settingsName(){
        Map<String , String> map = new HashMap<>();
        map.put("OAK_FENCE_GATE", "Allow player to use fence gate");
        map.put("OAK_DOOR", "Allow player to use Doors");
        map.put("OAK_TRAPDOOR", "Allow player to use Trapdoors");
        map.put("OAK_PRESSURE_PLATE", "Allow player to use activate pressure plate");
        map.put("OAK_BUTTON", "Allow player to use button");
        map.put("LEVER", "Allow player to use lever");
        map.put("WHITE_BED", "Allow player to use bed");
        map.put("CHEST", "Allow player to open chest");
        map.put("SHULKER_BOX", "Allow player to open shulker box");
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
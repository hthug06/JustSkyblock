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

public class IslandSettingsInv implements InventoryHolder {

    private Inventory inv;
    ItemStack item;
    int i =0;
    IslandManager islandManager = SkyblockPlugin.islandManager;

    public IslandSettingsInv(Player player){
        Island island = islandManager.getIslandbyplayer(player.getName());
        this.inv = SkyblockPlugin.getInstance().getServer().createInventory(this, 36, Component.text(island.getIslandName() + " Settings Page 1")); //création de l'inventaire
        init(player);//Pour mettre les item
    }


    private void init(Player player){
        Island island = islandManager.getIslandbyplayer(player.getName());
        for (Map.Entry<String, Boolean> v: island.getAllSettings().entrySet()){
            //player.sendMessage(v.getKey());
            if (i == 27) break;

            item = createItem(getSettingsname(v.getKey()).decoration(TextDecoration.ITALIC, false).color(TextColor.color(0xBFC9CA)), Material.getMaterial(v.getKey()),null);

            if (v.getValue()){
                SkyblockPlugin.setTrue(item);
            }
            else{
                SkyblockPlugin.setFalse(item);
            }
            inv.setItem(i, item);
            i++;
        }
        item = createItem(Component.text("PAGE 2"), Material.ARROW, null);
        inv.setItem(35, item);
        //player.sendMessage(String.valueOf(SkyblockPlugin.playerIslandSettings.get(player.getName())));

    }
        //player.sendMessage(String.valueOf(SkyblockPlugin.playerIslandSettings.get(player.getName())));


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
        map.put("OAK_FENCE_GATE", "Allow visitor to use fence gate");
        map.put("OAK_DOOR", "Allow visitor to use Doors");
        map.put("OAK_TRAPDOOR", "Allow visitor to use Trapdoors");
        map.put("OAK_PRESSURE_PLATE", "Allow visitor to use activate pressure plate");
        map.put("OAK_BUTTON", "Allow visitor to use button");
        map.put("LEVER", "Allow visitor to use lever");
        map.put("WHITE_BED", "Allow visitor to use bed");
        map.put("CHEST", "Allow visitor to open chest");
        map.put("BARREL", "Allow visitor to open barrel");
        map.put("SHULKER_BOX", "Allow visitor to open shulker box");
        map.put("CRAFTING_TABLE", "Allow visitor to use crafting table");
        map.put("STONECUTTER", "Allow visitor to use stonecutte");//10
        map.put("CARTOGRAPHY_TABLE", "Allow visitor to use cartography table");
        map.put("SMITHING_TABLE", "Allow visitor to use smithing table");
        map.put("GRINDSTONE", "Allows visitor to use grindstone");
        map.put("FURNACE", "Allows visitor to use furnace");
        map.put("SMOKER", "Allows visitor to use smoker");
        map.put("BLAST_FURNACE", "Allows visitor to use blast furnace");
        map.put("LOOM", "Allows visitor to use loom");
        map.put("ANVIL", "Allows visitor to use anvil");
        map.put("CAMPFIRE", "Allows visitor to use campfire");
        map.put("NOTE_BLOCK", "Allows visitor to use note block and play music");
        map.put("JUKEBOX", "Allows visitor to use jukebox and use a disk");
        map.put("ENCHANTING_TABLE", "Allows visitor to use enchanting table");
        map.put("BREWING_STAND", "Allows visitor to use brewing stand");
        map.put("BELL", "Allows visitor to ring bells");
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

package fr.ht06.justskyblock.Inventory;

import fr.ht06.justskyblock.JustSkyblock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TradeInventory implements InventoryHolder, Listener {

    Inventory inv;
    MiniMessage miniMessage = MiniMessage.miniMessage();

    public TradeInventory(Player player){
        this.inv = Bukkit.createInventory(this, 45, Component.text("Trading"));
        init();
    }

    public TradeInventory(){}

    private void init() {
        YamlConfiguration tradeConfig = JustSkyblock.tradeConfig;
        //for the main identifiant
        for (String base: tradeConfig.getConfigurationSection("Trade.").getKeys(false)){

            ItemStack item = null;
            ItemMeta itemMeta = null;
            //MainItem
            for (String map : tradeConfig.getConfigurationSection("Trade."+base+".mainItem.").getKeys(false)){
                switch (map){
                    case "Block"->{
                       // System.out.println(tradeConfig.getString("Trade."+base+".mainItem."+map));
                        item = new ItemStack(Material.getMaterial(tradeConfig.getString("Trade."+base+".mainItem."+map).toUpperCase()));
                        itemMeta = item.getItemMeta();
                        itemMeta.getPersistentDataContainer().set(new NamespacedKey(JustSkyblock.getInstance(), base), PersistentDataType.BOOLEAN, true);
                    }
                    case "Name"->{
                        itemMeta.displayName(miniMessage.deserialize(tradeConfig.getString("Trade."+base+".mainItem."+map)));
                    }
                    case "Lore" ->{
                        itemMeta.lore(tradeConfig.getStringList("Trade."+base+".mainItem."+map).stream().map(s -> miniMessage.deserialize(s)).toList());
                    }
                }
            }

            String need = tradeConfig.getString("Trade."+base+".Need");
            String received = tradeConfig.getString("Trade."+base+".Received");

            item.setItemMeta(itemMeta);

            this.inv.addItem(item);
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getClickedInventory() == null) return;  //NEEDED

        if (event.getClickedInventory().getHolder() instanceof TradeInventory) {//Vérification si c'estle bon inventaire
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;

            ItemStack item = event.getCurrentItem();
            ItemMeta itemMeta = item.getItemMeta();

            YamlConfiguration tradeConfig = JustSkyblock.tradeConfig;

            String need = null;
            String received = null;
            //put the item in the inv
            for (String bas2: tradeConfig.getConfigurationSection("Trade.").getKeys(false)){
                if (itemMeta.getPersistentDataContainer().has(new NamespacedKey(JustSkyblock.getInstance(), bas2))){
                    need = tradeConfig.getString("Trade."+bas2+".Need");
                    received = tradeConfig.getString("Trade."+bas2+".Received");
                }
            }

            //Check if the player has the right item
            Material material = Material.getMaterial(need.split(":")[0].toUpperCase());
            int amount = Integer.parseInt(need.split(":")[1]);
            ItemStack itemStack = new ItemStack(material, amount);

            //if no send a message
            if (!player.getInventory().containsAtLeast(new ItemStack(material),amount)){
                player.sendMessage("you don't have the required item for this trade");
            }
            //if yes take the item and give the other item
            else{
                Material receivedMaterial = Material.getMaterial(received.split(":")[0].toUpperCase());
                int receivedAmount = Integer.parseInt(received.split(":")[1]);
                ItemStack receivedItemStack = new ItemStack(receivedMaterial, receivedAmount);
                player.getInventory().removeItemAnySlot(itemStack);
                player.getInventory().addItem(receivedItemStack);
            }

        }
    }
}
